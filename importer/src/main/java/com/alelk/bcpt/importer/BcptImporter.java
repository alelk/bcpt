package com.alelk.bcpt.importer;

import com.alelk.bcpt.database.BcptDatabase;
import com.alelk.bcpt.importer.parsed.BcptDtoBundle;
import com.alelk.bcpt.importer.parsed.BcptDtoBundleInfo;
import com.alelk.bcpt.importer.persist.BcptDtoBundleSaver;
import com.alelk.bcpt.importer.result.OperationResult;
import com.alelk.bcpt.importer.parser.DbfParser;
import com.alelk.bcpt.importer.result.Result;
import com.alelk.bcpt.importer.util.Messages;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private Messages messages;

    @Autowired
    private BcptImporter(DbfParser dbfParser, BcptDtoBundleSaver bundleSaver, Messages messages) {
        this.dbfParser = dbfParser;
        this.bundleSaver = bundleSaver;
        this.messages = messages;
    }

    public Flowable<OperationResult<BcptDtoBundle>> parseDbf(File file) {
        return dbfParser.parse(file);
    }

    public Flowable<OperationResult<BcptDtoBundle>> saveBundle(BcptDtoBundle bundle) {
        return bundleSaver.save(bundle);
    }

    public Flowable<OperationResult<BcptDtoBundleInfo>> importDbf(File file) {
        return Flowable.create((FlowableEmitter<OperationResult<BcptDtoBundleInfo>> e) -> {
            final OperationResult<BcptDtoBundleInfo> operationResult = new OperationResult<>();
            operationResult.setOperationName(messages.get("importer.action.parsing", file.getAbsolutePath()));
            e.onNext(operationResult);
            final Flowable<OperationResult<BcptDtoBundle>> flow = parseDbf(file);
            flow.subscribe(pr -> {
                operationResult.setProgress(pr.getProgress() / 100);
                operationResult.setResult(pr.getResult());
                operationResult.setErrors(pr.getErrors());
                e.onNext(operationResult);
            });
            flow.takeLast(1).subscribe(pr -> {
                        operationResult.setOperationName(messages.get("importer.action.saving"));
                        final List<Throwable> prErrors = operationResult.getErrors() == null ? new ArrayList<>() : operationResult.getErrors();
                        saveBundle(pr.get()).subscribe(sr -> {
                            operationResult.setProgress(1 + sr.getProgress() * 0.99);
                            if (sr.getResult().isLessThan(operationResult.getResult()))
                                operationResult.setResult(sr.getResult());
                            if (sr.getErrors() != null)
                                operationResult.setErrors(Stream.concat(prErrors.stream(), sr.getErrors().stream()).collect(Collectors.toList()));
                            operationResult.setSubject(BcptDtoBundleInfo.fromBundle(sr.get()));
                            e.onNext(operationResult);
                        }, (exc -> onException(e, operationResult, exc, file)
                        ), () -> {
                            if (operationResult.getErrors() == null || operationResult.getErrors().size() == 0) {
                                operationResult.setResult(Result.SUCCESS);
                                operationResult.setOperationName(messages.get("importer.state.done"));
                            } else {
                                operationResult.setResult(Result.WITH_WARNINGS);
                                operationResult.setOperationName(messages.get("importer.state.doneWithWarnings"));
                            }
                            e.onComplete();
                        });
                    }
                    , exc -> onException(e, operationResult, exc, file));
        }, BackpressureStrategy.LATEST).sample(1, TimeUnit.SECONDS, true).share();
    }

    private void onException(FlowableEmitter<OperationResult<BcptDtoBundleInfo>> e,
                             OperationResult<BcptDtoBundleInfo> result, Throwable exc, File file) {
        result.addErrror(exc);
        result.setResult(Result.FAILED);
        result.setOperationName(messages.get("importer.state.error", file.getAbsolutePath()));
        e.onNext(result);
        e.onComplete();
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
