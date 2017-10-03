package com.alelk.bcpt.database.service;

import com.alelk.bcpt.database.builder.BloodInvoiceDtoBuilder;
import com.alelk.bcpt.database.builder.BloodInvoiceEntityBuilder;
import com.alelk.bcpt.database.model.BloodInvoiceEntity;
import com.alelk.bcpt.database.model.BloodDonationEntity;
import com.alelk.bcpt.database.model.BloodPoolEntity;
import com.alelk.bcpt.database.repository.BloodInvoiceRepository;
import com.alelk.bcpt.database.repository.BloodDonationRepository;
import com.alelk.bcpt.database.repository.BloodPoolRepository;
import com.alelk.bcpt.model.dto.BloodInvoiceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.alelk.bcpt.database.util.ValidationUtil.*;

/**
 * Blood Donation Delivery Service
 *
 * Created by Alex Elkin on 11.09.2017.
 */
@Service
public class BloodInvoiceService {

    private BloodInvoiceRepository bloodInvoiceRepository;
    private BloodDonationRepository bloodDonationRepository;
    private BloodPoolRepository bloodPoolRepository;

    @Autowired
    BloodInvoiceService(BloodInvoiceRepository bloodInvoiceRepository, BloodDonationRepository bloodDonationRepository, BloodPoolRepository bloodPoolRepository) {
        this.bloodInvoiceRepository = bloodInvoiceRepository;
        this.bloodDonationRepository = bloodDonationRepository;
        this.bloodPoolRepository = bloodPoolRepository;
    }

    @Transactional
    public BloodInvoiceDto create(BloodInvoiceDto dto) {
        final String message = "Cannot add new blood invoice info " + dto + ": ";
        validateNotNull(dto, "Blood invoice DTO must be not null!");
        validateNotEmpty(dto.getExternalId(), message + "external id does'nt provided.");
        return new BloodInvoiceDtoBuilder().apply(
                bloodInvoiceRepository.save(new BloodInvoiceEntityBuilder().apply(dto)
                        .apply(getBloodDonationEntitiesByExternalIds(dto.getBloodDonationExternalIds(), message))
                        .apply(findBloodPoolByExternalId(dto.getBloodPoolExternalId(), message))
                        .build())
        ).build();
    }

    @Transactional
    public BloodInvoiceDto update(String externalId, BloodInvoiceDto dto, boolean mergeWithNullValues) {
        final String message = "Error updating blood invoice info " + dto + ": ";
        validateNotNull(dto, message + "Blood invoice DTO object must be not null.");
        if (mergeWithNullValues)
            validateNotEmpty(dto.getExternalId(), message + "no Blood invoice external id provided!");
        final BloodInvoiceEntity entity = findEntityByExternalId(externalId, message);
        validateNotNull(entity, message + "Blood Invoice external id does'nt exist.");
        return new BloodInvoiceDtoBuilder().apply(
                new BloodInvoiceEntityBuilder(entity, mergeWithNullValues)
                        .apply(dto)
                        .apply(getBloodDonationEntitiesByExternalIds(dto.getBloodDonationExternalIds(), message))
                        .apply(findBloodPoolByExternalId(dto.getBloodPoolExternalId(), message))
                        .build()
        ).build();
    }

    @Transactional(readOnly = true)
    public List<BloodInvoiceDto> findAll() {
        return bloodInvoiceRepository.findAll().stream()
                .map(entity -> new BloodInvoiceDtoBuilder().apply(entity).build())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BloodInvoiceDto findByExternalId(String externalId) {
        return new BloodInvoiceDtoBuilder().apply(findEntityByExternalId(externalId, "")).build();
    }

    @Transactional
    public BloodInvoiceDto removeByExternalId(String externalId) {
        final String message = "Cannot remove Blood Invoice by external id: ";
        final BloodInvoiceEntity entity = findEntityByExternalId(externalId, message);
        validateNotNull(entity, message + "no entity found for the external id '" + externalId + '\'');
        bloodInvoiceRepository.remove(entity);
        return new BloodInvoiceDtoBuilder().apply(entity).build();
    }

    @Transactional
    public boolean isIdExists(String externalId) {
        return !StringUtils.isEmpty(externalId) && findEntityByExternalId(externalId, "Error checking if the blood invoice exists: ") != null;
    }

    private Set<BloodDonationEntity> getBloodDonationEntitiesByExternalIds(Set<String> externalIds, String message) {
        if (externalIds == null) return null;
        final Set<BloodDonationEntity> bloodDonationEntities = new HashSet<>();
        externalIds.forEach(externalId -> {
            if (StringUtils.isEmpty(externalId)) return;
            BloodDonationEntity bde = bloodDonationRepository.findByExternalId(externalId);
            validateNotNull(bde, message + "Cannot find blood donation entity for the external id '" + externalId + '\'');
            bloodDonationEntities.add(bde);
        });
        return bloodDonationEntities.size() > 0 ? bloodDonationEntities : null;
    }

    private BloodPoolEntity findBloodPoolByExternalId(String externalId, String message) {
        if (StringUtils.isEmpty(externalId)) return null;
        BloodPoolEntity bpe = bloodPoolRepository.findByExternalId(externalId);
        validateNotNull(bpe, message + "Cannot find Blood Pool for external id '" + externalId + '\'');
        return bpe;
    }

    private BloodInvoiceEntity findEntityByExternalId(String externalId, String message) {
        validateNotEmpty(externalId, message + "Cannot find blood invoice by external id: no id provided.");
        return bloodInvoiceRepository.findByExternalId(externalId);
    }
}
