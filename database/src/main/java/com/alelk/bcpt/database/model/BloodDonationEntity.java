package com.alelk.bcpt.database.model;

import com.alelk.bcpt.database.util.Sortable;
import com.alelk.bcpt.model.DonationType;

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
    private Double amount;

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

    public BloodDonationEntity() {}

    public BloodDonationEntity(String externalId, PersonEntity donor, Double amount, DonationType donationType, Date donationDate, Date quarantineDate, Date expirationDate) {
        super(externalId);
        this.donor = donor;
        this.amount = amount;
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

    public DonationType getDonationType() {
        return donationType;
    }

    public void setDonationType(DonationType donationType) {
        this.donationType = donationType;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "BloodDonationEntity{" +
                "id=" + getId() +
                ", externalId='" + getExternalId() +
                "', donorExternalId=" + (donor != null ? '\'' + donor.getExternalId() + '\'' : null) +
                "', bloodInvoiceExternalId=" + (bloodInvoice != null ? '\'' + bloodInvoice.getExternalId() + '\'' : null) +
                ", amount=" + amount +
                ", donationType=" + donationType +
                ", donationDate=" + donationDate +
                ", expirationDate=" + expirationDate +
                ", quarantineDate=" + quarantineDate +
                ", creationTimestamp=" + getCreationTimestamp() +
                ", updateTimestamp=" + getUpdateTimestamp() +
                '}';
    }
}
