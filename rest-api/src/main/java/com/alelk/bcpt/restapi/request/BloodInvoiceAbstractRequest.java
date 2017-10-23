package com.alelk.bcpt.restapi.request;

import com.alelk.bcpt.model.dto.BloodInvoiceDto;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Blood Invoice Abstract Request
 *
 * Created by Alex Elkin on 25.09.2017.
 */
@SuppressWarnings("unused")
public abstract class BloodInvoiceAbstractRequest extends BcptDtoApiRequest<BloodInvoiceDto> {

    private Date deliveryDate;
    private Set<String> bloodDonations;
    private String bloodPool;
    private Double totalAmount;

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Set<String> getBloodDonations() {
        return bloodDonations;
    }

    public void setBloodDonations(Set<String> bloodDonations) {
        this.bloodDonations = bloodDonations;
    }

    public String getBloodPool() {
        return bloodPool;
    }

    public void setBloodPool(String bloodPool) {
        this.bloodPool = bloodPool;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public BloodInvoiceDto toDto() {
        return new BloodInvoiceDto(getExternalId(), null, null, deliveryDate, bloodDonations, bloodPool, totalAmount);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "externalId='" + getExternalId() + '\'' +
                ", bloodPool='" + bloodPool + '\'' +
                ", deliveryDate='" + deliveryDate + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                ", bloodDonations=" + (bloodDonations != null
                ? '[' + bloodDonations.stream().collect(Collectors.joining(", ")) + ']' : null) +
                '}';
    }
}
