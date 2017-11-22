package com.alelk.bcpt.database.builder;

import com.alelk.bcpt.database.model.BloodInvoiceEntity;
import com.alelk.bcpt.database.model.BloodInvoiceSeriesEntity;
import com.alelk.bcpt.model.dto.BloodInvoiceSeriesDto;

import java.util.Set;

/**
 * Blood Invoice Series Entity Builder
 *
 * Created by Alex Elkin on 22.11.2017.
 */
public class BloodInvoiceSeriesEntityBuilder extends AbstractEntityBuilder<BloodInvoiceSeriesDto, BloodInvoiceSeriesEntity> {

    public BloodInvoiceSeriesEntityBuilder() {
        this(false);
    }

    public BloodInvoiceSeriesEntityBuilder(boolean mergeWithNullValues) {
        super(BloodInvoiceSeriesEntity.class, mergeWithNullValues);
    }

    public BloodInvoiceSeriesEntityBuilder(BloodInvoiceSeriesEntity existing, boolean mergeWithNullValues, boolean softMerge) {
        super(existing, mergeWithNullValues, softMerge);
    }

    @Override
    public BloodInvoiceSeriesEntityBuilder apply(BloodInvoiceSeriesDto dto) {
        super.apply(dto);
        if (dto == null) return this;
        setEntityFieldValue("seriesDate", dto.getSeriesDate());
        return this;
    }

    public BloodInvoiceSeriesEntityBuilder apply(Set<BloodInvoiceEntity> bloodInvoices) {
        setEntityFieldValue("bloodInvoices", bloodInvoices);
        return this;
    }
}
