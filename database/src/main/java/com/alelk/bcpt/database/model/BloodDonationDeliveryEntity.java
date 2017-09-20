package com.alelk.bcpt.database.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Blood Donation Delivery Entity
 *
 * Created by Alex Elkin on 11.09.2017.
 */
@Entity
@Table (name = "bloodDontionDeliveries")
public class BloodDonationDeliveryEntity extends AbstractEntity {

    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveryDate;

    @OneToMany
    private Set<BloodDonationEntity> bloodDonations;

    BloodDonationDeliveryEntity() {}

    public BloodDonationDeliveryEntity(String externalId, Date deliveryDate, Set<BloodDonationEntity> bloodDonations) {
        super(externalId);
        this.deliveryDate = deliveryDate;
        this.bloodDonations = bloodDonations;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Set<BloodDonationEntity> getBloodDonations() {
        return bloodDonations;
    }

    public void setBloodDonations(Set<BloodDonationEntity> bloodDonations) {
        this.bloodDonations = bloodDonations;
    }

    @Override
    public String toString() {
        return "BloodDonationDeliveryEntity{" +
                "id=" + getId() +
                ", externalId='" + getExternalId() + '\'' +
                ", bloodDonations=" + (bloodDonations != null
                ? '[' + bloodDonations.stream().map(BloodDonationEntity::getExternalId).collect(Collectors.joining(", ")) + ']'
                : null) +
                ", creationTimestamp=" + getCreationTimestamp() +
                ", updateTimestamp=" + getUpdateTimestamp() +
                '}';
    }
}