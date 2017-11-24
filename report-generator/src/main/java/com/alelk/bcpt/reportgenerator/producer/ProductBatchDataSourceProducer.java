package com.alelk.bcpt.reportgenerator.producer;

import com.alelk.bcpt.database.BcptDatabase;
import com.alelk.bcpt.model.dto.*;
import com.alelk.bcpt.reportgenerator.datasource.ProductBatchReportDataSource;
import com.alelk.bcpt.reportgenerator.report.ProductBatchReport;
import net.sf.jasperreports.engine.JRDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Product Batch Data Source Producer
 *
 * Created by Alex Elkin on 24.11.2017.
 */
@Component
public class ProductBatchDataSourceProducer implements DataSourceProducer<ProductBatchReport> {

    private final static Logger log = LoggerFactory.getLogger(ProductBatchDataSourceProducer.class);
    private BcptDatabase database;

    @Lazy
    @Autowired
    public void setDatabase(BcptDatabase database) {
        this.database = database;
    }

    @Override
    public JRDataSource produce(ProductBatchReport report) {
        if (report == null) return null;
        final String message = "Cannot produce BCPT Report Data Source: {}";
        if (database == null) {
            log.warn(message, "cannot access BCPT Database.");
            return null;
        }
        final String productBatchId = report.getProductBatchId();
        if (productBatchId == null) {
            log.warn(message, "no productBatchId report parameter found.");
            return null;
        }
        try {
            ProductBatchDto productBatchDto = database.getProductBatchService().findByExternalId(productBatchId);
            List<BloodPoolDto> bloodPools = database.getBloodPoolService().findByProductBatch(productBatchId);
            List<BloodInvoiceDto> bloodInvoices = database.getBloodInvoiceService().findByProductBatch(productBatchId);
            List<BloodInvoiceSeriesDto> bloodInvoiceSeries = database.getBloodInvoiceSeriesService().findByProductBatch(productBatchId);
            List<BloodPoolAnalysisDto> bloodPoolAnalyzes = database.getBloodPoolAnalysisService().findByProductBatch(productBatchId);
            return new ProductBatchReportDataSource(productBatchDto, bloodPools, bloodInvoices, bloodInvoiceSeries, bloodPoolAnalyzes);
        } catch (Exception e) {
            log.warn(message, "databaseException: " + e.getLocalizedMessage());
        }
        return null;
    }
}
