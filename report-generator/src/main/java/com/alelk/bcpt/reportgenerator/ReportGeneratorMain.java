package com.alelk.bcpt.reportgenerator;

import com.alelk.bcpt.database.BcptDatabase;
import com.alelk.bcpt.reportgenerator.exporter.target.OutputStreamTarget;
import com.alelk.bcpt.reportgenerator.exporter.target.Target;
import com.alelk.bcpt.reportgenerator.exporter.target.TargetFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;

/**
 * Report Generator Main class for the testing
 *
 * Created by Alex Elkin on 16.11.2017.
 */
public class ReportGeneratorMain {

    private static final Logger log = LoggerFactory.getLogger(ReportGeneratorMain.class);

    public static void main(String[] args) {

        BcptDatabase database = BcptDatabase.getInstance();
        ReportGenerator reportGenerator = ReportGenerator.getInstance(database);

        try {
            Target t1 = new OutputStreamTarget(TargetFormat.PDF, new FileOutputStream("C:\\Workspace\\bcpt-report.pdf"));
            Target t2 = new OutputStreamTarget(TargetFormat.XLS, new FileOutputStream("C:\\Workspace\\bcpt-report.xls"));
            Target t3 = new OutputStreamTarget(TargetFormat.DOCX, new FileOutputStream("C:\\Workspace\\bcpt-report.docx"));
            reportGenerator.generateProductBatchReport("2017-1", Arrays.asList(t1, t2, t3));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
