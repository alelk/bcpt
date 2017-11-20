package com.alelk.bcpt.importer;

import com.alelk.bcpt.common.process.ProcessState;
import com.alelk.bcpt.common.process.Progress;
import com.alelk.bcpt.database.BcptDatabase;
import com.alelk.bcpt.importer.parsed.BcptDtoBundle;
import com.alelk.bcpt.importer.parsed.BcptDtoBundleInfo;
import com.alelk.bcpt.importer.persist.BcptDtoBundleSaver;
import com.alelk.bcpt.importer.parser.DbfParser;
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

    public Flowable<Progress<BcptDtoBundle>> parseDbf(File file) {
        return dbfParser.parse(file);
    }

    public Flowable<Progress<BcptDtoBundle>> saveBundle(BcptDtoBundle bundle) {
        return bundleSaver.save(bundle);
    }

    public Flowable<Progress<BcptDtoBundleInfo>> importDbf(File file) {
        return Flowable.create((FlowableEmitter<Progress<BcptDtoBundleInfo>> e) -> {
            final Progress<BcptDtoBundleInfo> progress = new Progress<>();
            progress.setProcessName(messages.get("importer.action.parsing", file.getAbsolutePath()));
            e.onNext(progress);
            final Flowable<Progress<BcptDtoBundle>> flow = parseDbf(file);
            flow.subscribe(pr -> {
                progress.setProgress(pr.getProgress() / 100);
                progress.setState(pr.getState().isLessThan(ProcessState.SUCCESS) ? pr.getState() : ProcessState.IN_PROGRESS);
                progress.setErrors(pr.getErrors());
                e.onNext(progress);
            });
            flow.takeLast(1).subscribe(pr -> {
                        progress.setProcessName(messages.get("importer.action.saving"));
                        final List<Throwable> prErrors = progress.getErrors() == null ? new ArrayList<>() : progress.getErrors();
                        saveBundle(pr.getResult()).subscribe(sr -> {
                            progress.setProgress(1 + sr.getProgress() * 0.99);
                            if (sr.getState().isLessThan(progress.getState()))
                                progress.setState(sr.getState());
                            if (sr.getErrors() != null)
                                progress.setErrors(Stream.concat(prErrors.stream(), sr.getErrors().stream()).collect(Collectors.toList()));
                            progress.setResult(BcptDtoBundleInfo.fromBundle(sr.getResult()));
                            e.onNext(progress);
                        }, (exc -> onException(e, progress, exc, file)
                        ), () -> {
                            if (progress.getErrors() == null || progress.getErrors().size() == 0) {
                                progress.setState(ProcessState.SUCCESS);
                                progress.setProcessName(messages.get("importer.state.done"));
                            } else {
                                progress.setState(ProcessState.WITH_WARNINGS);
                                progress.setProcessName(messages.get("importer.state.doneWithWarnings"));
                            }
                            e.onComplete();
                        });
                    }
                    , exc -> onException(e, progress, exc, file));
        }, BackpressureStrategy.LATEST).sample(1, TimeUnit.SECONDS, true).share();
    }

    private void onException(FlowableEmitter<Progress<BcptDtoBundleInfo>> e,
                             Progress<BcptDtoBundleInfo> result, Throwable exc, File file) {
        result.addError(exc);
        result.setState(ProcessState.FAILED);
        result.setProcessName(messages.get("importer.state.error", file.getAbsolutePath()));
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
