package com.alelk.bcpt.database.model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Blood Donation Pool Entity
 *
 * Created by Alex Elkin on 11.09.2017.
 */
@Entity
@Table(name = "bloodDonationPools")
public class BloodDonationPoolEntity extends AbstractEntity{

    private Integer poolNumber;

    @OneToMany
    private Set<BloodDonationDeliveryEntity> bloodDonationDeliveries;

    public BloodDonationPoolEntity() {
    }

    public BloodDonationPoolEntity(String externalId, Integer poolNumber, Set<BloodDonationDeliveryEntity> bloodDonationDeliveries) {
        super(externalId);
        this.poolNumber = poolNumber;
        this.bloodDonationDeliveries = bloodDonationDeliveries;
    }

    @Override
    public String toString() {
        return "BloodDonationPoolEntity{" +
                "id=" + getId() +
                ", externalId='" + getExternalId() + '\'' +
                ", poolNumber=" + poolNumber +
                ", bloodDonationDeliveries=" + (bloodDonationDeliveries != null
                ? '[' + bloodDonationDeliveries.stream().map(AbstractEntity::getExternalId).collect(Collectors.joining(", ")) + ']'
                : null)+
                ", creationTimestamp=" + getCreationTimestamp() +
                ", updateTimestamp=" + getUpdateTimestamp() +
                '}';
    }
}
