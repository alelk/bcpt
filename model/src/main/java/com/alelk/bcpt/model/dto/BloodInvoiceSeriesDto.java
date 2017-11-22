package com.alelk.bcpt.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Blood Invoice Series DTO
 *
 * Created by Alex Elkin on 22.11.2017.
 */
public class BloodInvoiceSeriesDto extends AbstractBcptDto {

    private Date seriesDate;
    private Set<String> bloodInvoices;
    private Double totalAmount;

    public BloodInvoiceSeriesDto() {
    }

    public BloodInvoiceSeriesDto(String externalId, Date creationTimestamp, Date updateTimestamp, Date seriesDate, Set<String> bloodInvoices, Double totalAmount) {
        super(externalId, creationTimestamp, updateTimestamp);
        this.seriesDate = seriesDate;
        this.bloodInvoices = bloodInvoices;
        this.totalAmount = totalAmount;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    public Date getSeriesDate() {
        return seriesDate;
    }

    public void setSeriesDate(Date seriesDate) {
        this.seriesDate = seriesDate;
    }

    public Set<String> getBloodInvoices() {
        return bloodInvoices;
    }

    public void setBloodInvoices(Set<String> bloodInvoices) {
        this.bloodInvoices = bloodInvoices;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "BloodInvoiceSeriesDto{" +
                "externalId='" + getExternalId() +
                "' updateTimestamp=" + getUpdateTimestamp() +
                " seriesDate=" + seriesDate +
                " totalAmount=" + totalAmount +
                ", bloodInvoices=[" +
                (bloodInvoices != null ? bloodInvoices.stream().collect(Collectors.joining(", ")) : "") +
                "]}";
    }
}
