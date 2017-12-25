package com.alelk.bcpt.database.model;

import com.alelk.bcpt.common.util.StringUtil;
import com.alelk.bcpt.database.util.Sortable;
import com.alelk.bcpt.model.AnalysisConclusion;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import static com.alelk.bcpt.database.model.BloodInvoiceEntity.PARAMETER_PRODUCT_BATCH;
import static com.alelk.bcpt.database.model.BloodInvoiceEntity.QUERY_FIND_ALL;
import static com.alelk.bcpt.database.model.BloodInvoiceEntity.QUERY_FIND_BY_PRODUCT_BATCH;
import static com.alelk.bcpt.database.util.RepositoryUtil.processAnalysisConclusion;

/**
 * Blood Donation Delivery Entity
 *
 * Created by Alex Elkin on 11.09.2017.
 */
@Entity
@Table (name = "bloodInvoices")
@NamedQueries(value = {
        @NamedQuery(
                name = QUERY_FIND_ALL,
                query = "select bi from BloodInvoiceEntity bi"
        ),
        @NamedQuery(
                name = QUERY_FIND_BY_PRODUCT_BATCH,
                query = "select distinct bi from BloodPoolEntity as bp " +
                        "inner join bp.bloodDonations as bd inner join bd.bloodInvoice as bi where bp.productBatch=:" + PARAMETER_PRODUCT_BATCH
        )})
public class BloodInvoiceEntity extends AbstractEntity {
    public static final String QUERY_FIND_ALL = "findAllBloodInvoices";
    public static final String QUERY_FIND_BY_PRODUCT_BATCH = "findAllBloodInvoicesByProductBatch";
    public static final String PARAMETER_PRODUCT_BATCH = "pBatch";

    @Sortable
    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveryDate;

    @OneToMany
    @JoinColumn(name = "bloodinvoice_id")
    private Set<BloodDonationEntity> bloodDonations;

    @Sortable
    @ManyToOne
    private BloodInvoiceSeriesEntity bloodInvoiceSeries;

    protected BloodInvoiceEntity() {}

    public BloodInvoiceEntity(String externalId, Date deliveryDate, Set<BloodDonationEntity> bloodDonations, BloodInvoiceSeriesEntity bloodInvoiceSeries) {
        super(externalId);
        this.deliveryDate = deliveryDate;
        this.bloodDonations = bloodDonations;
        this.bloodInvoiceSeries = bloodInvoiceSeries;
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

    public Double getTotalAmount() {
        if (bloodDonations == null) return null;
        return bloodDonations.stream().mapToDouble(bde -> bde.getAmount() != null ? bde.getAmount() : 0).sum();
    }

    public AnalysisConclusion getAnalysisConclusion() {
        return bloodDonations == null ? null :
                processAnalysisConclusion(bloodDonations.stream().map(BloodDonationEntity::getAnalysisConclusion));
    }

    public BloodInvoiceSeriesEntity getBloodInvoiceSeries() {
        return bloodInvoiceSeries;
    }

    public void setBloodInvoiceSeries(BloodInvoiceSeriesEntity bloodInvoiceSeries) {
        this.bloodInvoiceSeries = bloodInvoiceSeries;
    }

    @Override
    public String toString() {
        return "BloodInvoiceEntity{" +
                "id=" + getId() +
                ", externalId='" + getExternalId() + '\'' +
                ", bloodDonations=" + StringUtil.toString(bloodDonations) +
                ", creationTimestamp=" + getCreationTimestamp() +
                ", updateTimestamp=" + getUpdateTimestamp() +
                ", bloodInvoiceSeries=" + (bloodInvoiceSeries != null ? bloodInvoiceSeries.getExternalId() : null) +
                '}';
    }
}