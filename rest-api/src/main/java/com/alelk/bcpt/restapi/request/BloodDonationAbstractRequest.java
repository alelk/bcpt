package com.alelk.bcpt.restapi.request;

import com.alelk.bcpt.model.AnalysisConclusion;
import com.alelk.bcpt.model.BloodType;
import com.alelk.bcpt.model.DonationType;
import com.alelk.bcpt.model.RhFactor;
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
    private BloodType bloodType;
    private RhFactor rhFactor;
    private AnalysisConclusion analysisConclusion;

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

    public Date getExpirationDate() {
        return expirationDate;
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
    public BloodDonationDto toDto() {
        return new BloodDonationDto(getExternalId(), null, null, donor,
                bloodInvoice, bloodPool, amount, donationDate, quarantineDate, expirationDate, donationType, bloodType, rhFactor, analysisConclusion);
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
                ", bloodType=" + bloodType +
                ", rhFactor=" + rhFactor +
                ", analysisConclusion=" + analysisConclusion +
                ", donationDate=" + donationDate +
                ", expirationDate=" + expirationDate +
                ", quarantineDate=" + quarantineDate +
                '}';
    }
}
