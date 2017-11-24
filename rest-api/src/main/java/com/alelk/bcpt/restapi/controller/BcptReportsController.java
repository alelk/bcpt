package com.alelk.bcpt.restapi.controller;

import com.alelk.bcpt.reportgenerator.ReportGenerator;
import com.alelk.bcpt.reportgenerator.exporter.target.OutputStreamTarget;
import com.alelk.bcpt.reportgenerator.exporter.target.TargetFormat;
import com.alelk.bcpt.storage.BcptStorage;
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

    private static final SimpleDateFormat REPORT_ID_DATE = new SimpleDateFormat("yyMMddHHmmssSSS");
    private ReportGenerator reportGenerator;
    private BcptStorage storage;

    @Autowired
    public BcptReportsController(ReportGenerator reportGenerator, BcptStorage storage) {
        this.reportGenerator = reportGenerator;
        this.storage = storage;
    }

    @GetMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<InputStreamResource> generateReport(@RequestParam String productBatchId,
                                                              @RequestParam String targetFormatSignature) throws IOException {
        final TargetFormat targetFormat = TargetFormat.forValue(targetFormatSignature);
        final String fileName = generateReportId(productBatchId) + "." + targetFormat.getFileExtension();
        final PipedInputStream inputStream = new PipedInputStream();
        final PipedOutputStream outputStream = new PipedOutputStream(inputStream);
        final OutputStreamTarget target = new OutputStreamTarget(targetFormat, outputStream);
        storage.store(inputStream, "reports", fileName);
        reportGenerator.generateProductBatchReport(productBatchId, Collections.singletonList(target));
        final Resource fileResource = storage.loadAsResource(fileName, "reports");
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
    }


    private String generateReportId(String productBatchId) {
        return "product-batch-" + productBatchId + "-" + REPORT_ID_DATE.format(new Date());
    }
}
