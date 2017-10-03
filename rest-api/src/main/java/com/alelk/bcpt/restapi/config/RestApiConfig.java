package com.alelk.bcpt.restapi.config;

import com.alelk.bcpt.database.BcptDatabase;
import com.alelk.bcpt.database.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * Rest Api Config
 *
 * Created by Alex Elkin on 11.09.2017.
 */
@Configuration
@ComponentScan(basePackages = {"com.alelk.bcpt.restapi.controller", "com.alelk.bcpt.restapi.validator"})
public class RestApiConfig {

    @Bean
    public BcptDatabase bcptDatabase() {
        return BcptDatabase.getInstance();
    }

    @Bean
    @Autowired
    public PersonService personService(BcptDatabase bcptDatabase) {
        return bcptDatabase.getPersonService();
    }

    @Bean
    @Autowired
    public BloodDonationService bloodDonationService(BcptDatabase bcptDatabase) {
        return bcptDatabase.getBloodDonationService();
    }

    @Bean
    @Autowired
    public BloodInvoiceService bloodInvoiceService(BcptDatabase bcptDatabase) {
        return bcptDatabase.getBloodInvoiceService();
    }

    @Bean
    @Autowired
    public BloodPoolService bloodPoolService(BcptDatabase bcptDatabase) {
        return bcptDatabase.getBloodPoolService();
    }

    @Bean
    @Autowired
    public ProductBatchService productBatchService(BcptDatabase bcptDatabase) {
        return bcptDatabase.getProductBatchService();
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("bcpt-api-messages");
        return messageSource;
    }
}
