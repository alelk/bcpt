package com.alelk.bcpt.model.dto;

import com.alelk.bcpt.model.AnalysisConclusion;
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
    private Set<String> bloodDonations;
    private String bloodInvoiceSeries;
    private Double totalAmount;
    private AnalysisConclusion analysisConclusion;

    public BloodInvoiceDto() {}

    public BloodInvoiceDto(String externalId, Date creationTimestamp, Date updateTimestamp) {
        super(externalId, creationTimestamp, updateTimestamp);
    }

    public BloodInvoiceDto(String externalId, Date creationTimestamp, Date updateTimestamp, Date deliveryDate, Set<String> bloodDonations, String bloodInvoiceSeries, Double totalAmount, AnalysisConclusion analysisConclusion) {
        super(externalId, creationTimestamp, updateTimestamp);
        this.deliveryDate = deliveryDate;
        this.bloodDonations = bloodDonations;
        this.bloodInvoiceSeries = bloodInvoiceSeries;
        this.totalAmount = totalAmount;
        this.analysisConclusion = analysisConclusion;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public Set<String> getBloodDonations() {
        return bloodDonations;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setBloodDonations(Set<String> bloodDonations) {
        this.bloodDonations = bloodDonations;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount == null ? null : new BigDecimal(totalAmount).setScale(2, RoundingMode.FLOOR).doubleValue();
    }

    public String getBloodInvoiceSeries() {
        return bloodInvoiceSeries;
    }

    public void setBloodInvoiceSeries(String bloodInvoiceSeries) {
        this.bloodInvoiceSeries = bloodInvoiceSeries;
    }

    public AnalysisConclusion getAnalysisConclusion() {
        return analysisConclusion;
    }

    public void setAnalysisConclusion(AnalysisConclusion analysisConclusion) {
        this.analysisConclusion = analysisConclusion;
    }

    @Override
    public String toString() {
        return "BloodInvoiceDto{" +
                "externalId='" + getExternalId() +
                ", deliveryDate=" + deliveryDate +
                ", bloodDonations=" + (bloodDonations != null
                ? '[' + bloodDonations.stream().collect(Collectors.joining(", ")) + ']'
                : null) +
                ", bloodInvoiceSeries='" + getBloodInvoiceSeries() + '\'' +
                ", totalAmount='" + getTotalAmount() + '\'' +
                ", analysisConclusion='" + analysisConclusion + '\'' +
                ", creationTimestamp=" + getCreationTimestamp() +
                ", updateTimestamp=" + getUpdateTimestamp() +
                '}';
    }
}
