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
    @OrderBy("externalId ASC")
    private Set<BloodInvoiceEntity> bloodInvoices;

    @Sortable
    @Formula("(SELECT sum(donations.amount) from bloodDonations donations JOIN  bloodInvoices invoices " +
            "ON donations.bloodinvoice_id = invoices.id WHERE invoices.bloodpool_id = id)")
    private Double totalAmount;

    public BloodPoolEntity() {
    }

    public BloodPoolEntity(String externalId, Integer poolNumber, Set<BloodInvoiceEntity> bloodInvoices) {
        super(externalId);
        this.poolNumber = poolNumber;
        this.bloodInvoices = bloodInvoices;
    }

    public Integer getPoolNumber() {
        return poolNumber;
    }

    public void setPoolNumber(Integer poolNumber) {
        this.poolNumber = poolNumber;
    }

    public Set<BloodInvoiceEntity> getBloodInvoices() {
        return bloodInvoices;
    }

    public void setBloodInvoices(Set<BloodInvoiceEntity> bloodInvoices) {
        this.bloodInvoices = bloodInvoices;
    }

    public ProductBatchEntity getProductBatch() {
        return productBatch;
    }

    public void setProductBatch(ProductBatchEntity productBatch) {
        this.productBatch = productBatch;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "BloodPoolEntity{" +
                "id=" + getId() +
                ", externalId='" + getExternalId() + '\'' +
                ", poolNumber=" + poolNumber +
                ", bloodInvoices=" + Util.toString(bloodInvoices)+
                ", productBatchExternalId='" + (productBatch != null ? productBatch.getExternalId() : null) + '\'' +
                ", creationTimestamp=" + getCreationTimestamp() +
                ", totalAmount=" + totalAmount +
                ", updateTimestamp=" + getUpdateTimestamp() +
                '}';
    }
}
