package com.alelk.bcpt.database;

import com.alelk.bcpt.database.model.PersonEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Database Main
 *
 * Created by Alex Elkin on 07.09.2017.
 */
public class DatabaseMain {

    private static final Logger log = LoggerFactory.getLogger(DatabaseMain.class);

    public static void main(String[] args) {

        BcptDatabase bcptDatabase = BcptDatabase.getInstance();

        /*
        log.info("create person " + bcptDatabase.getPersonService()
                .create("p1", "Alex", "Elkin", null, null));
        log.info("create person " + bcptDatabase.getPersonService()
                .create("p2", "B", "B", null, null));

        log.info("create blood donation " + bcptDatabase.getBloodDonationService()
                .create("p1", "d1", 2.21, new Date(), new Date()));
        log.info("create blood donation " + bcptDatabase.getBloodDonationService()
                .create("p2", "d2", 2.1, new Date(), new Date()));

*/

        log.info("create blood don del info " + bcptDatabase.getBloodDonationDeliveryService()
                .create("del6", null, new String[]{"d1", "d2"}));
    }
}
