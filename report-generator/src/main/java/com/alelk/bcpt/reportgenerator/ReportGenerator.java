package com.alelk.bcpt.reportgenerator;

import com.alelk.bcpt.database.BcptDatabase;
import com.alelk.bcpt.reportgenerator.exporter.ProductBatchReportExporter;
import com.alelk.bcpt.reportgenerator.exporter.target.OutputStreamTarget;
import com.alelk.bcpt.reportgenerator.exporter.target.Target;
import com.alelk.bcpt.reportgenerator.exporter.target.TargetFormat;
import com.alelk.bcpt.reportgenerator.report.ProductBatchReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.util.List;

/**
 * Report Generator
 *
 * Created by Alex Elkin on 08.06.2017.
 */
@Component
public class ReportGenerator {

    private static final Logger log = LoggerFactory.getLogger(ReportGenerator.class);
    private static ApplicationContext applicationContext;
    private BcptDatabase database;
    private ProductBatchReportExporter productBatchReportExporter;

    private ReportGenerator() {
    }

    BcptDatabase getDatabase() {
        return database;
    }

    void setDatabase(BcptDatabase database) {
        this.database = database;
    }

    @Autowired
    public void setProductBatchReportExporter(ProductBatchReportExporter productBatchReportExporter) {
        this.productBatchReportExporter = productBatchReportExporter;
    }

    public static ReportGenerator getInstance(BcptDatabase database) {
        if (applicationContext == null)
            applicationContext = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        ReportGenerator reportGenerator = applicationContext.getBean(ReportGenerator.class);
        reportGenerator.setDatabase(database);
        return reportGenerator;
    }

    public void generateProductBatchReport(String productBatchExternalId, List<? extends Target> targets) {
        final ProductBatchReport report = new ProductBatchReport(productBatchExternalId);
        try {
            for (Target target : targets) {
                if (target.getTargetFormat() == null) continue;
                if (target instanceof OutputStreamTarget) {
                    final OutputStream outputStream = ((OutputStreamTarget) target).getTarget();
                    if (TargetFormat.PDF.equals(target.getTargetFormat()))
                        productBatchReportExporter.toPdf(report, outputStream);
                    else if (TargetFormat.XLS.equals(target.getTargetFormat()))
                        productBatchReportExporter.toXls(report, outputStream);
                    else if (TargetFormat.DOCX.equals(target.getTargetFormat()))
                        productBatchReportExporter.toDocx(report, outputStream);
                    else
                        log.warn("Unable to export Product Batch report to the target " + target);
                    outputStream.close();
                }
            }
        } catch (Exception e) {
            log.error("Unable to export Product Batch report: " + e.getLocalizedMessage());
        }
    }
}
