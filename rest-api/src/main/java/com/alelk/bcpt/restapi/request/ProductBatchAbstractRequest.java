package com.alelk.bcpt.restapi.request;

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

    @Override
    public ProductBatchDto toDto() {
        return new ProductBatchDto(getExternalId(), null, null, batchNumber, batchDate, bloodPools, totalAmount);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "externalId='" + getExternalId() + '\'' +
                ", batchNumber=" + batchNumber +
                ", batchDate=" + batchDate +
                ", totalAmount=" + totalAmount +
                ", bloodPools=" + (bloodPools != null ?
                '[' + bloodPools.stream().collect(Collectors.joining(", ")) + ']' : null) +
                '}';
    }
}
