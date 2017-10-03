package com.alelk.bcpt.restapi.request;

import com.alelk.bcpt.model.dto.BloodDonationDto;

import java.util.Date;

/**
 * Blood Donation Abstract Request
 *
 * Created by Alex Elkin on 21.09.2017.
 */
@SuppressWarnings("unused")
public class BloodDonationAbstractRequest extends BcptDtoApiRequest<BloodDonationDto> {

    private String donorExternalId;
    private String bloodInvoiceExternalId;
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

    public String getBloodInvoiceExternalId() {
        return bloodInvoiceExternalId;
    }

    public void setBloodInvoiceExternalId(String bloodInvoiceExternalId) {
        this.bloodInvoiceExternalId = bloodInvoiceExternalId;
    }

    @Override
    public BloodDonationDto toDto() {
        return new BloodDonationDto(getExternalId(), null, null, donorExternalId, bloodInvoiceExternalId, amount, donationDate, quarantineDate);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "externalId='" + getExternalId() + '\'' +
                ", bloodInvoiceExternalId='" + bloodInvoiceExternalId + '\'' +
                ", donorExternalId='" + donorExternalId + '\'' +
                ", amount=" + amount +
                ", donationDate=" + donationDate +
                ", quarantineDate=" + quarantineDate +
                '}';
    }
}
