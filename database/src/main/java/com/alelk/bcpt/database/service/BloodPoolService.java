package com.alelk.bcpt.database.service;

import com.alelk.bcpt.database.builder.BloodPoolDtoBuilder;
import com.alelk.bcpt.database.builder.BloodPoolEntityBuilder;
import com.alelk.bcpt.database.model.BloodPoolEntity;
import com.alelk.bcpt.database.model.ProductBatchEntity;
import com.alelk.bcpt.database.repository.BloodDonationRepository;
import com.alelk.bcpt.database.repository.BloodPoolRepository;
import com.alelk.bcpt.database.repository.ProductBatchRepository;
import com.alelk.bcpt.database.util.DatabaseUtil;
import com.alelk.bcpt.model.dto.BloodPoolDto;
import com.alelk.bcpt.model.pagination.Filter;
import com.alelk.bcpt.model.pagination.Page;
import com.alelk.bcpt.model.pagination.SortBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.alelk.bcpt.database.util.ServiceUtil.getBloodDonationEntitiesByExternalIds;
import static com.alelk.bcpt.database.util.ValidationUtil.validateNotEmpty;
import static com.alelk.bcpt.database.util.ValidationUtil.validateNotNull;
import static com.alelk.bcpt.database.util.DatabaseUtil.mapBloodPoolEntityToDto;

/**
 * Blood Donation Pool Service
 *
 * Created by Alex Elkin on 11.09.2017.
 */
@Service
public class BloodPoolService {

    private BloodPoolRepository bloodPoolRepository;
    private BloodDonationRepository bloodDonationRepository;
    private ProductBatchRepository productBatchRepository;

    @Autowired
    public BloodPoolService(BloodPoolRepository bloodPoolRepository, BloodDonationRepository bloodDonationRepository, ProductBatchRepository productBatchRepository) {
        this.bloodPoolRepository = bloodPoolRepository;
        this.bloodDonationRepository = bloodDonationRepository;
        this.productBatchRepository = productBatchRepository;
    }

    @Transactional
    public BloodPoolDto create(BloodPoolDto dto) {
        final String message = "Cannot add new blood pool info '" + dto + ':';
        validateNotNull(dto, message + " BloodPool DTO must be not null!");
        validateNotEmpty(dto.getExternalId(), message + " no external id provided!");
        return mapBloodPoolEntityToDto(
                bloodPoolRepository.save(new BloodPoolEntityBuilder().apply(dto)
                        .apply(getBloodDonationEntitiesByExternalIds(bloodDonationRepository, dto.getBloodDonations(), message))
                        .apply(findProductBatchEntityByExternalId(dto.getProductBatch(), message))
                        .build())
        );
    }

    @Transactional
    public BloodPoolDto update(String externalId, BloodPoolDto dto, boolean mergeWithNullValues, boolean softUpdate) {
        final String message = "Error updating blood pool info " + dto + ": ";
        validateNotNull(dto, message + "Blood pool DTO object must be not null.");
        if (mergeWithNullValues)
            validateNotEmpty(dto.getExternalId(), message + "no Blood pool external id provided!");
        final BloodPoolEntity entity = findEntityByExternalId(externalId, message);
        validateNotNull(entity, message + "Blood Pool external id does'nt exist.");
        return mapBloodPoolEntityToDto(
                new BloodPoolEntityBuilder(entity, mergeWithNullValues, softUpdate)
                        .apply(dto)
                        .apply(getBloodDonationEntitiesByExternalIds(bloodDonationRepository, dto.getBloodDonations(), message))
                        .apply(findProductBatchEntityByExternalId(dto.getProductBatch(), message))
                        .build()
        );
    }

    @Transactional(readOnly = true)
    public List<BloodPoolDto> findAll() {
        return bloodPoolRepository.findAll().stream()
                .map(DatabaseUtil::mapBloodPoolEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<BloodPoolDto> findAll(int pageNumber, int itemsPerPage, List<SortBy> sortByList, List<Filter> filterList) {
        return new Page<>(
                pageNumber,
                itemsPerPage,
                bloodPoolRepository.findAll(pageNumber, itemsPerPage, sortByList, filterList)
                        .stream().map(DatabaseUtil::mapBloodPoolEntityToDto).collect(Collectors.toList()),
                bloodPoolRepository.countItems(filterList),
                sortByList,
                filterList);
    }


    @Transactional(readOnly = true)
    public BloodPoolDto findByExternalId(String externalId) {
        return mapBloodPoolEntityToDto(findEntityByExternalId(externalId, ""));
    }

    @Transactional
    public BloodPoolDto removeByExternalId(String externalId) {
        final String message = "Cannot remove Blood Pool by external id: ";
        final BloodPoolEntity entity = findEntityByExternalId(externalId, message);
        validateNotNull(entity, message + "no entity found for the external id '" + externalId + '\'');
        bloodPoolRepository.remove(entity);
        return mapBloodPoolEntityToDto(entity);
    }

    @Transactional
    public boolean isIdExists(String externalId) {
        return !StringUtils.isEmpty(externalId) && findEntityByExternalId(externalId, "Error checking if the blood pool exists: ") != null;
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
