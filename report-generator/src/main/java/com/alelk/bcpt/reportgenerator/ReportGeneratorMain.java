package com.alelk.bcpt.reportgenerator;

import com.alelk.bcpt.database.BcptDatabase;
import com.alelk.bcpt.model.dto.*;
import com.alelk.bcpt.reportgenerator.datasource.BloodPoolsDataSource;
import com.alelk.bcpt.reportgenerator.report.ProductBatchReport;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Report Generator Main class for the testing
 *
 * Created by Alex Elkin on 16.11.2017.
 */
public class ReportGeneratorMain {

    private static final Logger log = LoggerFactory.getLogger(ReportGeneratorMain.class);

    public static void main(String[] args) {

        JasperReport report = new ProductBatchReport().getJasperReport();

        if (report ==null) return;

        BcptDatabase database = BcptDatabase.getInstance();

        ProductBatchDto productBatchDto = database.getProductBatchService().findByExternalId("2017-1");
        log.info("Product batch: " + productBatchDto);
        List<BloodPoolDto> bloodPools = database.getBloodPoolService().findByProductBatch("2017-1");
        log.info("Blood pools: {}", bloodPools);
        List<BloodInvoiceDto> bloodInvoices = database.getBloodInvoiceService().findByProductBatch("2017-1");
        log.info("Blood invoices: {}", bloodInvoices.stream().map(BloodInvoiceDto::getExternalId).collect(Collectors.toList()));
        List<BloodInvoiceSeriesDto> bloodInvoiceSeries = database.getBloodInvoiceSeriesService().findByProductBatch("2017-1");
        log.info("Blood invoice series: {}", bloodInvoices.stream().map(BloodInvoiceDto::getExternalId).collect(Collectors.toList()));
        List<BloodPoolAnalysisDto> bloodPoolAnalyzes = database.getBloodPoolAnalysisService().findByProductBatch("2017-1");
        log.info("Blood pool analyzes: {}", bloodPoolAnalyzes);

        try {
            JasperPrint print = JasperFillManager.fillReport(report, new LinkedHashMap<>(),
                    new BloodPoolsDataSource(productBatchDto, bloodPools, bloodInvoices, bloodInvoiceSeries, bloodPoolAnalyzes));
            JasperViewer.viewReport(print);
        } catch (JRException e) {
            e.printStackTrace();
        }



    }
}
