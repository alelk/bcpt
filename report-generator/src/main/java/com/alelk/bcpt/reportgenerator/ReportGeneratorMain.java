package com.alelk.bcpt.reportgenerator;

import com.alelk.bcpt.common.util.StringUtil;
import com.alelk.bcpt.database.BcptDatabase;
import com.alelk.bcpt.model.dto.BloodPoolDto;
import com.alelk.bcpt.model.dto.ProductBatchDto;
import com.alelk.bcpt.reportgenerator.datasource.BloodPoolsDataSource;
import com.alelk.bcpt.reportgenerator.report.ProductBatchReport;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

        Map<String, Object> parameters = new LinkedHashMap<>();
        ProductBatchDto productBatchDto = database.getProductBatchService().findByExternalId("2017-1");
        log.info("Product batch: " + productBatchDto);
        List<BloodPoolDto> bloodPools = database.getBloodPoolService().findByProductBatch("2017-1");
        log.info("Blood pools: {}", bloodPools);

        parameters.put("productBatch", productBatchDto);

        try {
            JasperPrint print = JasperFillManager.fillReport(report, parameters, new BloodPoolsDataSource(bloodPools));
            JasperViewer.viewReport(print);
        } catch (JRException e) {
            e.printStackTrace();
        }



    }
}