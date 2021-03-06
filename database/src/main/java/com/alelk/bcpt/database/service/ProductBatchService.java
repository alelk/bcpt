package com.alelk.bcpt.database.service;

import com.alelk.bcpt.database.builder.ProductBatchEntityBuilder;
import com.alelk.bcpt.database.model.BloodPoolEntity;
import com.alelk.bcpt.database.model.ProductBatchEntity;
import com.alelk.bcpt.database.repository.BloodPoolRepository;
import com.alelk.bcpt.database.repository.ProductBatchRepository;
import com.alelk.bcpt.database.util.DatabaseUtil;
import com.alelk.bcpt.model.dto.ProductBatchDto;
import com.alelk.bcpt.model.pagination.Filter;
import com.alelk.bcpt.model.pagination.Page;
import com.alelk.bcpt.model.pagination.SortBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.alelk.bcpt.database.util.ValidationUtil.*;
import static com.alelk.bcpt.database.util.DatabaseUtil.mapProductBatchEntityToDto;

/**
 * Product Batch Service
 *
 * Created by Alex Elkin on 11.09.2017.
 */
@Service
public class ProductBatchService {

    private BloodPoolRepository bloodPoolRepository;
    private ProductBatchRepository productBatchRepository;

    @Autowired
    public ProductBatchService(BloodPoolRepository bloodPoolRepository, ProductBatchRepository productBatchRepository) {
        this.bloodPoolRepository = bloodPoolRepository;
        this.productBatchRepository = productBatchRepository;
    }

    @Transactional
    public ProductBatchDto create(ProductBatchDto dto) {
        final String message = "Cannot add new product batch info '" + dto + ':';
        validateNotNull(dto, message + " Product Batch DTO must be not null!");
        validateNotEmpty(dto.getExternalId(), message + " no external id provided!");
        return mapProductBatchEntityToDto(
                productBatchRepository.save(
                        new ProductBatchEntityBuilder()
                                .apply(dto)
                                .apply(getBloodPoolEntitiesByExternalIds(dto.getBloodPools(), message))
                                .build())
        );
    }

    @Transactional
    public ProductBatchDto update(String externalId, ProductBatchDto dto, boolean mergeWithNullValues, boolean softUpdate) {
        final String message = "Error updating product batch info " + dto + ": ";
        validateNotNull(dto, message + "Product batch DTO object must be not null.");
        if (mergeWithNullValues)
            validateNotEmpty(dto.getExternalId(), message + "no product batch external id provided!");
        final ProductBatchEntity entity = findEntityByExternalId(externalId, message);
        validateNotNull(entity, message + "product batch external id does'nt exist.");
        return mapProductBatchEntityToDto(
                new ProductBatchEntityBuilder(entity, mergeWithNullValues, softUpdate)
                        .apply(dto)
                        .apply(getBloodPoolEntitiesByExternalIds(dto.getBloodPools(), message))
                        .build()
        );
    }

    @Transactional(readOnly = true)
    public List<ProductBatchDto> findAll() {
        return productBatchRepository.findAll().stream()
                .map(DatabaseUtil::mapProductBatchEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<ProductBatchDto> findAll(int pageNumber, int itemsPerPage, List<SortBy> sortByList, List<Filter> filterList) {
        return new Page<>(
                pageNumber,
                itemsPerPage,
                productBatchRepository.findAll(pageNumber, itemsPerPage, sortByList, filterList)
                        .stream().map(DatabaseUtil::mapProductBatchEntityToDto).collect(Collectors.toList()),
                productBatchRepository.countItems(filterList),
                sortByList,
                filterList);
    }

    @Transactional(readOnly = true)
    public ProductBatchDto findByExternalId(String externalId) {
        return mapProductBatchEntityToDto(findEntityByExternalId(externalId, ""));
    }

    @Transactional
    public ProductBatchDto removeByExternalId(String externalId) {
        final String message = "Cannot remove Product Batch by external id: ";
        final ProductBatchEntity entity = findEntityByExternalId(externalId, message);
        validateNotNull(entity, message + "no entity found for the external id '" + externalId + '\'');
        productBatchRepository.remove(entity);
        return mapProductBatchEntityToDto(entity);
    }

    @Transactional
    public boolean isIdExists(String externalId) {
        return !StringUtils.isEmpty(externalId) && findEntityByExternalId(externalId, "Error checking if the product batch exists: ") != null;
    }

    private Set<BloodPoolEntity> getBloodPoolEntitiesByExternalIds(Set<String> externalIds, String message) {
        if (externalIds == null) return null;
        final Set<BloodPoolEntity> entities = new HashSet<>();
        externalIds.forEach(externalId -> {
            if (StringUtils.isEmpty(externalId)) return;
            BloodPoolEntity bpe = bloodPoolRepository.findByExternalId(externalId);
            validateNotNull(bpe, message + "Cannot find blood pool entity for the external id '" + externalId + '\'');
            entities.add(bpe);
        });
        return entities.size() > 0 ? entities : null;
    }

    private ProductBatchEntity findEntityByExternalId(String externalId, String message) {
        validateNotEmpty(externalId, message + "Cannot find product batch by external id: no id provided.");
        return productBatchRepository.findByExternalId(externalId);
    }
}
