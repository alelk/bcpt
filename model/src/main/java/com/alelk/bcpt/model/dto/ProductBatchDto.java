package com.alelk.bcpt.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Product Batch Dto
 *
 * Created by Alex Elkin on 12.09.2017.
 */
public class ProductBatchDto extends AbstractBcptDto {

    private Date batchDate;
    private String[] bloodPoolIds;

    public ProductBatchDto() {}

    ProductBatchDto(String externalId, Date creationTimestamp, Date updateTimestamp) {
        super(externalId, creationTimestamp, updateTimestamp);
    }

    public ProductBatchDto(String externalId, Date creationTimestamp, Date updateTimestamp, Date batchDate, String[] bloodPoolIds) {
        super(externalId, creationTimestamp, updateTimestamp);
        this.batchDate = batchDate;
        this.bloodPoolIds = bloodPoolIds;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    public Date getBatchDate() {
        return batchDate;
    }

    public String[] getBloodPoolIds() {
        return bloodPoolIds;
    }

    public void setBatchDate(Date batchDate) {
        this.batchDate = batchDate;
    }

    public void setBloodPoolIds(String[] bloodPoolIds) {
        this.bloodPoolIds = bloodPoolIds;
    }

    @Override
    public String toString() {
        return "ProductBatchDto{" +
                "externalId='" + getExternalId() +
                ", batchDate='" + batchDate +
                ", bloodPoolIds=" + (bloodPoolIds != null
                ? '[' + Arrays.stream(bloodPoolIds).collect(Collectors.joining(", ")) + ']'
                : null) +
                ", creationTimestamp=" + getCreationTimestamp() +
                ", updateTimestamp=" + getUpdateTimestamp() +
                '}';
    }
}
