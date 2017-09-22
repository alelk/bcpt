package com.alelk.bcpt.database.builder;

import com.alelk.bcpt.database.model.BloodPoolEntity;
import com.alelk.bcpt.database.model.ProductBatchEntity;
import com.alelk.bcpt.model.dto.ProductBatchDto;

/**
 * Product Batch DTO builder
 *
 * Created by Alex Elkin on 22.09.2017.
 */
public class ProductBatchDtoBuilder extends AbstractDtoBuilder<ProductBatchEntity, ProductBatchDto> {

    public ProductBatchDtoBuilder() {
        super(ProductBatchDto.class);
    }

    @Override
    public ProductBatchDtoBuilder apply(ProductBatchEntity entity) {
        super.apply(entity);
        if (entity == null) return this;
        dto.setBatchDate(entity.getBatchDate());
        dto.setBloodPoolIds(
                entity.getBloodPools().stream().map(BloodPoolEntity::getExternalId).toArray(String[]::new)
        );
        return this;
    }
}
