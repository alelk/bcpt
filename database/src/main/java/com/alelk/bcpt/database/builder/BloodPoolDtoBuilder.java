package com.alelk.bcpt.database.builder;

import com.alelk.bcpt.database.model.BloodDonationEntity;
import com.alelk.bcpt.database.model.BloodPoolEntity;
import com.alelk.bcpt.model.dto.BloodPoolDto;

import java.util.stream.Collectors;

/**
 * Blood Donation Pool DTO Builder
 *
 * Created by Alex Elkin on 22.09.2017.
 */
public class BloodPoolDtoBuilder extends AbstractDtoBuilder<BloodPoolEntity, BloodPoolDto> {

    public BloodPoolDtoBuilder() {
        super(BloodPoolDto.class);
    }

    @Override
    public BloodPoolDtoBuilder apply(BloodPoolEntity entity) {
        super.apply(entity);
        if (entity == null) return this;
        dto.setPoolNumber(entity.getPoolNumber());
        dto.setTotalAmount(entity.getTotalAmount());
        dto.setAnalysisConclusion(entity.getAnalysisConclusion());
        dto.setProductBatch(entity.getProductBatch() != null ? entity.getProductBatch().getExternalId() : null);
        dto.setBloodDonations(
                entity.getBloodDonations() != null ? entity.getBloodDonations().stream()
                        .map(BloodDonationEntity::getExternalId).collect(Collectors.toSet()) : null
        );
        return this;
    }
}
