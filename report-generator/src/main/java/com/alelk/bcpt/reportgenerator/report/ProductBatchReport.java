package com.alelk.bcpt.reportgenerator.report;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Product Batch Report
 *
 * Created by Alex Elkin on 16.11.2017.
 */
public class ProductBatchReport extends AbstractReport {

    private static final Logger log = LoggerFactory.getLogger(ProductBatchReport.class);
    private static final String TEMPLATE_NAME = "reports/product-batch-report-template.jrxml";

    public ProductBatchReport() {
    }

    public ProductBatchReport(String productBatchId) {
        setProductBatchId(productBatchId);
    }

    public void setProductBatchId(String productBatchId) {
        setParam("productBatchId", productBatchId);
    }

    public String getProductBatchId() {
        return (String) getParam("productBatchId");
    }

    @Override
    public String getTemplateName() {
        return TEMPLATE_NAME;
    }

    @Override
    public JasperReport getJasperReport() {
        try {
            return JasperCompileManager.compileReport(getClass().getClassLoader().getResourceAsStream(TEMPLATE_NAME));
        } catch (JRException e) {
            log.error("Unable to compile Jasper Report from the template file '" + TEMPLATE_NAME + "': " + e.getLocalizedMessage(), e);
            return null;
        }
    }

    @Override
    public String toString() {
        return "ProductBatchReport{" +
                "templateName='" + getTemplateName() +
                "', productBatchId='" + getProductBatchId() +
                "'}";
    }
}
