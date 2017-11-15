package com.alelk.bcpt.restapi.dto;

import com.alelk.bcpt.importer.parsed.BcptDtoBundleInfo;
import com.alelk.bcpt.importer.result.OperationResult;
import com.alelk.bcpt.importer.result.Result;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Import State DTO
 *
 * Created by Alex Elkin on 09.11.2017.
 */
public class ImportStateDto {

    private Date importTimestamp;
    private String importProcessId;
    private String category;
    private String fileName;
    private String operationName;
    private Double progress;
    private Result result;
    private Integer countPersons;
    private Integer countBloodDonations;
    private Integer countBloodInvoices;
    private Integer countBloodPools;
    private Integer countProductBatches;
    private List<String> errors;

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

    public ImportStateDto(String importProcessId, String category, String fileName, Date importTimestamp) {
        this.importTimestamp = importTimestamp;
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

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public Double getProgress() {
        return progress;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Integer getCountPersons() {
        return countPersons;
    }

    public void setCountPersons(Integer countPersons) {
        this.countPersons = countPersons;
    }

    public Integer getCountBloodDonations() {
        return countBloodDonations;
    }

    public void setCountBloodDonations(Integer countBloodDonations) {
        this.countBloodDonations = countBloodDonations;
    }

    public Integer getCountBloodInvoices() {
        return countBloodInvoices;
    }

    public void setCountBloodInvoices(Integer countBloodInvoices) {
        this.countBloodInvoices = countBloodInvoices;
    }

    public Integer getCountBloodPools() {
        return countBloodPools;
    }

    public void setCountBloodPools(Integer countBloodPools) {
        this.countBloodPools = countBloodPools;
    }

    public Integer getCountProductBatches() {
        return countProductBatches;
    }

    public void setCountProductBatches(Integer countProductBatches) {
        this.countProductBatches = countProductBatches;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    public Date getImportTimestamp() {
        return importTimestamp;
    }

    public void setImportTimestamp(Date importTimestamp) {
        this.importTimestamp = importTimestamp;
    }

    public void applyOperationResult(OperationResult<BcptDtoBundleInfo> operationResult) {
        if (operationResult == null) return;
        operationName = operationResult.getOperationName();
        progress = operationResult.getProgress();
        result = operationResult.getResult();
        errors = operationResult.getErrors() != null ? operationResult.getErrors().stream().map(Throwable::toString).collect(Collectors.toList()) : null;
        final BcptDtoBundleInfo bundleInfo = operationResult.get();
        if (bundleInfo == null) return;
        countPersons = bundleInfo.getCountPersons();
        countBloodDonations = bundleInfo.getCountBloodDonations();
        countBloodInvoices = bundleInfo.getCountBloodInvoices();
        countBloodPools = bundleInfo.getCountBloodPools();
        countProductBatches = bundleInfo.getCountProductBatches();
    }

    @Override
    public String toString() {
        return "ImportStateDto{" +
                "importProcessId='" + importProcessId + '\'' +
                ", category='" + category + '\'' +
                ", fileName='" + fileName + '\'' +
                ", operationName='" + operationName + '\'' +
                ", progress=" + progress +
                ", result=" + result +
                ", countPersons=" + countPersons +
                ", countBloodDonations=" + countBloodDonations +
                ", countBloodInvoices=" + countBloodInvoices +
                ", countBloodPools=" + countBloodPools +
                ", countProductBatches=" + countProductBatches +
                ", errors=" + errors +
                ", importTimestamp=" + importTimestamp +
                '}';
    }
}
