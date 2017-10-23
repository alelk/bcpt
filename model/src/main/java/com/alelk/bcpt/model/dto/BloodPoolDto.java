package com.alelk.bcpt.model.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Blood Donation Pool Dto
 *
 * Created by Alex Elkin on 12.09.2017.
 */
public class BloodPoolDto extends AbstractBcptDto {

    private Integer poolNumber;
    private Set<String> bloodInvoices;
    private String productBatch;
    private Double totalAmount;

    public BloodPoolDto() {}

    public BloodPoolDto(String externalId, Date creationTimestamp, Date updateTimestamp) {
        super(externalId, creationTimestamp, updateTimestamp);
    }

    public BloodPoolDto(String externalId, Date creationTimestamp, Date updateTimestamp, Integer poolNumber, Set<String> bloodInvoices, String productBatch, Double totalAmount) {
        super(externalId, creationTimestamp, updateTimestamp);
        this.poolNumber = poolNumber;
        this.bloodInvoices = bloodInvoices;
        this.productBatch = productBatch;
    }

    public Integer getPoolNumber() {
        return poolNumber;
    }

    public Set<String> getBloodInvoices() {
        return bloodInvoices;
    }

    public void setPoolNumber(Integer poolNumber) {
        this.poolNumber = poolNumber;
    }

    public void setBloodInvoices(Set<String> bloodInvoices) {
        this.bloodInvoices = bloodInvoices;
    }

    public String getProductBatch() {
        return productBatch;
    }

    public void setProductBatch(String productBatch) {
        this.productBatch = productBatch;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount == null ? null : new BigDecimal(totalAmount).setScale(2, RoundingMode.FLOOR).doubleValue();
    }

    @Override
    public String toString() {
        return "BloodPoolDto{" +
                "externalId='" + getExternalId() +
                ", productBatch='" + productBatch +
                ", poolNumber='" + poolNumber +
                ", bloodInvoices=" + (bloodInvoices != null
                ? '[' + bloodInvoices.stream().collect(Collectors.joining(", ")) + ']'
                : null) +
                ", totalAmount=" + totalAmount +
                ", creationTimestamp=" + getCreationTimestamp() +
                ", updateTimestamp=" + getUpdateTimestamp() +
                '}';
    }
}
