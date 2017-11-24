package com.alelk.bcpt.reportgenerator.report;

import net.sf.jasperreports.engine.JasperReport;

import java.util.Map;

/**
 * Report Interface
 *
 * Created by Alex Elkin on 08.06.2017.
 */
public interface Report {

    String getTemplateName();

    JasperReport getJasperReport();

    Map<String, Object> getParams();
}
