package com.alelk.bcpt.database.model;

import com.alelk.bcpt.database.util.Sortable;
import com.alelk.bcpt.model.util.Util;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

import static com.alelk.bcpt.database.model.BloodInvoiceEntity.QUERY_FIND_ALL;

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
                query = "select bdde from BloodInvoiceEntity bdde"
        )})
public class BloodInvoiceEntity extends AbstractEntity {
    public static final String QUERY_FIND_ALL = "findAllBloodInvoices";

    @Sortable
    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveryDate;

    @OneToMany
    @JoinColumn(name = "bloodinvoice_id")
    private Set<BloodDonationEntity> bloodDonations;

    @Sortable
    @ManyToOne
    private BloodPoolEntity bloodPool;

    @Sortable
    //@Formula("(select sum(donations.amount) from bloodDonations donations where donations.bloodinvoice_id = id)")
    private Double totalAmount;

    protected BloodInvoiceEntity() {}

    public BloodInvoiceEntity(String externalId, Date deliveryDate, Set<BloodDonationEntity> bloodDonations) {
        super(externalId);
        this.deliveryDate = deliveryDate;
        this.bloodDonations = bloodDonations;
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

    public BloodPoolEntity getBloodPool() {
        return bloodPool;
    }

    public void setBloodPool(BloodPoolEntity bloodPool) {
        this.bloodPool = bloodPool;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    @PostLoad
    void calculateAmount() {
        totalAmount = 0.0;
        if (bloodDonations == null) return;
        for (BloodDonationEntity bde : bloodDonations) totalAmount += bde.getAmount();
    }

    @Override
    public String toString() {
        return "BloodInvoiceEntity{" +
                "id=" + getId() +
                ", externalId='" + getExternalId() + '\'' +
                ", bloodDonations=" + Util.toString(bloodDonations) +
                ", bloodPoolExternalId='" + (bloodPool != null ? bloodPool.getExternalId() : null) + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                ", creationTimestamp=" + getCreationTimestamp() +
                ", updateTimestamp=" + getUpdateTimestamp() +
                '}';
    }
}