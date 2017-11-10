package com.alelk.bcpt.restapi.dto;

/**
 * Import State DTO
 *
 * Created by Alex Elkin on 09.11.2017.
 */
public class ImportStateDto {

    private String importProcessId;
    private String category;
    private String fileName;

    public ImportStateDto() {
    }

    public ImportStateDto(String importProcessId) {
        this.importProcessId = importProcessId;
    }

    public ImportStateDto(String importProcessId, String category, String fileName) {
        this.importProcessId = importProcessId;
        this.category = category;
        this.fileName = fileName;
    }

    public String getImportProcessId() {
        return importProcessId;
    }

    public void setImportProcessId(String importProcessId) {
        this.importProcessId = importProcessId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "ImportStateDto{" +
                "importProcessId='" + importProcessId + '\'' +
                ", category='" + category + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
