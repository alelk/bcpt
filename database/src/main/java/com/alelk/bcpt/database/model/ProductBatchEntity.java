package com.alelk.bcpt.database.model;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static com.alelk.bcpt.database.model.ProductBatchEntity.*;

/**
 * Product Batch Entity
 *
 * Created by Alex Elkin on 11.09.2017.
 */
@Entity
@Table(name = "productBatches")
@NamedQueries(value = {
        @NamedQuery(
                name = QUERY_FIND_ALL,
                query = "select pbe from ProductBatchEntity pbe"
        )})
public class ProductBatchEntity extends AbstractEntity {
    public static final String QUERY_FIND_ALL = "findAllProductBatches";

    private Date batchDate;

    @OneToMany
    @JoinColumn(name = "productbatch_id")
    private Set<BloodPoolEntity> bloodPools;

    @Formula("(SELECT sum(donations.amount) from bloodDonations donations " +
            "JOIN  bloodInvoices invoices ON donations.bloodinvoice_id = invoices.id " +
            "JOIN bloodPools pools ON invoices.bloodpool_id = pools.id " +
            "WHERE pools.productbatch_id = id)")
    private Double totalAmount;

    public ProductBatchEntity() {
    }

    public ProductBatchEntity(String externalId, Date batchDate, Set<BloodPoolEntity> bloodPools) {
        super(externalId);
        this.batchDate = batchDate;
        this.bloodPools = bloodPools;
    }

    public Date getBatchDate() {
        return batchDate;
    }

    public void setBatchDate(Date batchDate) {
        this.batchDate = batchDate;
    }

    public Set<BloodPoolEntity> getBloodPools() {
        return bloodPools;
    }

    public void setBloodPools(Set<BloodPoolEntity> bloodPools) {
        this.bloodPools = bloodPools;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "ProductBatchEntity{" +
                "id=" + getId() +
                ", externalId='" + getExternalId() + '\'' +
                ", batchDate=" + batchDate +
                ", bloodPools=" + (bloodPools != null
                ? '[' + bloodPools.stream().map(AbstractEntity::getExternalId).collect(Collectors.joining(", ")) + ']'
                : null) +
                ", totalAmount=" + totalAmount +
                ", creationTimestamp=" + getCreationTimestamp() +
                ", updateTimestamp=" + getUpdateTimestamp() +
                '}';
    }
}
