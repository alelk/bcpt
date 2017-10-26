package com.alelk.bcpt.restapi.request;

import com.alelk.bcpt.model.DonationType;
import com.alelk.bcpt.model.dto.BloodDonationDto;

import java.util.Date;

/**
 * Blood Donation Abstract Request
 *
 * Created by Alex Elkin on 21.09.2017.
 */
@SuppressWarnings("unused")
public class BloodDonationAbstractRequest extends BcptDtoApiRequest<BloodDonationDto> {

    private String donor;
    private String bloodInvoice;
    private String bloodPool;
    private Double amount;
    private Date donationDate;
    private Date quarantineDate;
    private Date expirationDate;
    private DonationType donationType;

    public String getDonor() {
        return donor;
    }

    public void setDonor(String donor) {
        this.donor = donor;
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

    public String getBloodInvoice() {
        return bloodInvoice;
    }

    public void setBloodInvoice(String bloodInvoice) {
        this.bloodInvoice = bloodInvoice;
    }

    public String getBloodPool() {
        return bloodPool;
    }

    public void setBloodPool(String bloodPool) {
        this.bloodPool = bloodPool;
    }

    @Override
    public BloodDonationDto toDto() {
        return new BloodDonationDto(getExternalId(), null, null, donor,
                bloodInvoice, bloodPool, amount, donationDate, quarantineDate, expirationDate, donationType);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "externalId='" + getExternalId() + '\'' +
                ", bloodInvoice='" + bloodInvoice + '\'' +
                ", bloodPool='" + bloodPool + '\'' +
                ", donor='" + donor + '\'' +
                ", amount=" + amount +
                ", donationType=" + donationType +
                ", donationDate=" + donationDate +
                ", expirationDate=" + expirationDate +
                ", quarantineDate=" + quarantineDate +
                '}';
    }
}
