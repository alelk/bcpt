package com.alelk.bcpt.database.model;

import com.alelk.bcpt.database.util.Sortable;
import com.alelk.bcpt.model.util.Util;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.Set;

import static com.alelk.bcpt.database.model.BloodPoolEntity.QUERY_FIND_ALL;

/**
 * Blood Donation Pool Entity
 *
 * Created by Alex Elkin on 11.09.2017.
 */
@Entity
@Table(name = "bloodPools")
@NamedQueries(value = {
        @NamedQuery(
                name = QUERY_FIND_ALL,
                query = "select bdpe from BloodPoolEntity bdpe"
        )})
public class BloodPoolEntity extends AbstractEntity {
    public static final String QUERY_FIND_ALL = "findAllBloodPools";

    @Sortable
    private Integer poolNumber;

    @ManyToOne
    @Sortable
    private ProductBatchEntity productBatch;

    @OneToMany
    @JoinColumn(name = "bloodpool_id")
    private Set<BloodDonationEntity> bloodDonations;

    public BloodPoolEntity() {
    }

    public BloodPoolEntity(String externalId, Integer poolNumber, Set<BloodDonationEntity> bloodDonations) {
        super(externalId);
        this.poolNumber = poolNumber;
        this.bloodDonations = bloodDonations;
    }

    public Integer getPoolNumber() {
        return poolNumber;
    }

    public void setPoolNumber(Integer poolNumber) {
        this.poolNumber = poolNumber;
    }

    public Set<BloodDonationEntity> getBloodDonations() {
        return bloodDonations;
    }

    public void setBloodDonations(Set<BloodDonationEntity> bloodDonations) {
        this.bloodDonations = bloodDonations;
    }

    public ProductBatchEntity getProductBatch() {
        return productBatch;
    }

    public void setProductBatch(ProductBatchEntity productBatch) {
        this.productBatch = productBatch;
    }

    public Double getTotalAmount() {
        if (bloodDonations == null) return null;
        return bloodDonations.stream().mapToDouble(BloodDonationEntity::getAmount).sum();
    }

    @Override
    public String toString() {
        return "BloodPoolEntity{" +
                "id=" + getId() +
                ", externalId='" + getExternalId() + '\'' +
                ", poolNumber=" + poolNumber +
                ", bloodDonations=" + Util.toString(bloodDonations)+
                ", productBatchExternalId='" + (productBatch != null ? productBatch.getExternalId() : null) + '\'' +
                ", creationTimestamp=" + getCreationTimestamp() +
                ", updateTimestamp=" + getUpdateTimestamp() +
                '}';
    }
}
