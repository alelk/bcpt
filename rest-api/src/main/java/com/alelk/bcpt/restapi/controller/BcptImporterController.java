package com.alelk.bcpt.restapi.controller;

import com.alelk.bcpt.importer.BcptImporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Bcpt Importer Controller
 *
 * Created by Alex Elkin on 01.11.2017.
 */
@Controller
@RequestMapping("/importer")
public class BcptImporterController {

    private static final Logger log = LoggerFactory.getLogger(BcptImporterController.class);
    private SimpMessagingTemplate simpMessagingTemplate;
    private BcptImporter importer;

    @Autowired
    public BcptImporterController(SimpMessagingTemplate simpMessagingTemplate, BcptImporter importer) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.importer = importer;
    }

    @PostMapping("/importDbf")
    public String importData(@RequestParam("file") MultipartFile file) {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(file.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            do {
                line = bufferedReader.readLine();
                log.info("Line: " + line);
            } while (line != null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        Flowable<OperationResult<BcptDtoBundle>> flow = importer.parseDbf(null).subscribeOn(Schedulers.io());
        flow.subscribe(pr -> log.info("Parsed: " + pr.getProgress()), exc -> log.error("Error: ", exc), () -> log.info("Complete"));
        */
        return null;
    }


}
