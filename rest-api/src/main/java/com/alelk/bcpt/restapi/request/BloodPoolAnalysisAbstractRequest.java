package com.alelk.bcpt.restapi.request;

import com.alelk.bcpt.model.dto.BloodPoolAnalysisDto;

/**
 * Blood Pool Analysis Abstract Request
 *
 * Created by Alex Elkin on 23.11.2017.
 */
public class BloodPoolAnalysisAbstractRequest extends BcptDtoApiRequest<BloodPoolAnalysisDto> {

    private Double pH;
    private Double proteinConcentration;

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

    @Override
    public BloodPoolAnalysisDto toDto() {
        return new BloodPoolAnalysisDto(getExternalId(), null, null, getExternalId(), pH, proteinConcentration, null, null, null);
    }

    @Override
    public String toString() {
        return "BloodPoolAnalysisAbstractRequest{" +
                "externalId=" + getExternalId() +
                ", pH=" + pH +
                ", proteinConcentration=" + proteinConcentration +
                '}';
    }
}
