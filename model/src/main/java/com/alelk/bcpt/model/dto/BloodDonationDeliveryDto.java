package com.alelk.bcpt.model.dto;

import java.util.Date;

/**
 * Blood Donation Delivery Dto
 *
 * Created by Alex Elkin on 12.09.2017.
 */
public class BloodDonationDeliveryDto extends AbstractBcptDto {

    private Date deliveryDate;
    private String[] bloodDonationExternalIds;

    BloodDonationDeliveryDto(String externalId, Date creationTimestamp, Date updateTimestamp) {
        super(externalId, creationTimestamp, updateTimestamp);
    }

    public BloodDonationDeliveryDto(String externalId, Date creationTimestamp, Date updateTimestamp, Date deliveryDate, String[] bloodDonationExternalIds) {
        super(externalId, creationTimestamp, updateTimestamp);
        this.deliveryDate = deliveryDate;
        this.bloodDonationExternalIds = bloodDonationExternalIds;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public String[] getBloodDonationExternalIds() {
        return bloodDonationExternalIds;
    }
}
