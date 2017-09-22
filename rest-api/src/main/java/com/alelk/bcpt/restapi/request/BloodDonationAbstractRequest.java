package com.alelk.bcpt.restapi.request;

import com.alelk.bcpt.model.dto.BloodDonationDto;

import java.util.Date;

/**
 * Blood Donation Abstract Request
 *
 * Created by Alex Elkin on 21.09.2017.
 */
public class BloodDonationAbstractRequest extends BcptDtoApiRequest<BloodDonationDto> {

    private String donorExternalId;
    private Double amount;
    private Date donationDate;
    private Date quarantineDate;

    public String getDonorExternalId() {
        return donorExternalId;
    }

    public void setDonorExternalId(String donorExternalId) {
        this.donorExternalId = donorExternalId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(Date donationDate) {
        this.donationDate = donationDate;
    }

    public Date getQuarantineDate() {
        return quarantineDate;
    }

    public void setQuarantineDate(Date quarantineDate) {
        this.quarantineDate = quarantineDate;
    }

    @Override
    public BloodDonationDto toDto() {
        return new BloodDonationDto(getExternalId(), null, null, donorExternalId, amount, donationDate, quarantineDate);
    }

    @Override
    public String toString() {
        return "BloodDonationAbstractRequest{" +
                "externalId='" + getExternalId() + '\'' +
                ", donorExternalId='" + donorExternalId + '\'' +
                ", amount=" + amount +
                ", donationDate=" + donationDate +
                ", quarantineDate=" + quarantineDate +
                '}';
    }
}
