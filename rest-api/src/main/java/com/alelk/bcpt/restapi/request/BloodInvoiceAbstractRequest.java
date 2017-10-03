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
    private Set<String> bloodDonationExternalIds;
    private String bloodPoolExternalId;
    private Double totalAmount;

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Set<String> getBloodDonationExternalIds() {
        return bloodDonationExternalIds;
    }

    public void setBloodDonationExternalIds(Set<String> bloodDonationExternalIds) {
        this.bloodDonationExternalIds = bloodDonationExternalIds;
    }

    public String getBloodPoolExternalId() {
        return bloodPoolExternalId;
    }

    public void setBloodPoolExternalId(String bloodPoolExternalId) {
        this.bloodPoolExternalId = bloodPoolExternalId;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public BloodInvoiceDto toDto() {
        return new BloodInvoiceDto(getExternalId(), null, null, deliveryDate, bloodDonationExternalIds, bloodPoolExternalId, totalAmount);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "externalId='" + getExternalId() + '\'' +
                ", bloodPoolExternalId='" + bloodPoolExternalId + '\'' +
                ", deliveryDate='" + deliveryDate + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                ", bloodDonationExternalIds=" + (bloodDonationExternalIds != null
                ? '[' + bloodDonationExternalIds.stream().collect(Collectors.joining(", ")) + ']' : null) +
                '}';
    }
}
