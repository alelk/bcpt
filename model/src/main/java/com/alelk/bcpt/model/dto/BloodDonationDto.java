package com.alelk.bcpt.model.dto;

import java.util.Date;

/**
 * Blood Donation Dto
 *
 * Created by Alex Elkin on 12.09.2017.
 */
public class BloodDonationDto extends AbstractBcptDto {

    private String donorExternalId;
    private Double amount;
    private Date donationDate;
    private Date quarantineDate;

    public BloodDonationDto(String externalId, Date creationTimestamp, Date updateTimestamp) {
        super(externalId, creationTimestamp, updateTimestamp);
    }

    public BloodDonationDto(String externalId, Date creationTimestamp, Date updateTimestamp, String donorExternalId, Double amount, Date donationDate, Date quarantineDate) {
        super(externalId, creationTimestamp, updateTimestamp);
        this.donorExternalId = donorExternalId;
        this.amount = amount;
        this.donationDate = donationDate;
        this.quarantineDate = quarantineDate;
    }

    public void setDonorExternalId(String donorExternalId) {
        this.donorExternalId = donorExternalId;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setDonationDate(Date donationDate) {
        this.donationDate = donationDate;
    }

    public void setQuarantineDate(Date quarantineDate) {
        this.quarantineDate = quarantineDate;
    }

    public String getDonorExternalId() {
        return donorExternalId;
    }

    public Double getAmount() {
        return amount;
    }

    public Date getDonationDate() {
        return donationDate;
    }

    public Date getQuarantineDate() {
        return quarantineDate;
    }

    @Override
    public String toString() {
        return "BloodDonationDto{" +
                ", externalId='" + getExternalId() +
                "', donorExternalId=" + donorExternalId +
                ", amount=" + amount +
                ", donationDate=" + donationDate +
                ", quarantineDate=" + quarantineDate +
                ", creationTimestamp=" + getCreationTimestamp() +
                ", updateTimestamp=" + getUpdateTimestamp() +
                '}';
    }
}
