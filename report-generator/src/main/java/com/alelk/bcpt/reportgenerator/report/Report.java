package com.alelk.bcpt.reportgenerator.report;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperReport;

import java.io.OutputStream;

/**
 * Report Interface
 *
 * Created by Alex Elkin on 08.06.2017.
 */
public interface Report {

    String getTemplateName();

    JasperReport getJasperReport();

    JRDataSource produceJRDataSource();

    String printDataModel();
}
