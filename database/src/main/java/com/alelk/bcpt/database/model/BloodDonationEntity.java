package com.alelk.bcpt.database.model;

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

    @ManyToOne
    private PersonEntity donor;

    private Double amount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date donationDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date quarantineDate;

    public BloodDonationEntity() {}

    public BloodDonationEntity(String externalId, PersonEntity donor, Double amount, Date donationDate, Date quarantineDate) {
        super(externalId);
        this.donor = donor;
        this.amount = amount;
        this.donationDate = donationDate;
        this.quarantineDate = quarantineDate;
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

    @Override
    public String toString() {
        return "BloodDonationEntity{" +
                "id=" + getId() +
                ", externalId='" + getExternalId() +
                "', donorExternalId=" + (donor != null ? '\'' + donor.getExternalId() + '\'' : null) +
                ", amount=" + amount +
                ", donationDate=" + donationDate +
                ", quarantineDate=" + quarantineDate +
                ", creationTimestamp=" + getCreationTimestamp() +
                ", updateTimestamp=" + getUpdateTimestamp() +
                '}';
    }
}
