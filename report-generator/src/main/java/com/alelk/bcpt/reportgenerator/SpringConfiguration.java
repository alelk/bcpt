package com.alelk.bcpt.reportgenerator;

import com.alelk.bcpt.database.BcptDatabase;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * Spring Configuration
 *
 * Created by Alex Elkin on 16.11.2017.
 */
@Configuration
@ComponentScan
class SpringConfiguration {

    @Lazy
    @Bean
    @Autowired
    public BcptDatabase bcptDatabase(ReportGenerator reportGenerator) {
        return reportGenerator.getDatabase();
    }

    @Bean
    SimpleXlsReportConfiguration simpleXlsReportConfiguration() {
        final SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
        configuration.setOnePagePerSheet(true);
        configuration.setForcePageBreaks(true);
        configuration.setDetectCellType(true);
        return configuration;
    }
}
