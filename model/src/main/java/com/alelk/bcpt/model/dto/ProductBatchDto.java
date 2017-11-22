package com.alelk.bcpt.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Product Batch Dto
 *
 * Created by Alex Elkin on 12.09.2017.
 */
public class ProductBatchDto extends AbstractBcptDto {

    private Integer batchNumber;
    private Date batchDate;
    private Set<String> bloodPools;
    private Double totalAmount;
    private String location;
    private String batchAuthor;
    private String productProvider;
    private String productName;

    public ProductBatchDto() {}

    ProductBatchDto(String externalId, Date creationTimestamp, Date updateTimestamp) {
        super(externalId, creationTimestamp, updateTimestamp);
    }

    public ProductBatchDto(String externalId, Date creationTimestamp, Date updateTimestamp, Integer batchNumber, Date batchDate, Set<String> bloodPools, Double totalAmount, String location, String batchAuthor, String productProvider, String productName) {
        super(externalId, creationTimestamp, updateTimestamp);
        this.batchNumber = batchNumber;
        this.batchDate = batchDate;
        this.bloodPools = bloodPools;
        this.totalAmount = totalAmount;
        this.location = location;
        this.batchAuthor = batchAuthor;
        this.productProvider = productProvider;
        this.productName = productName;
    }

    public Integer getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(Integer batchNumber) {
        this.batchNumber = batchNumber;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    public Date getBatchDate() {
        return batchDate;
    }

    public Set<String> getBloodPools() {
        return bloodPools;
    }

    public void setBatchDate(Date batchDate) {
        this.batchDate = batchDate;
    }

    public void setBloodPools(Set<String> bloodPools) {
        this.bloodPools = bloodPools;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount == null ? null : new BigDecimal(totalAmount).setScale(2, RoundingMode.FLOOR).doubleValue();
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

    @Override
    public String toString() {
        return "ProductBatchDto{" +
                "externalId='" + getExternalId() +
                "', batchNumber=" + batchNumber +
                ", batchDate='" + batchDate +
                ", bloodPools=" + (bloodPools != null
                ? '[' + bloodPools.stream().collect(Collectors.joining(", ")) + ']'
                : null) +
                ", creationTimestamp=" + getCreationTimestamp() +
                ", totalAmount=" + totalAmount +
                ", location=" + location +
                ", batchAuthor=" + batchAuthor +
                ", productName=" + productName +
                ", productProvider=" + productProvider +
                ", updateTimestamp=" + getUpdateTimestamp() +
                '}';
    }
}
