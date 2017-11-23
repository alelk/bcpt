package com.alelk.bcpt.model.dto;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Blood Pool Analysis DTO
 *
 * Created by Alex Elkin on 23.11.2017.
 */
public class BloodPoolAnalysisDto extends AbstractBcptDto {

    private String bloodPool;
    private Double pH;
    private Double proteinConcentration;
    private String productBatch;
    private Set<String> bloodDonations;
    private Integer poolNumber;

    public BloodPoolAnalysisDto() {
    }

    public BloodPoolAnalysisDto(String externalId, Date creationTimestamp, Date updateTimestamp) {
        super(externalId, creationTimestamp, updateTimestamp);
    }

    public BloodPoolAnalysisDto(String externalId, Date creationTimestamp, Date updateTimestamp, String bloodPool, Double pH, Double proteinConcentration, String productBatch, Set<String> bloodDonations, Integer poolNumber) {
        super(externalId, creationTimestamp, updateTimestamp);
        this.bloodPool = bloodPool;
        this.pH = pH;
        this.proteinConcentration = proteinConcentration;
        this.productBatch = productBatch;
        this.bloodDonations = bloodDonations;
        this.poolNumber = poolNumber;
    }

    public String getBloodPool() {
        return bloodPool;
    }

    public void setBloodPool(String bloodPool) {
        this.bloodPool = bloodPool;
    }

    public Double getpH() {
        return pH;
    }

    public void setpH(Double pH) {
        this.pH = pH;
    }

    public Double getProteinConcentration() {
        return proteinConcentration;
    }

    public void setProteinConcentration(Double proteinConcentration) {
        this.proteinConcentration = proteinConcentration;
    }

    public String getProductBatch() {
        return productBatch;
    }

    public void setProductBatch(String productBatch) {
        this.productBatch = productBatch;
    }

    public Set<String> getBloodDonations() {
        return bloodDonations;
    }

    public void setBloodDonations(Set<String> bloodDonations) {
        this.bloodDonations = bloodDonations;
    }

    public Integer getPoolNumber() {
        return poolNumber;
    }

    public void setPoolNumber(Integer poolNumber) {
        this.poolNumber = poolNumber;
    }

    @Override
    public String toString() {
        return "BloodPoolAnalysisDto{" +
                "externalId='" + getExternalId() + '\'' +
                ", bloodPool='" + bloodPool + '\'' +
                ", pH=" + pH +
                ", proteinConcentration=" + proteinConcentration +
                ", productBatch='" + productBatch + '\'' +
                ", bloodDonations=" + (bloodDonations != null
                ? '[' + bloodDonations.stream().collect(Collectors.joining(", ")) + ']'
                : null) +
                ", poolNumber=" + poolNumber +
                ", updateTimestamp=" + getUpdateTimestamp() +
                '}';
    }
}
