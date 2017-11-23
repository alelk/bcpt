package com.alelk.bcpt.database.builder;

import com.alelk.bcpt.database.model.BloodPoolAnalysisEntity;
import com.alelk.bcpt.database.model.BloodPoolEntity;
import com.alelk.bcpt.model.dto.BloodPoolAnalysisDto;

/**
 * Blood Pool Analysis Entity Builder
 *
 * Created by Alex Elkin on 23.11.2017.
 */
public class BloodPoolAnalysisEntityBuilder extends AbstractEntityBuilder<BloodPoolAnalysisDto, BloodPoolAnalysisEntity> {

    public BloodPoolAnalysisEntityBuilder() {
        this(false);
    }

    public BloodPoolAnalysisEntityBuilder(boolean mergeWithNullValues) {
        super(BloodPoolAnalysisEntity.class, mergeWithNullValues);
    }

    public BloodPoolAnalysisEntityBuilder(BloodPoolAnalysisEntity existingEntity, boolean mergeWithNullValues, boolean softMerge) {
        super(existingEntity, mergeWithNullValues, softMerge);
    }

    @Override
    public BloodPoolAnalysisEntityBuilder apply(BloodPoolAnalysisDto dto) {
        super.apply(dto);
        if (dto == null) return this;
        setEntityFieldValue("pH", dto.getpH());
        setEntityFieldValue("proteinConcentration", dto.getProteinConcentration());
        return this;
    }

    public BloodPoolAnalysisEntityBuilder apply(BloodPoolEntity bloodPool) {
        setEntityFieldValue("bloodPool", bloodPool);
        return this;
    }
}
