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
    private Set<String> bloodInvoiceIds;
    private String productBatchExternalId;
    private Double totalAmount;

    public BloodPoolDto() {}

    public BloodPoolDto(String externalId, Date creationTimestamp, Date updateTimestamp) {
        super(externalId, creationTimestamp, updateTimestamp);
    }

    public BloodPoolDto(String externalId, Date creationTimestamp, Date updateTimestamp, Integer poolNumber, Set<String> bloodInvoiceIds, String productBatchExternalId, Double totalAmount) {
        super(externalId, creationTimestamp, updateTimestamp);
        this.poolNumber = poolNumber;
        this.bloodInvoiceIds = bloodInvoiceIds;
        this.productBatchExternalId = productBatchExternalId;
    }

    public Integer getPoolNumber() {
        return poolNumber;
    }

    public Set<String> getBloodInvoiceIds() {
        return bloodInvoiceIds;
    }

    public void setPoolNumber(Integer poolNumber) {
        this.poolNumber = poolNumber;
    }

    public void setBloodInvoiceIds(Set<String> bloodInvoiceIds) {
        this.bloodInvoiceIds = bloodInvoiceIds;
    }

    public String getProductBatchExternalId() {
        return productBatchExternalId;
    }

    public void setProductBatchExternalId(String productBatchExternalId) {
        this.productBatchExternalId = productBatchExternalId;
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
                ", productBatchExternalId='" + productBatchExternalId +
                ", poolNumber='" + poolNumber +
                ", bloodInvoiceIds=" + (bloodInvoiceIds != null
                ? '[' + bloodInvoiceIds.stream().collect(Collectors.joining(", ")) + ']'
                : null) +
                ", totalAmount=" + totalAmount +
                ", creationTimestamp=" + getCreationTimestamp() +
                ", updateTimestamp=" + getUpdateTimestamp() +
                '}';
    }
}
