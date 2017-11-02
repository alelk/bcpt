package com.alelk.bcpt.restapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Date;

/**
 * File Info Dto
 *
 * Created by Alex Elkin on 02.11.2017.
 */
public class FileInfoDto {

    private static final Logger log = LoggerFactory.getLogger(FileInfoDto.class);
    private String fileName;
    private Long fileSize;
    private Date lastModified;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public static FileInfoDto fromResource(Resource fileResource) {
        if (fileResource == null) return null;
        FileInfoDto dto = new FileInfoDto();
        dto.setFileName(fileResource.getFilename());
        try {
            dto.setFileSize(fileResource.contentLength());
            dto.setLastModified(new Date(fileResource.lastModified()));
        } catch (IOException e) {
            log.warn("Unable to build file info dto from resource: fileName:" + fileResource.getFilename() + ":" + e.getLocalizedMessage());
        }
        return dto;
    }

    @Override
    public String toString() {
        return "FileInfoDto{" +
                "fileName='" + fileName + '\'' +
                ", fileSize=" + fileSize +
                ", lastModified=" + lastModified +
                '}';
    }
}
