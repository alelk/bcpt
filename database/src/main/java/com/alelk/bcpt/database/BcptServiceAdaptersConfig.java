package com.alelk.bcpt.database;

import com.alelk.bcpt.database.service.*;
import com.alelk.bcpt.model.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service Adapters Config
 *
 * Created by Alex Elkin on 09.10.2017.
 */
@Configuration
public class BcptServiceAdaptersConfig {

    @Bean
    @Autowired
    public Map<Class<?>, BcptService<?>> serviceAdapters(BcptService<PersonDto> personServiceAdapter,
                                                         BcptService<BloodDonationDto> bloodDonationServiceAdapter,
                                                         BcptService<BloodInvoiceDto> bloodInvoiceServiceAdapter,
                                                         BcptService<BloodPoolDto> bloodPoolServiceAdapter,
                                                         BcptService<ProductBatchDto> productBatchServiceAdapter) {
        final Map<Class<?>, BcptService<?>> serviceMap = new HashMap<>();
        serviceMap.put(PersonDto.class, personServiceAdapter);
        serviceMap.put(BloodDonationDto.class, bloodDonationServiceAdapter);
        serviceMap.put(BloodInvoiceDto.class, bloodInvoiceServiceAdapter);
        serviceMap.put(BloodPoolDto.class, bloodPoolServiceAdapter);
        serviceMap.put(ProductBatchDto.class, productBatchServiceAdapter);
        return serviceMap;
    }

    @Bean
    @Autowired
    public BcptService<PersonDto> personServiceAdapter(PersonService personService) {
        return new BcptService<PersonDto>() {
            @Override
            public PersonDto create(PersonDto dto) {
                return personService.create(dto);
            }

            @Override
            public PersonDto update(String externalId, PersonDto dto, boolean mergeWithNullValues, boolean softUpdate) {
                return personService.update(externalId, dto, mergeWithNullValues, softUpdate);
            }

            @Override
            public List<PersonDto> findAll() {
                return personService.findAll();
            }

            @Override
            public PersonDto findByExternalId(String externalId) {
                return personService.findByExternalId(externalId);
            }

            @Override
            public boolean isIdExists(String externalId) {
                return personService.isIdExists(externalId);
            }

            @Override
            public PersonDto removeByExternalId(String externalId) {
                return personService.removeByExternalId(externalId);
            }
        };
    }

    @Bean
    @Autowired
    public BcptService<BloodDonationDto> bloodDonationServiceAdapter(BloodDonationService service) {
        return new BcptService<BloodDonationDto>() {
            @Override
            public BloodDonationDto create(BloodDonationDto dto) {
                return service.create(dto);
            }

            @Override
            public BloodDonationDto update(String externalId, BloodDonationDto dto, boolean mergeWithNullValues, boolean softUpdate) {
                return service.update(externalId, dto, mergeWithNullValues, softUpdate);
            }

            @Override
            public List<BloodDonationDto> findAll() {
                return service.findAll();
            }

            @Override
            public BloodDonationDto findByExternalId(String externalId) {
                return service.findByExternalId(externalId);
            }

            @Override
            public boolean isIdExists(String externalId) {
                return service.isIdExists(externalId);
            }

            @Override
            public BloodDonationDto removeByExternalId(String externalId) {
                return service.removeByExternalId(externalId);
            }
        };
    }

    @Bean
    @Autowired
    public BcptService<BloodInvoiceDto> bloodInvoiceServiceAdapter(BloodInvoiceService service) {
        return new BcptService<BloodInvoiceDto>() {
            @Override
            public BloodInvoiceDto create(BloodInvoiceDto dto) {
                return service.create(dto);
            }

            @Override
            public BloodInvoiceDto update(String externalId, BloodInvoiceDto dto, boolean mergeWithNullValues, boolean softUpdate) {
                return service.update(externalId, dto, mergeWithNullValues, softUpdate);
            }

            @Override
            public List<BloodInvoiceDto> findAll() {
                return service.findAll();
            }

            @Override
            public BloodInvoiceDto findByExternalId(String externalId) {
                return service.findByExternalId(externalId);
            }

            @Override
            public boolean isIdExists(String externalId) {
                return service.isIdExists(externalId);
            }

            @Override
            public BloodInvoiceDto removeByExternalId(String externalId) {
                return service.removeByExternalId(externalId);
            }
        };
    }

    @Bean
    @Autowired
    public BcptService<BloodPoolDto> bloodPoolServiceAdapter(BloodPoolService service) {
        return new BcptService<BloodPoolDto>() {
            @Override
            public BloodPoolDto create(BloodPoolDto dto) {
                return service.create(dto);
            }

            @Override
            public BloodPoolDto update(String externalId, BloodPoolDto dto, boolean mergeWithNullValues, boolean softUpdate) {
                return service.update(externalId, dto, mergeWithNullValues, softUpdate);
            }

            @Override
            public List<BloodPoolDto> findAll() {
                return service.findAll();
            }

            @Override
            public BloodPoolDto findByExternalId(String externalId) {
                return service.findByExternalId(externalId);
            }

            @Override
            public boolean isIdExists(String externalId) {
                return service.isIdExists(externalId);
            }

            @Override
            public BloodPoolDto removeByExternalId(String externalId) {
                return service.removeByExternalId(externalId);
            }
        };
    }

    @Bean
    @Autowired
    public BcptService<ProductBatchDto> productBatchServiceAdapter(ProductBatchService service) {
        return new BcptService<ProductBatchDto>() {
            @Override
            public ProductBatchDto create(ProductBatchDto dto) {
                return service.create(dto);
            }

            @Override
            public ProductBatchDto update(String externalId, ProductBatchDto dto, boolean mergeWithNullValues, boolean softUpdate) {
                return service.update(externalId, dto, mergeWithNullValues, softUpdate);
            }

            @Override
            public List<ProductBatchDto> findAll() {
                return service.findAll();
            }

            @Override
            public ProductBatchDto findByExternalId(String externalId) {
                return service.findByExternalId(externalId);
            }

            @Override
            public boolean isIdExists(String externalId) {
                return service.isIdExists(externalId);
            }

            @Override
            public ProductBatchDto removeByExternalId(String externalId) {
                return service.removeByExternalId(externalId);
            }
        };
    }
}
