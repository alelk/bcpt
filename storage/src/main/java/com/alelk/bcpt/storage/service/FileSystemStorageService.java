package com.alelk.bcpt.storage.service;

import com.alelk.bcpt.storage.exception.BcptStorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

/**
 * File System Storage Service
 *
 * Created by Alex Elkin on 02.11.2017.
 */
@Service
public class FileSystemStorageService implements StorageService {

    private static final Logger log = LoggerFactory.getLogger(FileSystemStorageService.class);
    private final Path rootLocation;

    @Autowired
    public FileSystemStorageService(Environment environment) {
        rootLocation = Paths.get(environment.getProperty("root-location"));
    }

    @Override
    public void store(MultipartFile file) {
        store(file, null);
    }

    @Override
    public void store(MultipartFile file, String category) {
        store(file, null, null);
    }

    @Override
    public void store(MultipartFile file, String category, String name) {
        String fileName = StringUtils.cleanPath(name != null ? name : file.getOriginalFilename());
        Path location = location(category);
        init(location);
        Path filePath = location.resolve(fileName);
        try {
            Files.copy(file.getInputStream(), location.resolve(filePath), StandardCopyOption.REPLACE_EXISTING);
            log.debug("The file '" + fileName + "' category '" + category + "': saved to '" + filePath.toAbsolutePath() + '\'');
        } catch (IOException e) {
            throw new BcptStorageException("Cannot store the file '" + fileName + "' in the category '" + category + '\'', e);
        }
    }

    @Override
    public Path load(String fileName) {
        return load(fileName, null);
    }

    @Override
    public Stream<Path> loadAll() {
        return loadAll(null);
    }

    @Override
    public Stream<Path> loadAll(String category) {
        try {
            final Path location = location(category);
            return Files.walk(location, 1)
                    .filter(path -> !path.equals(location));
        } catch (IOException e) {
            throw new BcptStorageException("Cannot load file list from category '" + category + "'", e);
        }
    }

    @Override
    public Stream<Resource> loadAllAsResources() {
        return loadAllAsResources(null);
    }

    @Override
    public Stream<Resource> loadAllAsResources(String category) {
        return loadAll(category).map(FileSystemStorageService::resourceByPath);
    }

    @Override
    public Path load(String fileName, String category) {
        return location(category).resolve(fileName);
    }

    @Override
    public Resource loadAsResource(String fileName) {
        return loadAsResource(fileName, null);
    }

    public Resource loadAsResource(String fileName, String category) {
        Path filePath = load(fileName, category);
        return resourceByPath(filePath);
    }

    @Override
    public void delete(String fileName, String category) {
        try {
            Files.deleteIfExists(location(category).resolve(fileName));
        } catch (IOException e) {
            throw new BcptStorageException("Cannot delete the file '" + fileName + "' from the category '" + category + '\'', e);
        }
    }

    @Override
    public void delete(String fileName) {
        delete(fileName, null);
    }

    private Path location(String category) {
        return category == null ? rootLocation : rootLocation.resolve(category);
    }

    private static Resource resourceByPath(Path filePath) {
        try {
            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists() || !resource.isReadable() || !resource.isFile())
                throw new BcptStorageException("Cannot find the file '" + filePath.toAbsolutePath() + '\'');
            return resource;
        } catch (MalformedURLException e) {
            throw new BcptStorageException("Cannot find the file '" + filePath.toAbsolutePath() + "': malformed URL", e);
        }
    }

    private void init(Path directory) {
        try {
            Files.createDirectories(directory);
        } catch (IOException e) {
            log.error("Cannot create directory '" + directory + '\'');
        }
    }

}
