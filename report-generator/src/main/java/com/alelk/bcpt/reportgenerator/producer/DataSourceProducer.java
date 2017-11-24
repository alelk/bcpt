package com.alelk.bcpt.reportgenerator.producer;

import com.alelk.bcpt.reportgenerator.report.Report;
import net.sf.jasperreports.engine.JRDataSource;

/**
 * Data Source Producer
 *
 * Created by Alex Elkin on 24.11.2017.
 */
public interface DataSourceProducer<R extends Report> {

    JRDataSource produce(R report);

}
