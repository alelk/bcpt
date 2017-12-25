package com.alelk.bcpt.database.model;

import com.alelk.bcpt.common.util.StringUtil;
import com.alelk.bcpt.database.util.Sortable;
import com.alelk.bcpt.model.AnalysisConclusion;

import javax.persistence.*;
import java.util.Set;

import static com.alelk.bcpt.database.model.BloodPoolEntity.PARAMETER_PRODUCT_BATCH;
import static com.alelk.bcpt.database.model.BloodPoolEntity.QUERY_FIND_ALL;
import static com.alelk.bcpt.database.model.BloodPoolEntity.QUERY_FIND_BY_PRODUCT_BATCH;
import static com.alelk.bcpt.database.util.RepositoryUtil.processAnalysisConclusion;

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
                query = "select bpe from BloodPoolEntity bpe"
        ), @NamedQuery(
        name = QUERY_FIND_BY_PRODUCT_BATCH,
        query = "select bpe from BloodPoolEntity bpe where bpe.productBatch=:" + PARAMETER_PRODUCT_BATCH
)})
public class BloodPoolEntity extends AbstractEntity {
    public static final String QUERY_FIND_ALL = "findAllBloodPools";
    public static final String QUERY_FIND_BY_PRODUCT_BATCH = "findBloodPoolsByProductBatch";
    public static final String PARAMETER_PRODUCT_BATCH = "pBatch";

    @Sortable
    private Integer poolNumber;

    @ManyToOne
    @Sortable
    private ProductBatchEntity productBatch;

    @OneToMany
    @JoinColumn(name = "bloodpool_id")
    private Set<BloodDonationEntity> bloodDonations;

    @OneToOne
    private BloodPoolAnalysisEntity bloodPoolAnalysis;

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

    public AnalysisConclusion getAnalysisConclusion() {
        return bloodDonations == null ? null :
                processAnalysisConclusion(bloodDonations.stream().map(BloodDonationEntity::getAnalysisConclusion));
    }

    @Override
    public String toString() {
        return "BloodPoolEntity{" +
                "id=" + getId() +
                ", externalId='" + getExternalId() + '\'' +
                ", poolNumber=" + poolNumber +
                ", bloodDonations=" + StringUtil.toString(bloodDonations)+
                ", productBatchExternalId='" + (productBatch != null ? productBatch.getExternalId() : null) + '\'' +
                ", creationTimestamp=" + getCreationTimestamp() +
                ", updateTimestamp=" + getUpdateTimestamp() +
                '}';
    }
}
