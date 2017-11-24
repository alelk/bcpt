package com.alelk.bcpt.reportgenerator.exporter;

import com.alelk.bcpt.reportgenerator.producer.ProductBatchDataSourceProducer;
import com.alelk.bcpt.reportgenerator.report.ProductBatchReport;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Product Batch Report Exporter
 *
 * Created by Alex Elkin on 24.11.2017.
 */
@Component
public class ProductBatchReportExporter implements ReportExporter<ProductBatchReport> {

    private static final Logger log = LoggerFactory.getLogger(ProductBatchReportExporter.class);
    private ProductBatchDataSourceProducer dataSourceProducer;
    private SimpleXlsReportConfiguration xlsReportConfiguration;

    @Autowired
    public ProductBatchReportExporter(ProductBatchDataSourceProducer producer, SimpleXlsReportConfiguration xlsReportConfiguration) {
        this.dataSourceProducer = producer;
        this.xlsReportConfiguration = xlsReportConfiguration;
    }

    @Override
    public void toPdf(ProductBatchReport report, OutputStream outputStream) {
        final String message = "Unable to export report '" + (report != null ? report.getTemplateName() : null) +
                "' to PDF";
        if (report == null || outputStream == null) {
            log.warn(message + ": incorrect arguments: report=" + report + " outputStream=" + outputStream);
        }
        final JasperPrint jasperPrint = getJasperPrint(report, new LinkedHashMap<>(), message);
        if (jasperPrint == null) return;
        try {
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        } catch (JRException e) {
            log.error(message + ": " + e.getLocalizedMessage());
        }
    }

    @Override
    public void toXls(ProductBatchReport report, OutputStream outputStream) {
        final String message = "Unable to export report '" + (report != null ? report.getTemplateName() : null) +
                "' to XLS";
        if (report == null || outputStream == null) {
            log.warn(message + ": incorrect arguments: report=" + report + " outputStream=" + outputStream);
        }
        final JasperPrint jasperPrint = getJasperPrint(report, new LinkedHashMap<>(), message);
        if (jasperPrint == null) return;
        JRXlsExporter xlsExporter = new JRXlsExporter();
        xlsExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
        xlsExporter.setConfiguration(xlsReportConfiguration);
        try {
            xlsExporter.exportReport();
        } catch (JRException e) {
            log.error(message + ": " + e.getLocalizedMessage());
        }
    }

    @Override
    public void toDocx(ProductBatchReport report, OutputStream outputStream) {
        final String message = "Unable to export report '" + (report != null ? report.getTemplateName() : null) +
                "' to DOCX";
        if (report == null || outputStream == null) {
            log.warn(message + ": incorrect arguments: report=" + report + " outputStream=" + outputStream);
        }
        final JasperPrint jasperPrint = getJasperPrint(report, new LinkedHashMap<>(), message);
        if (jasperPrint == null) return;
        JRDocxExporter docxExporter = new JRDocxExporter();
        docxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        docxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
        try {
            docxExporter.exportReport();
        } catch (JRException e) {
            log.error(message + ": " + e.getLocalizedMessage());
        }

    }

    private JasperPrint getJasperPrint(ProductBatchReport report, Map<String, Object> properties, String message) {
        final JasperReport jasperReport = report.getJasperReport();
        final JRDataSource dataSource = dataSourceProducer.produce(report);
        if (dataSource == null) {
            log.warn(message + ": unable to produce JasperReport datasource: null");
            return null;
        }
        try {
            return JasperFillManager.fillReport(jasperReport, properties, dataSource);
        } catch (JRException e) {
            log.error(message + ": " + e.getLocalizedMessage());
        }
        return null;
    }
}
