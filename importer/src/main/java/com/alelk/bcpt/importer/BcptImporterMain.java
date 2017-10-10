package com.alelk.bcpt.importer;

import com.alelk.bcpt.database.BcptDatabase;
import com.alelk.bcpt.importer.parsed.BcptDtoBundle;
import com.alelk.bcpt.importer.result.OperationResult;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Locale;

/**
 * Bcpt Importer Main
 * <p>
 * Created by Alex Elkin on 05.10.2017.
 */
public class BcptImporterMain {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(BcptImporter.class);

    public static void main(String[] args) {
        BcptImporter importer = BcptImporter.get();
        importer.setDatabase(BcptDatabase.getInstance());

        Flowable<OperationResult<BcptDtoBundle>> flow = importer.parseDbf(new File("/bc-data/база-данных-1.txt")).subscribeOn(Schedulers.io());
        flow.subscribe(pr -> log.info("Parsed: " + pr.getProgress()), exc -> log.error("Error: ", exc), () -> log.info("Complete"));
        flow.takeLast(1).subscribe(pr -> {
            log.info("Parsing result: " + pr);
            sleep(10000);
            importer.saveBundle(pr.get())
                    .subscribe(pr2 -> log.info("SAVED: " + pr2), exc -> log.error("Error: ", exc), () -> log.info("Complete saving"));
                }
        );

        sleep(35000000);
    }

    private static final void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
