package com.alelk.bcpt.database.builder;

import com.alelk.bcpt.database.model.BloodDonationEntity;
import com.alelk.bcpt.database.model.BloodPoolEntity;
import com.alelk.bcpt.database.model.ProductBatchEntity;
import com.alelk.bcpt.model.dto.BloodPoolDto;

import java.util.Set;

/**
 * Blood Donation Pool Entity Builder
 *
 * Created by Alex Elkin on 22.09.2017.
 */
public class BloodPoolEntityBuilder extends AbstractEntityBuilder<BloodPoolDto, BloodPoolEntity> {

    public BloodPoolEntityBuilder() {
        this(false);
    }

    public BloodPoolEntityBuilder(boolean mergeWithNullValues) {
        super(BloodPoolEntity.class, mergeWithNullValues);
    }

    public BloodPoolEntityBuilder(BloodPoolEntity existingEntity, boolean mergeWithNullValues, boolean softUpdate) {
        super(existingEntity, mergeWithNullValues, softUpdate);
    }

    @Override
    public BloodPoolEntityBuilder apply(BloodPoolDto dto) {
        super.apply(dto);
        if (dto == null) return this;
        setEntityFieldValue("poolNumber", dto.getPoolNumber());
        return this;
    }

    public BloodPoolEntityBuilder apply(Set<BloodDonationEntity> bloodDonations) {
        setEntityFieldValue("bloodDonations", bloodDonations);
        return this;
    }

    public BloodPoolEntityBuilder apply(ProductBatchEntity productBatchEntity) {
        setEntityFieldValue("productBatchEntity", productBatchEntity);
        return this;
    }
}
