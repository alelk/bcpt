package com.alelk.bcpt.model.dto;

import com.alelk.bcpt.model.DonationType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Blood Donation Dto
 *
 * Created by Alex Elkin on 12.09.2017.
 */
public class BloodDonationDto extends AbstractBcptDto {

    private String donor;
    private String bloodInvoice;
    private Double amount;
    private Date donationDate;
    private Date expirationDate;
    private Date quarantineDate;
    private DonationType donationType;

    public BloodDonationDto() {}

    public BloodDonationDto(String externalId, Date creationTimestamp, Date updateTimestamp) {
        super(externalId, creationTimestamp, updateTimestamp);
    }

    public BloodDonationDto(String externalId, Date creationTimestamp, Date updateTimestamp,
                            String donor, String bloodInvoiceExternalId, Double amount,
                            Date donationDate, Date quarantineDate, Date expirationDate, DonationType donationType) {
        super(externalId, creationTimestamp, updateTimestamp);
        this.donor = donor;
        this.bloodInvoice = bloodInvoiceExternalId;
        this.amount = amount;
        this.donationDate = donationDate;
        this.quarantineDate = quarantineDate;
        this.expirationDate = expirationDate;
        this.donationType = donationType;
    }

    public void setDonor(String donor) {
        this.donor = donor;
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

    public String getDonor() {
        return donor;
    }

    public Double getAmount() {
        return amount;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public DonationType getDonationType() {
        return donationType;
    }

    public void setDonationType(DonationType donationType) {
        this.donationType = donationType;
    }

    public String getBloodInvoice() {
        return bloodInvoice;
    }

    public void setBloodInvoice(String bloodInvoice) {
        this.bloodInvoice = bloodInvoice;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    public Date getDonationDate() {
        return donationDate;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    public Date getQuarantineDate() {
        return quarantineDate;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    public Date getExpirationDate() {
        return expirationDate;
    }

    @Override
    public String toString() {
        return "BloodDonationDto{" +
                "externalId='" + getExternalId() +
                "', donor=" + donor +
                "', bloodInvoice=" + bloodInvoice +
                ", amount=" + amount +
                ", donationDate=" + donationDate +
                ", quarantineDate=" + quarantineDate +
                ", donationType=" + donationType +
                ", expirationDate=" + expirationDate +
                ", creationTimestamp=" + getCreationTimestamp() +
                ", updateTimestamp=" + getUpdateTimestamp() +
                '}';
    }
}
