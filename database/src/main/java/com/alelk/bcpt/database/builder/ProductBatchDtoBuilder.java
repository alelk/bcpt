package com.alelk.bcpt.database.builder;

import com.alelk.bcpt.database.model.BloodPoolEntity;
import com.alelk.bcpt.database.model.ProductBatchEntity;
import com.alelk.bcpt.model.dto.ProductBatchDto;

import java.util.stream.Collectors;

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
        dto.setBatchNumber(entity.getBatchNumber());
        dto.setBatchDate(entity.getBatchDate());
        dto.setTotalAmount(entity.getTotalAmount());
        dto.setBatchAuthor(entity.getBatchAuthor());
        dto.setLocation(entity.getLocation());
        dto.setProductName(entity.getProductName());
        dto.setProductProvider(entity.getProductProvider());
        dto.setBloodPools(
                entity.getBloodPools() != null ?
                entity.getBloodPools().stream().map(BloodPoolEntity::getExternalId).collect(Collectors.toSet()) : null
        );
        return this;
    }
}
