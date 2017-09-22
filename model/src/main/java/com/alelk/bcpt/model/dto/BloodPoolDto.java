package com.alelk.bcpt.model.dto;

import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Blood Donation Pool Dto
 *
 * Created by Alex Elkin on 12.09.2017.
 */
public class BloodPoolDto extends AbstractBcptDto {

    private Integer poolNumber;
    private String[] bloodInvoiceIds;

    public BloodPoolDto() {}

    public BloodPoolDto(String externalId, Date creationTimestamp, Date updateTimestamp) {
        super(externalId, creationTimestamp, updateTimestamp);
    }

    public BloodPoolDto(String externalId, Date creationTimestamp, Date updateTimestamp, Integer poolNumber, String[] bloodInvoiceIds) {
        super(externalId, creationTimestamp, updateTimestamp);
        this.poolNumber = poolNumber;
        this.bloodInvoiceIds = bloodInvoiceIds;
    }

    public Integer getPoolNumber() {
        return poolNumber;
    }

    public String[] getBloodInvoiceIds() {
        return bloodInvoiceIds;
    }

    public void setPoolNumber(Integer poolNumber) {
        this.poolNumber = poolNumber;
    }

    public void setBloodInvoiceIds(String[] bloodInvoiceIds) {
        this.bloodInvoiceIds = bloodInvoiceIds;
    }

    @Override
    public String toString() {
        return "BloodPoolDto{" +
                "externalId='" + getExternalId() +
                ", poolNumber='" + poolNumber +
                ", bloodInvoiceIds=" + (bloodInvoiceIds != null
                ? '[' + Arrays.stream(bloodInvoiceIds).collect(Collectors.joining(", ")) + ']'
                : null) +
                ", creationTimestamp=" + getCreationTimestamp() +
                ", updateTimestamp=" + getUpdateTimestamp() +
                '}';
    }
}
