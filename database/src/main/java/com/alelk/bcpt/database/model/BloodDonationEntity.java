package com.alelk.bcpt.database.model;

import com.alelk.bcpt.database.util.Sortable;
import com.alelk.bcpt.model.AnalysisConclusion;
import com.alelk.bcpt.model.BloodType;
import com.alelk.bcpt.model.DonationType;
import com.alelk.bcpt.model.RhFactor;

import javax.persistence.*;
import java.util.Date;
import static com.alelk.bcpt.database.model.BloodDonationEntity.*;

/**
 * Blood Container Entity
 *
 * Created by Alex Elkin on 07.09.2017.
 */

@Entity
@Table (name = "bloodDonations")
@NamedQueries(value = {
        @NamedQuery(
                name = QUERY_FIND_ALL,
                query = "select bde from BloodDonationEntity bde"
        ), @NamedQuery(
                name = QUERY_FIND_BY_DONOR,
                query = "select bde from BloodDonationEntity bde where bde.donor = :" + PARAMETER_DONOR
)})
public class BloodDonationEntity extends AbstractEntity {

    public static final String QUERY_FIND_ALL = "findAllBloodDonations";
    public static final String QUERY_FIND_BY_DONOR = "findBloodDonationsByDonor";
    public static final String PARAMETER_DONOR = "donorId";

    @Sortable
    @ManyToOne
    private PersonEntity donor;

    @Sortable
    @ManyToOne
    private BloodInvoiceEntity bloodInvoice;

    @Sortable
    @ManyToOne
    private BloodPoolEntity bloodPool;

    @Sortable
    private Double amount;

    @Sortable
    private AnalysisConclusion analysisConclusion;

    @Sortable
    private DonationType donationType;

    @Sortable
    @Temporal(TemporalType.TIMESTAMP)
    private Date donationDate;

    @Sortable
    @Temporal(TemporalType.TIMESTAMP)
    private Date quarantineDate;

    @Sortable
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;

    public BloodDonationEntity() {
    }

    public BloodDonationEntity(String externalId, PersonEntity donor, BloodPoolEntity bloodPool, Double amount, AnalysisConclusion analysisConclusion, DonationType donationType, Date donationDate, Date quarantineDate, Date expirationDate) {
        super(externalId);
        this.donor = donor;
        this.bloodPool = bloodPool;
        this.amount = amount;
        this.analysisConclusion = analysisConclusion;
        this.donationType = donationType;
        this.donationDate = donationDate;
        this.quarantineDate = quarantineDate;
        this.expirationDate = expirationDate;
    }

    public PersonEntity getDonor() {
        return donor;
    }

    public void setDonor(PersonEntity donor) {
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

    public BloodInvoiceEntity getBloodInvoice() {
        return bloodInvoice;
    }

    public void setBloodInvoice(BloodInvoiceEntity bloodInvoice) {
        this.bloodInvoice = bloodInvoice;
    }

    public BloodPoolEntity getBloodPool() {
        return bloodPool;
    }

    public void setBloodPool(BloodPoolEntity bloodPool) {
        this.bloodPool = bloodPool;
    }

    public DonationType getDonationType() {
        return donationType;
    }

    public void setDonationType(DonationType donationType) {
        this.donationType = donationType;
    }

    public AnalysisConclusion getAnalysisConclusion() {
        return analysisConclusion;
    }

    public void setAnalysisConclusion(AnalysisConclusion analysisConclusion) {
        this.analysisConclusion = analysisConclusion;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public BloodType getBloodType() {
        return donor != null ? donor.getBloodType() : null;
    }

    public RhFactor getRhFactor() {
        return donor != null ? donor.getRhFactor() : null;
    }

    @Override
    public String toString() {
        return "BloodDonationEntity{" +
                "id=" + getId() +
                ", externalId='" + getExternalId() +
                "', donor=" + (donor != null ? '\'' + donor.getExternalId() + '\'' : null) +
                "', bloodInvoice=" + (bloodInvoice != null ? '\'' + bloodInvoice.getExternalId() + '\'' : null) +
                "', bloodPool=" + (bloodPool != null ? '\'' + bloodPool.getExternalId() + '\'' : null) +
                ", amount=" + amount +
                ", analysisConclusion=" + analysisConclusion +
                ", donationType=" + donationType +
                ", donationDate=" + donationDate +
                ", expirationDate=" + expirationDate +
                ", quarantineDate=" + quarantineDate +
                ", creationTimestamp=" + getCreationTimestamp() +
                ", updateTimestamp=" + getUpdateTimestamp() +
                '}';
    }
}
