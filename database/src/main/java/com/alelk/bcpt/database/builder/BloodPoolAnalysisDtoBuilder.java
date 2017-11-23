package com.alelk.bcpt.database.builder;

import com.alelk.bcpt.database.model.BloodDonationEntity;
import com.alelk.bcpt.database.model.BloodPoolAnalysisEntity;
import com.alelk.bcpt.model.dto.BloodPoolAnalysisDto;

import java.util.stream.Collectors;

/**
 * Blood Pool Analysis DTO Builder
 *
 * Created by Alex Elkin on 23.11.2017.
 */
public class BloodPoolAnalysisDtoBuilder extends AbstractDtoBuilder<BloodPoolAnalysisEntity, BloodPoolAnalysisDto> {

    public BloodPoolAnalysisDtoBuilder() {
        super(BloodPoolAnalysisDto.class);
    }

    @Override
    public BloodPoolAnalysisDtoBuilder apply(BloodPoolAnalysisEntity entity) {
        super.apply(entity);
        if (entity == null) return this;
        dto.setpH(entity.getpH());
        dto.setProteinConcentration(entity.getProteinConcentration());
        dto.setPoolNumber(entity.getPoolNumber());
        dto.setProductBatch(entity.getProductBatch() != null ? entity.getProductBatch().getExternalId() : null);
        dto.setBloodDonations(
                entity.getBloodDonations() != null ? entity.getBloodDonations().stream()
                        .map(BloodDonationEntity::getExternalId).collect(Collectors.toSet()) : null
        );
        return this;
    }
}
