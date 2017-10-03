package com.alelk.bcpt.database.service;

import com.alelk.bcpt.database.builder.BloodPoolDtoBuilder;
import com.alelk.bcpt.database.builder.BloodPoolEntityBuilder;
import com.alelk.bcpt.database.model.BloodInvoiceEntity;
import com.alelk.bcpt.database.model.BloodPoolEntity;
import com.alelk.bcpt.database.model.ProductBatchEntity;
import com.alelk.bcpt.database.repository.BloodInvoiceRepository;
import com.alelk.bcpt.database.repository.BloodPoolRepository;
import com.alelk.bcpt.database.repository.ProductBatchRepository;
import com.alelk.bcpt.model.dto.BloodPoolDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.alelk.bcpt.database.util.ValidationUtil.*;

/**
 * Blood Donation Pool Service
 *
 * Created by Alex Elkin on 11.09.2017.
 */
@Service
public class BloodPoolService {

    private BloodPoolRepository bloodPoolRepository;
    private BloodInvoiceRepository bloodInvoiceRepository;
    private ProductBatchRepository productBatchRepository;

    @Autowired
    public BloodPoolService(BloodPoolRepository bloodPoolRepository, BloodInvoiceRepository bloodInvoiceRepository, ProductBatchRepository productBatchRepository) {
        this.bloodPoolRepository = bloodPoolRepository;
        this.bloodInvoiceRepository = bloodInvoiceRepository;
        this.productBatchRepository = productBatchRepository;
    }

    @Transactional
    public BloodPoolDto create(BloodPoolDto dto) {
        final String message = "Cannot add new blood pool info '" + dto + ':';
        validateNotNull(dto, message + " BloodPool DTO must be not null!");
        validateNotEmpty(dto.getExternalId(), message + " no external id provided!");
        return new BloodPoolDtoBuilder().apply(
                bloodPoolRepository.save(new BloodPoolEntityBuilder().apply(dto)
                        .apply(getBloodInvoiceEntitiesByExternalIds(dto.getBloodInvoiceIds(), message))
                        .apply(findProductBatchEntityByExternalId(dto.getProductBatchExternalId(), message))
                        .build())
        ).build();
    }

    @Transactional
    public BloodPoolDto update(String externalId, BloodPoolDto dto, boolean mergeWithNullValues) {
        final String message = "Error updating blood pool info " + dto + ": ";
        validateNotNull(dto, message + "Blood pool DTO object must be not null.");
        if (mergeWithNullValues)
            validateNotEmpty(dto.getExternalId(), message + "no Blood pool external id provided!");
        final BloodPoolEntity entity = findEntityByExternalId(externalId, message);
        validateNotNull(entity, message + "Blood Pool external id does'nt exist.");
        return new BloodPoolDtoBuilder().apply(
                new BloodPoolEntityBuilder(entity, mergeWithNullValues)
                        .apply(dto)
                        .apply(getBloodInvoiceEntitiesByExternalIds(dto.getBloodInvoiceIds(), message))
                        .apply(findProductBatchEntityByExternalId(dto.getProductBatchExternalId(), message))
                        .build()
        ).build();
    }

    @Transactional(readOnly = true)
    public List<BloodPoolDto> findAll() {
        return bloodPoolRepository.findAll().stream()
                .map(entity -> new BloodPoolDtoBuilder().apply(entity).build())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BloodPoolDto findByExternalId(String externalId) {
        return new BloodPoolDtoBuilder().apply(findEntityByExternalId(externalId, "")).build();
    }

    @Transactional
    public BloodPoolDto removeByExternalId(String externalId) {
        final String message = "Cannot remove Blood Pool by external id: ";
        final BloodPoolEntity entity = findEntityByExternalId(externalId, message);
        validateNotNull(entity, message + "no entity found for the external id '" + externalId + '\'');
        bloodPoolRepository.remove(entity);
        return new BloodPoolDtoBuilder().apply(entity).build();
    }

    @Transactional
    public boolean isIdExists(String externalId) {
        return !StringUtils.isEmpty(externalId) && findEntityByExternalId(externalId, "Error checking if the blood pool exists: ") != null;
    }

    private Set<BloodInvoiceEntity> getBloodInvoiceEntitiesByExternalIds(Set<String> externalIds, String message) {
        if (externalIds == null) return null;
        final Set<BloodInvoiceEntity> entities = new HashSet<>();
        externalIds.forEach(externalId -> {
            if (StringUtils.isEmpty(externalId)) return;
            BloodInvoiceEntity bie = bloodInvoiceRepository.findByExternalId(externalId);
            validateNotNull(bie, message + "Cannot find blood invoice entity for the external id '" + externalId + '\'');
            entities.add(bie);
        });
        return entities.size() > 0 ? entities : null;
    }

    private ProductBatchEntity findProductBatchEntityByExternalId(String externalId, String message) {
        if (StringUtils.isEmpty(externalId)) return null;
        ProductBatchEntity pbe = productBatchRepository.findByExternalId(externalId);
        validateNotNull(pbe, message + "Cannot find product batch for external id '" + externalId + '\'');
        return pbe;
    }

    private BloodPoolEntity findEntityByExternalId(String externalId, String message) {
        validateNotEmpty(externalId, message + "Cannot find blood pool by external id: no id provided.");
        return bloodPoolRepository.findByExternalId(externalId);
    }
}
