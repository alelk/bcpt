package com.alelk.bcpt.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Blood Donation Delivery Dto
 *
 * Created by Alex Elkin on 12.09.2017.
 */
public class BloodInvoiceDto extends AbstractBcptDto {

    private Date deliveryDate;
    private Set<String> bloodDonationExternalIds;
    private String bloodPoolExternalId;
    private Double totalAmount;

    public BloodInvoiceDto() {}

    public BloodInvoiceDto(String externalId, Date creationTimestamp, Date updateTimestamp) {
        super(externalId, creationTimestamp, updateTimestamp);
    }

    public BloodInvoiceDto(String externalId, Date creationTimestamp, Date updateTimestamp, Date deliveryDate, Set<String> bloodDonationExternalIds, String bloodPoolExternalId, Double totalAmount) {
        super(externalId, creationTimestamp, updateTimestamp);
        this.deliveryDate = deliveryDate;
        this.bloodDonationExternalIds = bloodDonationExternalIds;
        this.bloodPoolExternalId = bloodPoolExternalId;
        this.totalAmount = totalAmount;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public Set<String> getBloodDonationExternalIds() {
        return bloodDonationExternalIds;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
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
        this.totalAmount = totalAmount == null ? null : new BigDecimal(totalAmount).setScale(2, RoundingMode.FLOOR).doubleValue();
    }

    @Override
    public String toString() {
        return "BloodInvoiceDto{" +
                "externalId='" + getExternalId() +
                ", deliveryDate=" + deliveryDate +
                ", bloodDonationExternalIds=" + (bloodDonationExternalIds != null
                ? '[' + bloodDonationExternalIds.stream().collect(Collectors.joining(", ")) + ']'
                : null) +
                ", bloodPoolExternalId='" + getBloodPoolExternalId() + '\'' +
                ", totalAmount='" + getTotalAmount() + '\'' +
                ", creationTimestamp=" + getCreationTimestamp() +
                ", updateTimestamp=" + getUpdateTimestamp() +
                '}';
    }
}
