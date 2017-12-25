package com.alelk.bcpt.model.dto;

import com.alelk.bcpt.model.AnalysisConclusion;
import com.alelk.bcpt.model.BloodType;
import com.alelk.bcpt.model.DonationType;
import com.alelk.bcpt.model.RhFactor;
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
    private String bloodPool;
    private Double amount;
    private Date donationDate;
    private Date expirationDate;
    private Date quarantineDate;
    private DonationType donationType;
    private BloodType bloodType;
    private RhFactor rhFactor;
    private AnalysisConclusion analysisConclusion;

    public BloodDonationDto() {}

    public BloodDonationDto(String externalId, Date creationTimestamp, Date updateTimestamp) {
        super(externalId, creationTimestamp, updateTimestamp);
    }

    public BloodDonationDto(String externalId, Date creationTimestamp, Date updateTimestamp,
                            String donor, String bloodInvoiceExternalId, String bloodPool, Double amount,
                            Date donationDate, Date quarantineDate, Date expirationDate, DonationType donationType, BloodType bloodType, RhFactor rhFactor, AnalysisConclusion analysisConclusion) {
        super(externalId, creationTimestamp, updateTimestamp);
        this.donor = donor;
        this.bloodInvoice = bloodInvoiceExternalId;
        this.bloodPool = bloodPool;
        this.amount = amount;
        this.donationDate = donationDate;
        this.quarantineDate = quarantineDate;
        this.expirationDate = expirationDate;
        this.donationType = donationType;
        this.bloodType = bloodType;
        this.rhFactor = rhFactor;
        this.analysisConclusion = analysisConclusion;
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

    public String getBloodPool() {
        return bloodPool;
    }

    public void setBloodPool(String bloodPool) {
        this.bloodPool = bloodPool;
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

    public BloodType getBloodType() {
        return bloodType;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public RhFactor getRhFactor() {
        return rhFactor;
    }

    public void setRhFactor(RhFactor rhFactor) {
        this.rhFactor = rhFactor;
    }

    public AnalysisConclusion getAnalysisConclusion() {
        return analysisConclusion;
    }

    public void setAnalysisConclusion(AnalysisConclusion analysisConclusion) {
        this.analysisConclusion = analysisConclusion;
    }

    @Override
    public String toString() {
        return "BloodDonationDto{" +
                "externalId='" + getExternalId() +
                "', donor=" + donor +
                "', bloodInvoice=" + bloodInvoice +
                "', bloodPool=" + bloodPool +
                ", amount=" + amount +
                ", donationDate=" + donationDate +
                ", quarantineDate=" + quarantineDate +
                ", donationType=" + donationType +
                ", expirationDate=" + expirationDate +
                ", bloodType=" + bloodType +
                ", rhFactor=" + rhFactor +
                ", analysisConclusion=" + analysisConclusion +
                ", creationTimestamp=" + getCreationTimestamp() +
                ", updateTimestamp=" + getUpdateTimestamp() +
                '}';
    }
}
