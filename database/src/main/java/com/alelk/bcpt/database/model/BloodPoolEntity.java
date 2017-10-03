package com.alelk.bcpt.database.model;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;
import static com.alelk.bcpt.database.model.BloodPoolEntity.*;

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

    private Integer poolNumber;

    @ManyToOne
    private ProductBatchEntity productBatch;

    @OneToMany
    @JoinColumn(name = "bloodpool_id")
    private Set<BloodInvoiceEntity> bloodInvoices;

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
                ", bloodInvoices=" + (bloodInvoices != null
                ? '[' + bloodInvoices.stream().map(AbstractEntity::getExternalId).collect(Collectors.joining(", ")) + ']'
                : null)+
                ", productBatchExternalId='" + (productBatch != null ? productBatch.getExternalId() : null) + '\'' +
                ", creationTimestamp=" + getCreationTimestamp() +
                ", totalAmount=" + totalAmount +
                ", updateTimestamp=" + getUpdateTimestamp() +
                '}';
    }
}
