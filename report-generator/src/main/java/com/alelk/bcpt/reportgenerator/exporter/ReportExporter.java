package com.alelk.bcpt.reportgenerator.exporter;

import com.alelk.bcpt.reportgenerator.report.Report;

import java.io.OutputStream;

/**
 * Report Exporter
 *
 * Created by Alex Elkin on 24.11.2017.
 */
public interface ReportExporter<R extends Report> {

    void toPdf(R report, OutputStream outputStream);

    void toXls(R report, OutputStream outputStream);

    void toDocx(R report, OutputStream outputStream);
}
