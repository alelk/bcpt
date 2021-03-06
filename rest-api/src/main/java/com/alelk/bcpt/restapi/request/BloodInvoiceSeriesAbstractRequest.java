package com.alelk.bcpt.restapi.request;

import com.alelk.bcpt.common.util.StringUtil;
import com.alelk.bcpt.model.AnalysisConclusion;
import com.alelk.bcpt.model.dto.BloodInvoiceSeriesDto;

import java.util.Date;
import java.util.Set;

/**
 * Blood Invoice Series Abstract Request
 *
 * Created by Alex Elkin on 22.11.2017.
 */
public class BloodInvoiceSeriesAbstractRequest extends BcptDtoApiRequest<BloodInvoiceSeriesDto> {

    private Date seriesDate;
    private Set<String> bloodInvoices;
    private Double totalAmount;
    private AnalysisConclusion analysisConclusion;

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

    public AnalysisConclusion getAnalysisConclusion() {
        return analysisConclusion;
    }

    public void setAnalysisConclusion(AnalysisConclusion analysisConclusion) {
        this.analysisConclusion = analysisConclusion;
    }

    @Override
    public BloodInvoiceSeriesDto toDto() {
        return new BloodInvoiceSeriesDto(getExternalId(), null, null, seriesDate, bloodInvoices, totalAmount, analysisConclusion);
    }

    @Override
    public String toString() {
        return "BloodInvoiceSeriesAbstractRequest{" +
                "externalId='" + getExternalId() +
                "', seriesDate=" + seriesDate +
                ", totalAmount=" + totalAmount +
                ", analysisConclusion=" + analysisConclusion +
                ", bloodInvoices=[" + StringUtil.toString(bloodInvoices) +
                "]}";
    }
}
