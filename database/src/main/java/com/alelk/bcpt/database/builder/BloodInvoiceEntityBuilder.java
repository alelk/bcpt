package com.alelk.bcpt.database.builder;

import com.alelk.bcpt.database.model.BloodInvoiceEntity;
import com.alelk.bcpt.database.model.BloodDonationEntity;
import com.alelk.bcpt.model.dto.BloodInvoiceDto;

import java.util.HashSet;
import java.util.Set;

/**
 * Blood Donation Delivery Entity Builder
 *
 * Created by Alex Elkin on 22.09.2017.
 */
public class BloodInvoiceEntityBuilder extends AbstractEntityBuilder<BloodInvoiceDto, BloodInvoiceEntity> {

    public BloodInvoiceEntityBuilder() {
        this(false);
    }

    public BloodInvoiceEntityBuilder(boolean mergeWithNullValues) {
        super(BloodInvoiceEntity.class, mergeWithNullValues);
    }

    public BloodInvoiceEntityBuilder(BloodInvoiceEntity existingEntity, boolean mergeWithNullValues) {
        super(existingEntity, mergeWithNullValues);
    }

    @Override
    public BloodInvoiceEntityBuilder apply(BloodInvoiceDto dto) {
        super.apply(dto);
        if (dto == null) return this;
        if (mergeWithNullValues || dto.getDeliveryDate() != null)
            entity.setDeliveryDate(dto.getDeliveryDate());
        return this;
    }

    public BloodInvoiceEntityBuilder apply(Set<BloodDonationEntity> bloodDonationEntities) {
        if (mergeWithNullValues && bloodDonationEntities == null)
            entity.setBloodDonations(new HashSet<>());
        else
            entity.setBloodDonations(bloodDonationEntities);
        return this;
    }
}
