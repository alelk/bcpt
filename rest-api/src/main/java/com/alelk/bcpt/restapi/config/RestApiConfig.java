package com.alelk.bcpt.restapi.config;

import com.alelk.bcpt.database.BcptDatabase;
import com.alelk.bcpt.database.service.*;
import com.alelk.bcpt.importer.BcptImporter;
import com.alelk.bcpt.reportgenerator.ReportGenerator;
import com.alelk.bcpt.storage.BcptStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import javax.servlet.MultipartConfigElement;

/**
 * Rest Api Config
 *
 * Created by Alex Elkin on 11.09.2017.
 */
@Configuration
@EnableWebSocketMessageBroker
@ComponentScan(basePackages = {"com.alelk.bcpt.restapi.controller", "com.alelk.bcpt.restapi.validator"})
public class RestApiConfig extends AbstractWebSocketMessageBrokerConfigurer {

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
    public BloodInvoiceSeriesService bloodInvoiceSeriesService(BcptDatabase bcptDatabase) {
        return bcptDatabase.getBloodInvoiceSeriesService();
    }

    @Bean
    @Autowired
    public BloodPoolService bloodPoolService(BcptDatabase bcptDatabase) {
        return bcptDatabase.getBloodPoolService();
    }

    @Bean
    @Autowired
    public BloodPoolAnalysisService bloodPoolAnalysisService(BcptDatabase bcptDatabase) {
        return bcptDatabase.getBloodPoolAnalysisService();
    }

    @Bean
    @Autowired
    public ProductBatchService productBatchService(BcptDatabase bcptDatabase) {
        return bcptDatabase.getProductBatchService();
    }

    @Bean
    public BcptImporter bcptImporter(BcptDatabase database) {
        BcptImporter importer = BcptImporter.get();
        importer.setDatabase(database);
        return importer;
    }

    @Bean
    public BcptStorage bcptStorage() {
        return BcptStorage.getInstance();
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("bcpt-api-messages");
        return messageSource;
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(50 * 1024 * 1024);
        multipartResolver.setMaxInMemorySize(80 * 1024 * 1024);
        return multipartResolver;
    }

    @Bean
    @Autowired
    public ReportGenerator reportGenerator(BcptDatabase database) {
        return ReportGenerator.getInstance(database);
    }

    @Bean
    MultipartConfigElement multipartConfigElement() {
        return new MultipartConfigElement(null, 50 * 1024 * 1024, 50* 1024 * 1024, 1024 * 1024);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/bcpt-websocket")
                .setAllowedOrigins("http://localhost:3000", "http://localhost:3001", "http://localhost:8080")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/socket-output");
        config.setApplicationDestinationPrefixes("/app");
    }
}
