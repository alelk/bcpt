package com.alelk.bcpt.restapi.request;

import com.alelk.bcpt.model.AnalysisConclusion;
import com.alelk.bcpt.model.dto.ProductBatchDto;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Product Batch Abstract Request
 *
 * Created by Alex Elkin on 27.09.2017.
 */
@SuppressWarnings("unused")
public abstract class ProductBatchAbstractRequest extends BcptDtoApiRequest<ProductBatchDto> {

    private Integer batchNumber;
    private Date batchDate;
    private Set<String> bloodPools;
    private Double totalAmount;
    private String location;
    private String batchAuthor;
    private String productProvider;
    private String productName;
    private AnalysisConclusion analysisConclusion;

    public Integer getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(Integer batchNumber) {
        this.batchNumber = batchNumber;
    }

    public Date getBatchDate() {
        return batchDate;
    }

    public void setBatchDate(Date batchDate) {
        this.batchDate = batchDate;
    }

    public Set<String> getBloodPools() {
        return bloodPools;
    }

    public void setBloodPools(Set<String> bloodPools) {
        this.bloodPools = bloodPools;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBatchAuthor() {
        return batchAuthor;
    }

    public void setBatchAuthor(String batchAuthor) {
        this.batchAuthor = batchAuthor;
    }

    public String getProductProvider() {
        return productProvider;
    }

    public void setProductProvider(String productProvider) {
        this.productProvider = productProvider;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public AnalysisConclusion getAnalysisConclusion() {
        return analysisConclusion;
    }

    public void setAnalysisConclusion(AnalysisConclusion analysisConclusion) {
        this.analysisConclusion = analysisConclusion;
    }

    @Override
    public ProductBatchDto toDto() {
        return new ProductBatchDto(getExternalId(), null, null, batchNumber, batchDate, bloodPools, totalAmount, location, batchAuthor, productProvider, productName, analysisConclusion);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "externalId='" + getExternalId() + '\'' +
                ", batchNumber=" + batchNumber +
                ", batchDate=" + batchDate +
                ", totalAmount=" + totalAmount +
                ", location=" + location +
                ", batchAuthor=" + batchAuthor +
                ", productName=" + productName +
                ", productProvider=" + productProvider +
                ", analysisConclusion=" + analysisConclusion +
                ", bloodPools=" + (bloodPools != null ?
                '[' + bloodPools.stream().collect(Collectors.joining(", ")) + ']' : null) +
                '}';
    }
}
