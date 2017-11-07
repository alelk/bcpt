package com.alelk.bcpt.restapi.controller;

import com.alelk.bcpt.restapi.dto.FileInfoDto;
import com.alelk.bcpt.storage.BcptStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Bcpt Upload Controller
 *
 * Created by Alex Elkin on 02.11.2017.
 */
@Controller
@RequestMapping("/upload")
public class BcptUploadController {

    private static final Logger log = LoggerFactory.getLogger(BcptUploadController.class);
    private BcptStorage storage;

    public BcptUploadController(BcptStorage storage) {
        this.storage = storage;
    }

    @PostMapping("/{category}")
    public ResponseEntity<FileInfoDto> uploadFile(@RequestParam("file") MultipartFile file,
                                                  @PathVariable String category,
                                                  @RequestParam(required = false) String fileName) {
        log.debug("Request to upload " + category + " file: '" +
                (file != null ? file.getOriginalFilename() + "' size=" + file.getSize() + " name: " + fileName : null));
        storage.store(file, category(category), fileName);
        return ResponseEntity.ok(FileInfoDto.fromResource(storage.loadAsResource(fileName, category(category))));
    }

    @GetMapping("/{category}")
    public ResponseEntity<List<FileInfoDto>> getFileList(@PathVariable String category) {
        return ResponseEntity.ok(storage.loadAllAsResources(category(category))
                .map(FileInfoDto::fromResource).collect(Collectors.toList()));
    }

    @GetMapping("download/{category}")
    public ResponseEntity<InputStreamResource> getFile(@PathVariable String category, @RequestParam String fileName) throws IOException {
        log.info("Request to download {} file: '{}'", category, fileName);
        Resource fileResource = storage.loadAsResource(fileName, category(category));
        return ResponseEntity.ok()
                .header("Cache-Control", "no-cache, no-store, must-revalidate")
                .header("Pragma", "no-cache").header("Expires", "0")
                .header("Content-Disposition", "attachment; filename=" + fileName)
                .contentLength(fileResource.contentLength()).contentType(MediaType.TEXT_PLAIN)
                .body(new InputStreamResource(fileResource.getInputStream()));
    }

    private String category(String externalCategory) {
        return "uploaded" + externalCategory;
    }
}
