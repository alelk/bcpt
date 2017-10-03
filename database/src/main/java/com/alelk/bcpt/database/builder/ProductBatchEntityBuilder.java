package com.alelk.bcpt.database.builder;

import com.alelk.bcpt.database.model.BloodPoolEntity;
import com.alelk.bcpt.database.model.ProductBatchEntity;
import com.alelk.bcpt.model.dto.ProductBatchDto;

import java.util.HashSet;
import java.util.Set;

/**
 * Product Batch Entity Builder
 *
 * Created by Alex Elkin on 22.09.2017.
 */
public class ProductBatchEntityBuilder extends AbstractEntityBuilder<ProductBatchDto, ProductBatchEntity> {

    public ProductBatchEntityBuilder() {
        this(false);
    }

    public ProductBatchEntityBuilder(boolean mergeWithNullValues) {
        super(ProductBatchEntity.class, mergeWithNullValues);
    }

    public ProductBatchEntityBuilder(ProductBatchEntity existingEntity, boolean mergeWithNullValues) {
        super(existingEntity, mergeWithNullValues);
    }

    @Override
    public ProductBatchEntityBuilder apply(ProductBatchDto dto) {
        super.apply(dto);
        if (dto == null) return this;
        if (mergeWithNullValues || dto.getBatchDate() != null)
            entity.setBatchDate(dto.getBatchDate());
        return this;
    }

    public ProductBatchEntityBuilder apply(Set<BloodPoolEntity> bloodPools) {
        if (mergeWithNullValues || bloodPools != null)
            entity.setBloodPools(bloodPools);
        return this;
    }
}
