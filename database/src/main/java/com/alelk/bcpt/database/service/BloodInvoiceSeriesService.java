package com.alelk.bcpt.database.service;

import com.alelk.bcpt.database.builder.BloodInvoiceSeriesEntityBuilder;
import com.alelk.bcpt.database.model.BloodInvoiceSeriesEntity;
import com.alelk.bcpt.database.model.ProductBatchEntity;
import com.alelk.bcpt.database.repository.BloodInvoiceRepository;
import com.alelk.bcpt.database.repository.BloodInvoiceSeriesRepository;
import com.alelk.bcpt.database.repository.ProductBatchRepository;
import com.alelk.bcpt.database.util.DatabaseUtil;
import com.alelk.bcpt.model.dto.BloodInvoiceSeriesDto;
import com.alelk.bcpt.model.pagination.Filter;
import com.alelk.bcpt.model.pagination.Page;
import com.alelk.bcpt.model.pagination.SortBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.alelk.bcpt.database.util.ValidationUtil.validateNotEmpty;
import static com.alelk.bcpt.database.util.ValidationUtil.validateNotNull;
import static com.alelk.bcpt.database.util.ServiceUtil.getBloodInvoiceEntitiesByExternslIds;
import static com.alelk.bcpt.database.util.ServiceUtil.getProductBatchEntityByExternalId;

/**
 * Blood Invoice Series Service
 *
 * Created by Alex Elkin on 22.11.2017.
 */
@Service
public class BloodInvoiceSeriesService {

    private BloodInvoiceRepository bloodInvoiceRepository;
    private ProductBatchRepository productBatchRepository;
    private BloodInvoiceSeriesRepository bloodInvoiceSeriesRepository;

    @Autowired
    BloodInvoiceSeriesService(
            BloodInvoiceRepository bloodInvoiceRepository,
            ProductBatchRepository productBatchRepository, BloodInvoiceSeriesRepository bloodInvoiceSeriesRepository
    ) {
        this.bloodInvoiceRepository = bloodInvoiceRepository;
        this.productBatchRepository = productBatchRepository;
        this.bloodInvoiceSeriesRepository = bloodInvoiceSeriesRepository;
    }

    @Transactional
    public BloodInvoiceSeriesDto create(BloodInvoiceSeriesDto dto) {
        final String message = "Cannot add new blood invoice series info " + dto + ": ";
        validateNotNull(dto, "Blood invoice series DTO must be not null!");
        validateNotEmpty(dto.getExternalId(), message + "external id does'nt provided.");
        return DatabaseUtil.mapBloodInvoiceSeriesEntityToDto(
                bloodInvoiceSeriesRepository.save(new BloodInvoiceSeriesEntityBuilder().apply(dto)
                        .apply(getBloodInvoiceEntitiesByExternslIds(bloodInvoiceRepository, dto.getBloodInvoices(), message))
                        .build())
        );
    }

    @Transactional
    public BloodInvoiceSeriesDto update(String externalId, BloodInvoiceSeriesDto dto, boolean mergeWithNullValues, boolean softUpdate) {
        final String message = "Error updating blood invoice series info " + dto + ": ";
        validateNotNull(dto, message + "Blood invoice series DTO object must be not null.");
        if (mergeWithNullValues)
            validateNotEmpty(dto.getExternalId(), message + "no blood invoice series external id provided!");
        final BloodInvoiceSeriesEntity entity = findEntityByExternalId(externalId, message);
        validateNotNull(entity, message + "Blood Invoice series external id does'nt exist.");
        return DatabaseUtil.mapBloodInvoiceSeriesEntityToDto(
                new BloodInvoiceSeriesEntityBuilder(entity, mergeWithNullValues, softUpdate)
                        .apply(dto)
                        .apply(getBloodInvoiceEntitiesByExternslIds(bloodInvoiceRepository, dto.getBloodInvoices(), message))
                        .build()
        );
    }

    @Transactional(readOnly = true)
    public List<BloodInvoiceSeriesDto> findAll() {
        return bloodInvoiceSeriesRepository.findAll().stream()
                .map(DatabaseUtil::mapBloodInvoiceSeriesEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<BloodInvoiceSeriesDto> findAll(int pageNumber, int itemsPerPage, List<SortBy> sortByList, List<Filter> filterList) {
        return new Page<>(
                pageNumber,
                itemsPerPage,
                bloodInvoiceSeriesRepository.findAll(pageNumber, itemsPerPage, sortByList, filterList)
                        .stream().map(DatabaseUtil::mapBloodInvoiceSeriesEntityToDto).collect(Collectors.toList()),
                bloodInvoiceSeriesRepository.countItems(filterList),
                sortByList,
                filterList);
    }

    @SuppressWarnings("unused")
    @Transactional(readOnly = true)
    public List<BloodInvoiceSeriesDto> findByProductBatch(String productBatchExternalId) {
        ProductBatchEntity batchEntity = getProductBatchEntityByExternalId(productBatchRepository, productBatchExternalId,
                "Cannot find blood invoice series by product batch external id:");
        return bloodInvoiceSeriesRepository.findByProductBatch(batchEntity)
                .stream().map(DatabaseUtil::mapBloodInvoiceSeriesEntityToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BloodInvoiceSeriesDto findByExternalId(String externalId) {
        return DatabaseUtil.mapBloodInvoiceSeriesEntityToDto(findEntityByExternalId(externalId, ""));
    }

    @Transactional
    public BloodInvoiceSeriesDto removeByExternalId(String externalId) {
        final String message = "Cannot remove Blood Invoice Series by external id: ";
        final BloodInvoiceSeriesEntity entity = findEntityByExternalId(externalId, message);
        validateNotNull(entity, message + "no entity found for the external id '" + externalId + '\'');
        bloodInvoiceSeriesRepository.remove(entity);
        return DatabaseUtil.mapBloodInvoiceSeriesEntityToDto(entity);
    }

    @Transactional
    public boolean isIdExists(String externalId) {
        return !StringUtils.isEmpty(externalId) && findEntityByExternalId(externalId, "Error checking if the blood invoice exists: ") != null;
    }

    private BloodInvoiceSeriesEntity findEntityByExternalId(String externalId, String message) {
        validateNotEmpty(externalId, message + "Cannot find blood invoice series by external id: no id provided.");
        return bloodInvoiceSeriesRepository.findByExternalId(externalId);
    }
}
