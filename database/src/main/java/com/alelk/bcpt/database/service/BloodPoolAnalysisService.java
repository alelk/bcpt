package com.alelk.bcpt.database.service;

import com.alelk.bcpt.database.builder.BloodPoolAnalysisEntityBuilder;
import com.alelk.bcpt.database.model.BloodPoolAnalysisEntity;
import com.alelk.bcpt.database.model.ProductBatchEntity;
import com.alelk.bcpt.database.repository.BloodPoolAnalysisRepository;
import com.alelk.bcpt.database.repository.BloodPoolRepository;
import com.alelk.bcpt.database.repository.ProductBatchRepository;
import com.alelk.bcpt.database.util.DatabaseUtil;
import com.alelk.bcpt.model.dto.BloodPoolAnalysisDto;
import com.alelk.bcpt.model.pagination.Filter;
import com.alelk.bcpt.model.pagination.Page;
import com.alelk.bcpt.model.pagination.SortBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.alelk.bcpt.database.util.ValidationUtil.*;
import static com.alelk.bcpt.database.util.DatabaseUtil.mapBloodPoolAnalysisEntityToDto;
import static com.alelk.bcpt.database.util.ServiceUtil.getBloodPoolEntytyByExternalId;
import static com.alelk.bcpt.database.util.ServiceUtil.getProductBatchEntityByExternalId;

/**
 * Blood Pool Analysis Service
 *
 * Created by Alex Elkin on 23.11.2017.
 */
@Service
public class BloodPoolAnalysisService {

    private BloodPoolRepository bloodPoolRepository;
    private BloodPoolAnalysisRepository bloodPoolAnalysisRepository;
    private ProductBatchRepository productBatchRepository;

    @Autowired
    public BloodPoolAnalysisService(BloodPoolRepository bloodPoolRepository, BloodPoolAnalysisRepository bloodPoolAnalysisRepository, ProductBatchRepository productBatchRepository) {
        this.bloodPoolRepository = bloodPoolRepository;
        this.bloodPoolAnalysisRepository = bloodPoolAnalysisRepository;
        this.productBatchRepository = productBatchRepository;
    }

    @Transactional
    public BloodPoolAnalysisDto create(BloodPoolAnalysisDto dto) {
        final String message = "Cannot add new blood pool analysis info '" + dto + ':';
        validateNotNull(dto, message + " BloodPoolAnalysis DTO must be not null!");
        validateNotEmpty(dto.getExternalId(), message + " no external id provided!");
        return mapBloodPoolAnalysisEntityToDto(
                bloodPoolAnalysisRepository.save(new BloodPoolAnalysisEntityBuilder().apply(dto)
                        .apply(getBloodPoolEntytyByExternalId(bloodPoolRepository, dto.getBloodPool(), message))
                        .build())
        );
    }

    @Transactional
    public BloodPoolAnalysisDto update(String externalId, BloodPoolAnalysisDto dto, boolean mergeWithNullValues, boolean softUpdate) {
        final String message = "Error updating blood pool analysis info " + dto + ": ";
        validateNotNull(dto, message + "Blood pool analysis DTO object must be not null.");
        if (mergeWithNullValues)
            validateNotEmpty(dto.getExternalId(), message + "no blood pool analysis external id provided!");
        final BloodPoolAnalysisEntity entity = findEntityByExternalId(externalId, message);
        validateNotNull(entity, message + "Blood Pool Analysis external id does'nt exist.");
        return mapBloodPoolAnalysisEntityToDto(
                new BloodPoolAnalysisEntityBuilder(entity, mergeWithNullValues, softUpdate)
                        .apply(dto)
                        .apply(getBloodPoolEntytyByExternalId(bloodPoolRepository, dto.getBloodPool(), message))
                        .build()
        );
    }

    @Transactional(readOnly = true)
    public List<BloodPoolAnalysisDto> findAll() {
        return bloodPoolAnalysisRepository.findAll().stream()
                .map(DatabaseUtil::mapBloodPoolAnalysisEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<BloodPoolAnalysisDto> findAll(int pageNumber, int itemsPerPage, List<SortBy> sortByList, List<Filter> filterList) {
        return new Page<>(
                pageNumber,
                itemsPerPage,
                bloodPoolAnalysisRepository.findAll(pageNumber, itemsPerPage, sortByList, filterList)
                        .stream().map(DatabaseUtil::mapBloodPoolAnalysisEntityToDto).collect(Collectors.toList()),
                bloodPoolAnalysisRepository.countItems(filterList),
                sortByList,
                filterList);
    }

    @Transactional(readOnly = true)
    public List<BloodPoolAnalysisDto> findByProductBatch(String productBatchExternalId) {
        ProductBatchEntity batchEntity = getProductBatchEntityByExternalId(productBatchRepository, productBatchExternalId,
                "Cannot find blood pool analysis by product batch external id:");
        return bloodPoolAnalysisRepository.findByProductBatch(batchEntity)
                .stream().map(DatabaseUtil::mapBloodPoolAnalysisEntityToDto).collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public BloodPoolAnalysisDto findByExternalId(String externalId) {
        return mapBloodPoolAnalysisEntityToDto(findEntityByExternalId(externalId, ""));
    }

    @Transactional
    public BloodPoolAnalysisDto removeByExternalId(String externalId) {
        final String message = "Cannot remove Blood Pool Analysis by external id: ";
        final BloodPoolAnalysisEntity entity = findEntityByExternalId(externalId, message);
        validateNotNull(entity, message + "no entity found for the external id '" + externalId + '\'');
        bloodPoolAnalysisRepository.remove(entity);
        return mapBloodPoolAnalysisEntityToDto(entity);
    }

    @Transactional
    public boolean isIdExists(String externalId) {
        return !StringUtils.isEmpty(externalId) && findEntityByExternalId(externalId, "Error checking if the blood pool analysis exists: ") != null;
    }

    private BloodPoolAnalysisEntity findEntityByExternalId(String externalId, String message) {
        validateNotEmpty(externalId, message + "Cannot find blood pool analysis by external id: no id provided.");
        return bloodPoolAnalysisRepository.findByExternalId(externalId);
    }
}
