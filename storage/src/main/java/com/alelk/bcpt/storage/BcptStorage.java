package com.alelk.bcpt.storage;

import com.alelk.bcpt.storage.service.FileSystemStorageService;
import com.alelk.bcpt.storage.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * Bcpt Storage
 *
 * Created by Alex Elkin on 02.11.2017.
 */
@Component
public class BcptStorage implements StorageService {

    private StorageService storageService;
    private static ApplicationContext context;

    @Autowired
    private BcptStorage(FileSystemStorageService storageService) {
        this.storageService = storageService;
    }

    public static BcptStorage getInstance() {
        if (context == null) context = new AnnotationConfigApplicationContext(BcptStorageConfig.class);
        return context.getBean(BcptStorage.class);
    }

    @Override
    public void store(MultipartFile file) {
        storageService.store(file);
    }

    @Override
    public void store(MultipartFile file, String category) {
        storageService.store(file, category);
    }

    @Override
    public void store(MultipartFile file, String category, String fileName) {
        storageService.store(file, category, fileName);
    }

    @Override
    public Path load(String fileName) {
        return storageService.load(fileName);
    }

    @Override
    public Stream<Path> loadAll() {
        return storageService.loadAll();
    }

    @Override
    public Stream<Path> loadAll(String category) {
        return storageService.loadAll(category);
    }

    @Override
    public Stream<Resource> loadAllAsResources() {
        return storageService.loadAllAsResources();
    }

    @Override
    public Stream<Resource> loadAllAsResources(String category) {
        return storageService.loadAllAsResources(category);
    }

    @Override
    public Path load(String fileName, String category) {
        return storageService.load(fileName, category);
    }

    @Override
    public Resource loadAsResource(String fileName) {
        return storageService.loadAsResource(fileName);
    }

    @Override
    public Resource loadAsResource(String fileName, String category) {
        return storageService.loadAsResource(fileName, category);
    }

    @Override
    public void delete(String fileName, String category) {
        storageService.delete(fileName, category);
    }

    @Override
    public void delete(String fileName) {
        storageService.delete(fileName);
    }
}
