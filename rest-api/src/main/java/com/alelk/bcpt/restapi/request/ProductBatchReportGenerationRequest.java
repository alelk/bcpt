package com.alelk.bcpt.restapi.request;

import com.alelk.bcpt.reportgenerator.exporter.target.TargetFormat;

/**
 * Report Generation Request
 *
 * Created by Alex Elkin on 24.11.2017.
 */
public class ProductBatchReportGenerationRequest {

    private String productBatchExternalId;
    private TargetFormat targetFormat;

    public String getProductBatchExternalId() {
        return productBatchExternalId;
    }

    public void setProductBatchExternalId(String productBatchExternalId) {
        this.productBatchExternalId = productBatchExternalId;
    }

    public TargetFormat getTargetFormat() {
        return targetFormat;
    }

    public void setTargetFormat(TargetFormat targetFormat) {
        this.targetFormat = targetFormat;
    }

    @Override
    public String toString() {
        return "ProductBatchReportGenerationRequest{" +
                "productBatchExternalId='" + productBatchExternalId + '\'' +
                ", targetFormat=" + targetFormat +
                '}';
    }
}
