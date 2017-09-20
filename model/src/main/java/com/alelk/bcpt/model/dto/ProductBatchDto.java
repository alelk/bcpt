package com.alelk.bcpt.model.dto;

import java.util.Date;

/**
 * Product Batch Dto
 *
 * Created by Alex Elkin on 12.09.2017.
 */
public class ProductBatchDto extends AbstractBcptDto {

    private Date batchDate;
    private String[] bloodDonationPoolIds;

    ProductBatchDto(String externalId, Date creationTimestamp, Date updateTimestamp) {
        super(externalId, creationTimestamp, updateTimestamp);
    }

    public ProductBatchDto(String externalId, Date creationTimestamp, Date updateTimestamp, Date batchDate, String[] bloodDonationPoolIds) {
        super(externalId, creationTimestamp, updateTimestamp);
        this.batchDate = batchDate;
        this.bloodDonationPoolIds = bloodDonationPoolIds;
    }

    public Date getBatchDate() {
        return batchDate;
    }

    public String[] getBloodDonationPoolIds() {
        return bloodDonationPoolIds;
    }
}
