package com.alelk.bcpt.database;

import com.alelk.bcpt.database.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Bcpt Database
 *
 * Created by Alex Elkin on 07.09.2017.
 */
@Component
public class BcptDatabase {

    private static BcptDatabase bcptDatabase;
    private PersonService personService;
    private BloodDonationService bloodDonationService;
    private BloodInvoiceService bloodInvoiceService;
    private BloodPoolService bloodPoolService;
    private ProductBatchService productBatchService;

    @Autowired
    BcptDatabase(PersonService personService,
                 BloodDonationService bloodDonationService,
                 BloodInvoiceService bloodInvoiceService,
                 BloodPoolService bloodPoolService,
                 ProductBatchService productBatchService
    ) {
        this.personService = personService;
        this.bloodDonationService = bloodDonationService;
        this.bloodInvoiceService = bloodInvoiceService;
        this.bloodPoolService = bloodPoolService;
        this.productBatchService = productBatchService;
    }

    public static BcptDatabase getInstance() {
        if (bcptDatabase == null)
            bcptDatabase = new AnnotationConfigApplicationContext(BcptDatabaseConfig.class).getBean(BcptDatabase.class);
        return bcptDatabase;
    }

    public PersonService getPersonService() {
        return personService;
    }

    public BloodDonationService getBloodDonationService() {
        return bloodDonationService;
    }

    public BloodInvoiceService getBloodInvoiceService() {
        return bloodInvoiceService;
    }

    public BloodPoolService getBloodPoolService() {
        return bloodPoolService;
    }

    public ProductBatchService getProductBatchService() {
        return productBatchService;
    }
}
