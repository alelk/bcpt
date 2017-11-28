package com.alelk.bcpt.restapi.controller;

import com.alelk.bcpt.reportgenerator.ReportGenerator;
import com.alelk.bcpt.reportgenerator.exporter.target.OutputStreamTarget;
import com.alelk.bcpt.reportgenerator.exporter.target.TargetFormat;
import com.alelk.bcpt.storage.BcptStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

/**
 * BCPT Reports Controller
 *
 * Created by Alex Elkin on 24.11.2017.
 */
@RestController
@RequestMapping("/reports")
public class BcptReportsController {

    private static final Logger log = LoggerFactory.getLogger(BcptReportsController.class);
    private static final SimpleDateFormat REPORT_ID_DATE = new SimpleDateFormat("yyMMddHHmmssSSS");
    private ReportGenerator reportGenerator;
    private BcptStorage storage;

    @Autowired
    public BcptReportsController(ReportGenerator reportGenerator, BcptStorage storage) {
        this.reportGenerator = reportGenerator;
        this.storage = storage;
    }

    @GetMapping("/productBatch")
    public ResponseEntity<InputStreamResource> generateReport(@RequestParam String externalId,
                                                              @RequestParam String targetFormat) throws IOException {
        final TargetFormat tf = TargetFormat.forValue(targetFormat);
        if (tf == null) throw new IllegalArgumentException("Illegal target format signature: " + targetFormat);
        final String fileName = generateReportId(externalId) + "." + tf.getFileExtension();
        log.debug("Request to generate product batch report for the product batch '{}'; target format: {}; file name: {}",
                externalId, targetFormat, fileName);
        final PipedInputStream inputStream = new PipedInputStream();
        final PipedOutputStream outputStream = new PipedOutputStream(inputStream);
        try {
            final OutputStreamTarget target = new OutputStreamTarget(tf, outputStream);
            new Thread(() ->
                    reportGenerator.generateProductBatchReport(externalId, Collections.singletonList(target))
            ).start();
            storage.store(inputStream, "reports", fileName);
            final Resource fileResource = storage.loadAsResource(fileName, "reports");
            log.info("Stored: {}", fileResource.getFile().getAbsolutePath());
            return ResponseEntity.ok()
                    .header("Cache-Control", "no-cache, no-store, must-revalidate")
                    .header("Pragma", "no-cache").header("Expires", "0")
                    .header("Content-Disposition", "attachment; filename=" + fileName)
                    .contentLength(fileResource.contentLength())
                    .contentType(fileName.toLowerCase().endsWith(".pdf") ? MediaType.APPLICATION_PDF :
                            fileName.toLowerCase().endsWith(".xls") ? MediaType.parseMediaType("application/vnd.ms-excel") :
                                    fileName.toLowerCase().endsWith(".docx") ?
                                            MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                                            : null)
                    .body(new InputStreamResource(fileResource.getInputStream()));
        } finally {
            outputStream.close();
        }
    }


    private String generateReportId(String productBatchId) {
        return "product-batch-report-" + productBatchId + "-" + REPORT_ID_DATE.format(new Date());
    }
}
