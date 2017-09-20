package com.alelk.bcpt.database.model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Product Batch Entity
 *
 * Created by Alex Elkin on 11.09.2017.
 */
@Entity
@Table(name = "productBatches")
public class ProductBatchEntity extends AbstractEntity {

    private Date batchDate;

    @OneToMany
    private Set<BloodDonationPoolEntity> bloodDonationPools;

    public ProductBatchEntity() {
    }

    public ProductBatchEntity(String externalId, Date batchDate, Set<BloodDonationPoolEntity> bloodDonationPools) {
        super(externalId);
        this.batchDate = batchDate;
        this.bloodDonationPools = bloodDonationPools;
    }

    @Override
    public String toString() {
        return "ProductBatchEntity{" +
                "id=" + getId() +
                ", externalId='" + getExternalId() + '\'' +
                ", batchDate=" + batchDate +
                ", bloodDonationPools=" + (bloodDonationPools != null
                ? '[' + bloodDonationPools.stream().map(AbstractEntity::getExternalId).collect(Collectors.joining(", ")) + ']'
                : null) +
                ", creationTimestamp=" + getCreationTimestamp() +
                ", updateTimestamp=" + getUpdateTimestamp() +
                '}';
    }
}
