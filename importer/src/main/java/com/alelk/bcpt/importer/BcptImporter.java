package com.alelk.bcpt.importer;

import com.alelk.bcpt.database.BcptDatabase;
import com.alelk.bcpt.importer.parsed.BcptDtoBundle;
import com.alelk.bcpt.importer.persist.BcptDtoBundleSaver;
import com.alelk.bcpt.importer.result.OperationResult;
import com.alelk.bcpt.importer.parser.DbfParser;
import com.alelk.bcpt.importer.util.Messages;
import io.reactivex.Flowable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Locale;

/**
 * Bcpt Importer
 *
 * Created by Alex Elkin on 04.10.2017.
 */
@Component
public class BcptImporter {

    private static ApplicationContext applicationContext;

    private DbfParser dbfParser;
    private BcptDtoBundleSaver bundleSaver;

    @Autowired
    private BcptImporter(DbfParser dbfParser, BcptDtoBundleSaver bundleSaver) {
        this.dbfParser = dbfParser;
        this.bundleSaver = bundleSaver;
    }

    public Flowable<OperationResult<BcptDtoBundle>> parseDbf(File file) {
        return dbfParser.parse(file);
    }

    public Flowable<OperationResult<BcptDtoBundle>> saveBundle(BcptDtoBundle bundle) {
        return bundleSaver.save(bundle);
    }

    public static BcptImporter get() {
        if (applicationContext == null)
            applicationContext = new AnnotationConfigApplicationContext(BcptImporterConfig.class);
        return applicationContext.getBean(BcptImporter.class);
    }

    public void setLocale(Locale locale) {
        if (applicationContext == null) return;
        applicationContext.getBean(Messages.class).setLocale(locale != null ? locale : Locale.ENGLISH);
    }

    public void setDatabase(BcptDatabase database) {
        bundleSaver.setUniversalService(database.getUniversalService());
    }
}