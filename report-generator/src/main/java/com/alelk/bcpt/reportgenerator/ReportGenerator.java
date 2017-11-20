package com.alelk.bcpt.reportgenerator;

import com.alelk.bcpt.common.process.Progress;
import com.alelk.bcpt.reportgenerator.exporter.target.Target;
import com.alelk.bcpt.reportgenerator.report.Report;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import net.sf.jasperreports.engine.JasperReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Report Generator
 *
 * Created by Alex Elkin on 08.06.2017.
 */
@Component
public class ReportGenerator {

    private static final Logger log = LoggerFactory.getLogger(ReportGenerator.class);
    private static ApplicationContext applicationContext;

    private ReportGenerator() {
    }

    public static ReportGenerator get() {
        if (applicationContext == null)
            applicationContext = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        return applicationContext.getBean(ReportGenerator.class);
    }

    public Flowable<Progress<Void>> generateProductBatchReport(String productBatchExternalId, List<? extends Target> targets) {
        return Flowable.create((FlowableEmitter<Progress<Void>> e) -> {



        }, BackpressureStrategy.LATEST).sample(1, TimeUnit.SECONDS).share();
    }
}
