package com.alelk.bcpt.restapi.controller;

import com.alelk.bcpt.importer.BcptImporter;
import com.alelk.bcpt.importer.parsed.BcptDtoBundleInfo;
import com.alelk.bcpt.importer.result.OperationResult;
import com.alelk.bcpt.restapi.dto.ImportStateDto;
import com.alelk.bcpt.storage.BcptStorage;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Bcpt Importer Controller
 *
 * Created by Alex Elkin on 01.11.2017.
 */
@Controller
@RequestMapping("/import")
public class BcptImporterController {

    private static final Logger log = LoggerFactory.getLogger(BcptImporterController.class);
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd-hhmmss-SSS");
    private SimpMessagingTemplate simpMessagingTemplate;
    private BcptStorage storage;
    private BcptImporter importer;

    @Autowired
    public BcptImporterController(SimpMessagingTemplate simpMessagingTemplate, BcptStorage storage, BcptImporter importer) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.storage = storage;
        this.importer = importer;
    }

    @PostMapping("/dbf")
    public ResponseEntity<ImportStateDto> importDbfData(@RequestParam String fileName) throws IOException {
        final String processId = generateProcessId("uploadeddbf");
        final ImportStateDto importState = new ImportStateDto(processId, "uploadeddbf", fileName);
        doDbfImport(importState);
        return ResponseEntity.ok(importState);
    }

    private void doDbfImport(ImportStateDto importState) throws IOException {
        Flowable<OperationResult<BcptDtoBundleInfo>> flow = importer.importDbf(
                storage.loadAsResource(importState.getFileName(), importState.getCategory()).getFile()
        ).subscribeOn(Schedulers.io());
        flow.sample(5, TimeUnit.SECONDS, true).subscribe(progress -> {
            importState.applyOperationResult(progress);
            postOperationProgress(importState);
        });
    }

    private void postOperationProgress(ImportStateDto importState) {
        simpMessagingTemplate.convertAndSend("/socket-output/importer/", importState);
    }

    private static String generateProcessId(String category) {
        return "import-" + DATE_FORMAT.format(new Date()) + "-" + category;
    }
}
