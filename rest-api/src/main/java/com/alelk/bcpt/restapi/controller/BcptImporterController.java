package com.alelk.bcpt.restapi.controller;

import com.alelk.bcpt.importer.BcptImporter;
import com.alelk.bcpt.storage.BcptStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
    private BcptStorage storage;
    private BcptImporter importer;

    @Autowired
    public BcptImporterController(SimpMessagingTemplate simpMessagingTemplate, BcptStorage storage, BcptImporter importer) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.storage = storage;
        this.importer = importer;
    }



}
