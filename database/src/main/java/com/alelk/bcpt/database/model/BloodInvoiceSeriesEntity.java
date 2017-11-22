package com.alelk.bcpt.database.model;

import com.alelk.bcpt.database.util.Sortable;

import javax.persistence.*;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import static com.alelk.bcpt.database.model.BloodInvoiceSeriesEntity.*;

/**
 * Blood Series Entity
 *
 * Created by Alex Elkin on 22.11.2017.
 */
@Entity
@Table(name = "bloodInvoiceSeries")
@NamedQueries(value = {
        @NamedQuery(
                name = QUERY_FIND_ALL,
                query = "select bi from BloodInvoiceEntity bi"
        ),
        @NamedQuery(
                name = QUERY_FIND_BY_PRODUCT_BATCH,
                query = "select distinct bis from BloodPoolEntity as bp " +
                        "inner join bp.bloodDonations as bd inner join bd.bloodInvoice as bi " +
                        "inner join bi.bloodInvoiceSeries as bis where bp.productBatch=:" + PARAMETER_PRODUCT_BATCH
        )})
public class BloodInvoiceSeriesEntity extends AbstractEntity {
    public static final String QUERY_FIND_ALL = "findAllBloodInvoiceSeries";
    public static final String QUERY_FIND_BY_PRODUCT_BATCH = "findAllBloodInvoiceSeriesByProductBatch";
    public static final String PARAMETER_PRODUCT_BATCH = "pBatch";

    @OneToMany
    @JoinColumn(name = "bloodinvoiceseries_id")
    private Set<BloodInvoiceEntity> bloodInvoices;

    @Sortable
    @Temporal(TemporalType.TIMESTAMP)
    private Date seriesDate;

    public BloodInvoiceSeriesEntity() {
    }

    public Set<BloodInvoiceEntity> getBloodInvoices() {
        return bloodInvoices;
    }

    public void setBloodInvoices(Set<BloodInvoiceEntity> bloodInvoices) {
        this.bloodInvoices = bloodInvoices;
    }

    public Date getSeriesDate() {
        return seriesDate;
    }

    public void setSeriesDate(Date seriesDate) {
        this.seriesDate = seriesDate;
    }

    public Double getTotalAmount() {
        if (bloodInvoices == null) return null;
        return bloodInvoices.stream().mapToDouble(BloodInvoiceEntity::getTotalAmount).sum();
    }

    @Override
    public String toString() {
        return "BloodInvoiceSeriesEntity{" +
                "bloodInvoices=[" + (bloodInvoices != null
                ? bloodInvoices.stream().map(BloodInvoiceEntity::getExternalId).collect(Collectors.joining(", "))
                : "") +
                "], seriesDate=" + seriesDate +
                '}';
    }
}
