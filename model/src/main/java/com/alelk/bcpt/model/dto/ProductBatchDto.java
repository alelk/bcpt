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

    private Date batchDate;
    private Set<String> bloodPools;
    private Double totalAmount;

    public ProductBatchDto() {}

    ProductBatchDto(String externalId, Date creationTimestamp, Date updateTimestamp) {
        super(externalId, creationTimestamp, updateTimestamp);
    }

    public ProductBatchDto(String externalId, Date creationTimestamp, Date updateTimestamp, Date batchDate, Set<String> bloodPools, Double totalAmount) {
        super(externalId, creationTimestamp, updateTimestamp);
        this.batchDate = batchDate;
        this.bloodPools = bloodPools;
        this.totalAmount = totalAmount;
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

    @Override
    public String toString() {
        return "ProductBatchDto{" +
                "externalId='" + getExternalId() +
                ", batchDate='" + batchDate +
                ", bloodPools=" + (bloodPools != null
                ? '[' + bloodPools.stream().collect(Collectors.joining(", ")) + ']'
                : null) +
                ", creationTimestamp=" + getCreationTimestamp() +
                ", totalAmount=" + totalAmount +
                ", updateTimestamp=" + getUpdateTimestamp() +
                '}';
    }
}
