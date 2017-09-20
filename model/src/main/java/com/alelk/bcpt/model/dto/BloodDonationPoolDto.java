package com.alelk.bcpt.model.dto;

import java.util.Date;

/**
 * Blood Donation Pool Dto
 *
 * Created by Alex Elkin on 12.09.2017.
 */
public class BloodDonationPoolDto extends AbstractBcptDto {

    private Integer poolNumber;
    private String[] bloodDonationDeliveryIds;

    public BloodDonationPoolDto(String externalId, Date creationTimestamp, Date updateTimestamp) {
        super(externalId, creationTimestamp, updateTimestamp);
    }

    public BloodDonationPoolDto(String externalId, Date creationTimestamp, Date updateTimestamp, Integer poolNumber, String[] bloodDonationDeliveryIds) {
        super(externalId, creationTimestamp, updateTimestamp);
        this.poolNumber = poolNumber;
        this.bloodDonationDeliveryIds = bloodDonationDeliveryIds;
    }

    public Integer getPoolNumber() {
        return poolNumber;
    }

    public String[] getBloodDonationDeliveryIds() {
        return bloodDonationDeliveryIds;
    }
}
