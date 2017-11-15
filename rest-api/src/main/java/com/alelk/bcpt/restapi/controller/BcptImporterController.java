package com.alelk.bcpt.restapi.controller;

import com.alelk.bcpt.importer.BcptImporter;
import com.alelk.bcpt.importer.parsed.BcptDtoBundleInfo;
import com.alelk.bcpt.importer.result.OperationResult;
import com.alelk.bcpt.restapi.dto.ImportStateDto;
import com.alelk.bcpt.storage.BcptStorage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Bcpt Importer Controller
 *
 * Created by Alex Elkin on 01.11.2017.
 */
@Controller
@RequestMapping("/import")
public class BcptImporterController {

    private static final Logger log = LoggerFactory.getLogger(BcptImporterController.class);
    private static ObjectMapper objectMapper = new ObjectMapper();
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
        final String processId = generateProcessId("dbf");
        final ImportStateDto importState = new ImportStateDto(processId, "dbf", fileName, new Date());
        doDbfImport(importState);
        return ResponseEntity.ok(importState);
    }

    @GetMapping("/")
    public ResponseEntity<List<ImportStateDto>> getImportResults() {
        return ResponseEntity.ok(storage.loadAllAsResources("importResults")
                .map(this::readImportState).collect(Collectors.toList()));
    }

    private void doDbfImport(ImportStateDto importState) throws IOException {
        Flowable<OperationResult<BcptDtoBundleInfo>> flow = importer.importDbf(
                storage.loadAsResource(importState.getFileName(), "uploaded" + importState.getCategory()).getFile()
        ).subscribeOn(Schedulers.io());
        flow.sample(5, TimeUnit.SECONDS, true).subscribe(progress -> {
            importState.applyOperationResult(progress);
            postOperationProgress(importState);
        });
        flow.sample(20, TimeUnit.SECONDS, true).subscribe(progress -> {
            importState.applyOperationResult(progress);
            saveImportState(importState);
        });
    }

    private void saveImportState(ImportStateDto importStateDto) {
        try {
            final String str = objectMapper.writerFor(ImportStateDto.class).writeValueAsString(importStateDto);
            storage.store(
                    new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8.name())),
                    "importResults",
                    importStateDto.getImportProcessId() + ".json"
            );
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            log.warn("Unable to save importing state: " + importStateDto);
        }
    }

    private ImportStateDto readImportState(Resource fileResource) {
        try {
            return objectMapper.readerFor(ImportStateDto.class).readValue(fileResource.getInputStream());
        } catch (IOException e) {
            log.warn("Unable to read Import State from resource '" + fileResource.getFilename() + '\'');
            return null;
        }
    }

    private void postOperationProgress(ImportStateDto importState) {
        simpMessagingTemplate.convertAndSend("/socket-output/importer/", importState);
    }

    private static String generateProcessId(String category) {
        return "import-" + DATE_FORMAT.format(new Date()) + "-" + category;
    }
}
