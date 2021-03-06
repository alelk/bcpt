package com.alelk.bcpt.database.service;

import com.alelk.bcpt.database.builder.BloodInvoiceEntityBuilder;
import com.alelk.bcpt.database.model.BloodInvoiceEntity;
import com.alelk.bcpt.database.model.BloodInvoiceSeriesEntity;
import com.alelk.bcpt.database.model.ProductBatchEntity;
import com.alelk.bcpt.database.repository.*;
import com.alelk.bcpt.database.util.DatabaseUtil;
import com.alelk.bcpt.model.dto.BloodInvoiceDto;
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
import static com.alelk.bcpt.database.util.ServiceUtil.getBloodDonationEntitiesByExternalIds;
import static com.alelk.bcpt.database.util.ServiceUtil.getProductBatchEntityByExternalId;

/**
 * Blood Donation Delivery Service
 *
 * Created by Alex Elkin on 11.09.2017.
 */
@Service
public class BloodInvoiceService {

    private BloodInvoiceRepository bloodInvoiceRepository;
    private BloodDonationRepository bloodDonationRepository;
    private ProductBatchRepository productBatchRepository;
    private BloodInvoiceSeriesRepository bloodInvoiceSeriesRepository;

    @Autowired
    BloodInvoiceService(
            BloodInvoiceRepository bloodInvoiceRepository, BloodDonationRepository bloodDonationRepository,
            ProductBatchRepository productBatchRepository, BloodInvoiceSeriesRepository bloodInvoiceSeriesRepository
    ) {
        this.bloodInvoiceRepository = bloodInvoiceRepository;
        this.bloodDonationRepository = bloodDonationRepository;
        this.productBatchRepository = productBatchRepository;
        this.bloodInvoiceSeriesRepository = bloodInvoiceSeriesRepository;
    }

    @Transactional
    public BloodInvoiceDto create(BloodInvoiceDto dto) {
        final String message = "Cannot add new blood invoice info " + dto + ": ";
        validateNotNull(dto, "Blood invoice DTO must be not null!");
        validateNotEmpty(dto.getExternalId(), message + "external id does'nt provided.");
        return DatabaseUtil.mapBloodInvoiceEntityToDto(
                bloodInvoiceRepository.save(new BloodInvoiceEntityBuilder().apply(dto)
                        .apply(getBloodDonationEntitiesByExternalIds(bloodDonationRepository, dto.getBloodDonations(), message))
                        .apply(findBloodInvoiceSeriesByExternalId(dto.getBloodInvoiceSeries(), message))
                        .build())
        );
    }

    @Transactional
    public BloodInvoiceDto update(String externalId, BloodInvoiceDto dto, boolean mergeWithNullValues, boolean softUpdate) {
        final String message = "Error updating blood invoice info " + dto + ": ";
        validateNotNull(dto, message + "Blood invoice DTO object must be not null.");
        if (mergeWithNullValues)
            validateNotEmpty(dto.getExternalId(), message + "no Blood invoice external id provided!");
        final BloodInvoiceEntity entity = findEntityByExternalId(externalId, message);
        validateNotNull(entity, message + "Blood Invoice external id does'nt exist.");
        return DatabaseUtil.mapBloodInvoiceEntityToDto(
                new BloodInvoiceEntityBuilder(entity, mergeWithNullValues, softUpdate)
                        .apply(dto)
                        .apply(getBloodDonationEntitiesByExternalIds(bloodDonationRepository, dto.getBloodDonations(), message))
                        .apply(findBloodInvoiceSeriesByExternalId(dto.getBloodInvoiceSeries(), message))
                        .build()
        );
    }

    @Transactional(readOnly = true)
    public List<BloodInvoiceDto> findAll() {
        return bloodInvoiceRepository.findAll().stream()
                .map(DatabaseUtil::mapBloodInvoiceEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<BloodInvoiceDto> findAll(int pageNumber, int itemsPerPage, List<SortBy> sortByList, List<Filter> filterList) {
        return new Page<>(
                pageNumber,
                itemsPerPage,
                bloodInvoiceRepository.findAll(pageNumber, itemsPerPage, sortByList, filterList)
                        .stream().map(DatabaseUtil::mapBloodInvoiceEntityToDto).collect(Collectors.toList()),
                bloodInvoiceRepository.countItems(filterList),
                sortByList,
                filterList);
    }

    @Transactional(readOnly = true)
    public List<BloodInvoiceDto> findByProductBatch(String productBatchExternalId) {
        ProductBatchEntity batchEntity = getProductBatchEntityByExternalId(productBatchRepository, productBatchExternalId,
                "Cannot find blood invoices by product batch external id:");
        return bloodInvoiceRepository.findByProductBatch(batchEntity)
                .stream().map(DatabaseUtil::mapBloodInvoiceEntityToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BloodInvoiceDto findByExternalId(String externalId) {
        return DatabaseUtil.mapBloodInvoiceEntityToDto(findEntityByExternalId(externalId, ""));
    }

    @Transactional
    public BloodInvoiceDto removeByExternalId(String externalId) {
        final String message = "Cannot remove Blood Invoice by external id: ";
        final BloodInvoiceEntity entity = findEntityByExternalId(externalId, message);
        validateNotNull(entity, message + "no entity found for the external id '" + externalId + '\'');
        bloodInvoiceRepository.remove(entity);
        return DatabaseUtil.mapBloodInvoiceEntityToDto(entity);
    }

    @Transactional
    public boolean isIdExists(String externalId) {
        return !StringUtils.isEmpty(externalId) && findEntityByExternalId(externalId, "Error checking if the blood invoice exists: ") != null;
    }

    private BloodInvoiceSeriesEntity findBloodInvoiceSeriesByExternalId(String externalId, String message) {
        if (StringUtils.isEmpty(externalId)) return null;
        BloodInvoiceSeriesEntity entity = bloodInvoiceSeriesRepository.findByExternalId(externalId);
        validateNotNull(entity, message + "Cannot find Blood Invoice Series for external id '" + externalId + '\'');
        return entity;
    }

    private BloodInvoiceEntity findEntityByExternalId(String externalId, String message) {
        validateNotEmpty(externalId, message + "Cannot find blood invoice by external id: no id provided.");
        return bloodInvoiceRepository.findByExternalId(externalId);
    }
}
