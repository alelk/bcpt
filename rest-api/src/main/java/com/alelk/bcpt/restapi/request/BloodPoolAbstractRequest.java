package com.alelk.bcpt.restapi.request;

import com.alelk.bcpt.model.AnalysisConclusion;
import com.alelk.bcpt.model.dto.BloodPoolDto;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Blood Pool Abstract Request
 *
 * Created by Alex Elkin on 27.09.2017.
 */
@SuppressWarnings("unused")
public class BloodPoolAbstractRequest extends BcptDtoApiRequest<BloodPoolDto> {

    private Integer poolNumber;
    private Set<String> bloodDonations;
    private String productBatch;
    private Double totalAmount;
    private AnalysisConclusion analysisConclusion;

    public Integer getPoolNumber() {
        return poolNumber;
    }

    public void setPoolNumber(Integer poolNumber) {
        this.poolNumber = poolNumber;
    }

    public Set<String> getBloodDonations() {
        return bloodDonations;
    }

    public void setBloodDonations(Set<String> bloodDonations) {
        this.bloodDonations = bloodDonations;
    }

    public String getProductBatch() {
        return productBatch;
    }

    public void setProductBatch(String productBatch) {
        this.productBatch = productBatch;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public AnalysisConclusion getAnalysisConclusion() {
        return analysisConclusion;
    }

    public void setAnalysisConclusion(AnalysisConclusion analysisConclusion) {
        this.analysisConclusion = analysisConclusion;
    }

    @Override
    public BloodPoolDto toDto() {
        return new BloodPoolDto(getExternalId(), null, null, poolNumber, bloodDonations, productBatch, totalAmount, analysisConclusion);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "externalId='" + getExternalId() + '\'' +
                ", poolNumber=" + poolNumber +
                ", bloodDonations=" + (bloodDonations != null ?
                '[' + bloodDonations.stream().collect(Collectors.joining(", ")) + ']' : null) +
                ", totalAmount='" + totalAmount + '\'' +
                ", analysisConclusion=" + analysisConclusion +
                ", productBatch='" + productBatch + '\'' +
                '}';
    }
}
