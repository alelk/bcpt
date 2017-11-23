package com.alelk.bcpt.database.model;

import com.alelk.bcpt.database.util.Sortable;

import javax.persistence.*;

import java.util.Set;

import static com.alelk.bcpt.database.model.BloodPoolAnalysisEntity.*;

/**
 * Blood Pool Analysis Entity
 *
 * Created by Alex Elkin on 23.11.2017.
 */
@Entity
@Table(name = "bloodPoolAnalysis")
@NamedQueries(value = {
        @NamedQuery(
                name = QUERY_FIND_ALL,
                query = "select bpa from BloodPoolAnalysisEntity bpa"
        ), @NamedQuery(
        name = QUERY_FIND_BY_PRODUCT_BATCH,
        query = "select bpa from BloodPoolEntity bpe inner join bpe.bloodPoolAnalysis bpa where bpe.productBatch=:" + PARAMETER_PRODUCT_BATCH
)})
public class BloodPoolAnalysisEntity extends AbstractEntity {
    public static final String QUERY_FIND_ALL = "findAllBloodPoolAnalysis";
    public static final String QUERY_FIND_BY_PRODUCT_BATCH = "findBloodPoolAnalysisByProductBatch";
    public static final String PARAMETER_PRODUCT_BATCH = "pBatch";

    @OneToOne
    @Sortable
    @JoinColumn(name = "bloodpoolanalysis_id")
    private BloodPoolEntity bloodPool;

    @Sortable
    private Double pH;

    @Sortable
    private Double proteinConcentration;

    public BloodPoolAnalysisEntity() {
    }

    public BloodPoolAnalysisEntity(String externalId, BloodPoolEntity bloodPool, Double pH, Double proteinConcentration) {
        super(externalId);
        this.bloodPool = bloodPool;
        this.pH = pH;
        this.proteinConcentration = proteinConcentration;
    }

    public BloodPoolEntity getBloodPool() {
        return bloodPool;
    }

    public void setBloodPool(BloodPoolEntity bloodPool) {
        this.bloodPool = bloodPool;
    }

    public Double getpH() {
        return pH;
    }

    public void setpH(Double pH) {
        this.pH = pH;
    }

    public Double getProteinConcentration() {
        return proteinConcentration;
    }

    public void setProteinConcentration(Double proteinConcentration) {
        this.proteinConcentration = proteinConcentration;
    }

    public ProductBatchEntity getProductBatch() {
        return bloodPool != null ? bloodPool.getProductBatch() : null;
    }

    public Set<BloodDonationEntity> getBloodDonations() {
        return bloodPool != null ? bloodPool.getBloodDonations() : null;
    }

    public Integer getPoolNumber() {
        return bloodPool != null ? bloodPool.getPoolNumber() : null;
    }

    @Override
    public String toString() {
        return "BloodPoolAnalysisEntity{" +
                "id=" + getId() +
                ", externalId=" + getExternalId() +
                ", bloodPool=" + (bloodPool != null ? bloodPool.getExternalId() : null) +
                ", pH=" + pH +
                ", proteinConcentration=" + proteinConcentration +
                '}';
    }
}
