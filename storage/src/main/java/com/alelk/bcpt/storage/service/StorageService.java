package com.alelk.bcpt.storage.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * StorageService
 *
 * Created by Alex Elkin on 02.11.2017.
 */
public interface StorageService {

    void store(MultipartFile file);

    void store(MultipartFile file, String category);

    void store(MultipartFile file, String category, String fileName);

    Path load(String fileName);

    Stream<Path> loadAll();

    Stream<Path> loadAll(String category);

    Stream<Resource> loadAllAsResources();

    Stream<Resource> loadAllAsResources(String category);

    Path load(String fileName, String category);

    Resource loadAsResource(String fileName);

    Resource loadAsResource(String fileName, String category);

    void delete(String fileName, String category);

    void delete(String fileName);
}
