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
    private String bloodInvoiceSeries;
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

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getBloodInvoiceSeries() {
        return bloodInvoiceSeries;
    }

    public void setBloodInvoiceSeries(String bloodInvoiceSeries) {
        this.bloodInvoiceSeries = bloodInvoiceSeries;
    }

    @Override
    public BloodInvoiceDto toDto() {
        return new BloodInvoiceDto(getExternalId(), null, null, deliveryDate, bloodDonations, bloodInvoiceSeries, totalAmount);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "externalId='" + getExternalId() + '\'' +
                ", bloodInvoiceSeries='" + bloodInvoiceSeries + '\'' +
                ", deliveryDate='" + deliveryDate + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                ", bloodDonations=" + (bloodDonations != null
                ? '[' + bloodDonations.stream().collect(Collectors.joining(", ")) + ']' : null) +
                '}';
    }
}
