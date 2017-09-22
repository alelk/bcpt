package com.alelk.bcpt.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Blood Donation Delivery Dto
 *
 * Created by Alex Elkin on 12.09.2017.
 */
public class BloodInvoiceDto extends AbstractBcptDto {

    private Date deliveryDate;
    private String[] bloodDonationExternalIds;

    public BloodInvoiceDto() {}

    public BloodInvoiceDto(String externalId, Date creationTimestamp, Date updateTimestamp) {
        super(externalId, creationTimestamp, updateTimestamp);
    }

    public BloodInvoiceDto(String externalId, Date creationTimestamp, Date updateTimestamp, Date deliveryDate, String[] bloodDonationExternalIds) {
        super(externalId, creationTimestamp, updateTimestamp);
        this.deliveryDate = deliveryDate;
        this.bloodDonationExternalIds = bloodDonationExternalIds;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public String[] getBloodDonationExternalIds() {
        return bloodDonationExternalIds;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setBloodDonationExternalIds(String[] bloodDonationExternalIds) {
        this.bloodDonationExternalIds = bloodDonationExternalIds;
    }

    @Override
    public String toString() {
        return "BloodInvoiceDto{" +
                "externalId='" + getExternalId() +
                ", deliveryDate=" + deliveryDate +
                ", bloodDonationExternalIds=" + (bloodDonationExternalIds != null
                ? '[' + Arrays.stream(bloodDonationExternalIds).collect(Collectors.joining(", ")) + ']'
                : null) +
                ", creationTimestamp=" + getCreationTimestamp() +
                ", updateTimestamp=" + getUpdateTimestamp() +
                '}';
    }
}
