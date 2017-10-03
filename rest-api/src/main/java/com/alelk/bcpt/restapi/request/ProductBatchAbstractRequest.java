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

    private Date batchDate;
    private Set<String> bloodPoolIds;
    private Double totalAmount;

    public Date getBatchDate() {
        return batchDate;
    }

    public void setBatchDate(Date batchDate) {
        this.batchDate = batchDate;
    }

    public Set<String> getBloodPoolIds() {
        return bloodPoolIds;
    }

    public void setBloodPoolIds(Set<String> bloodPoolIds) {
        this.bloodPoolIds = bloodPoolIds;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public ProductBatchDto toDto() {
        return new ProductBatchDto(getExternalId(), null, null, batchDate, bloodPoolIds, totalAmount);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "externalId='" + getExternalId() + '\'' +
                ", batchDate=" + batchDate +
                ", totalAmount=" + totalAmount +
                ", bloodPoolIds=" + (bloodPoolIds != null ?
                '[' + bloodPoolIds.stream().collect(Collectors.joining(", ")) + ']' : null) +
                '}';
    }
}
