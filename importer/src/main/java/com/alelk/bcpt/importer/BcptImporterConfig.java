package com.alelk.bcpt.importer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
/**
 * BcptImporterConfig
 * <p>
 * Created by Alex Elkin on 05.10.2017.
 */
@Configuration
@ComponentScan
class BcptImporterConfig {

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("bcpt-importer-messages");
        return messageSource;
    }
}
