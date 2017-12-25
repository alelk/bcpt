package com.alelk.bcpt.database.builder;

import com.alelk.bcpt.database.model.BloodInvoiceEntity;
import com.alelk.bcpt.database.model.BloodInvoiceSeriesEntity;
import com.alelk.bcpt.model.dto.BloodInvoiceSeriesDto;

import java.util.stream.Collectors;

/**
 * Blood Invoice Series Dto Builder
 *
 * Created by Alex Elkin on 22.11.2017.
 */
public class BloodInvoiceSeriesDtoBuilder extends AbstractDtoBuilder<BloodInvoiceSeriesEntity, BloodInvoiceSeriesDto> {

    public BloodInvoiceSeriesDtoBuilder() {
        super(BloodInvoiceSeriesDto.class);
    }

    @Override
    public BloodInvoiceSeriesDtoBuilder apply(BloodInvoiceSeriesEntity entity) {
        super.apply(entity);
        if (entity == null) return this;
        dto.setSeriesDate(entity.getSeriesDate());
        dto.setTotalAmount(entity.getTotalAmount());
        dto.setAnalysisConclusion(entity.getAnalysisConclusion());
        dto.setBloodInvoices(
                entity.getBloodInvoices() != null ? entity.getBloodInvoices().stream()
                        .map(BloodInvoiceEntity::getExternalId).collect(Collectors.toSet()) : null
        );
        return this;
    }
}
