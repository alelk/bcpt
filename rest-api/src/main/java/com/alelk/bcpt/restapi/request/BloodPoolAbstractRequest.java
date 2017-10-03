package com.alelk.bcpt.restapi.request;

import com.alelk.bcpt.model.dto.BloodPoolDto;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Blood Pool Abstract Request
 *
 * Created by Alex Elkin on 27.09.2017.
 */
@SuppressWarnings("unused")
public class BloodPoolAbstractRequest extends BcptDtoApiRequest<BloodPoolDto> {

    private Integer poolNumber;
    private Set<String> bloodInvoiceIds;
    private String productBatchExternalId;
    private Double totalAmount;

    public Integer getPoolNumber() {
        return poolNumber;
    }

    public void setPoolNumber(Integer poolNumber) {
        this.poolNumber = poolNumber;
    }

    public Set<String> getBloodInvoiceIds() {
        return bloodInvoiceIds;
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
        this.totalAmount = totalAmount;
    }

    @Override
    public BloodPoolDto toDto() {
        return new BloodPoolDto(getExternalId(), null, null, poolNumber, bloodInvoiceIds, productBatchExternalId, totalAmount);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "externalId='" + getExternalId() + '\'' +
                ", poolNumber=" + poolNumber +
                ", bloodInvoiceIds=" + (bloodInvoiceIds != null ?
                '[' + bloodInvoiceIds.stream().collect(Collectors.joining(", ")) + ']' : null) +
                ", totalAmount='" + totalAmount + '\'' +
                ", productBatchExternalId='" + productBatchExternalId + '\'' +
                '}';
    }
}
