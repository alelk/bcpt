package com.alelk.bcpt.database.builder;

import com.alelk.bcpt.database.model.BloodInvoiceEntity;
import com.alelk.bcpt.database.model.BloodDonationEntity;
import com.alelk.bcpt.model.dto.BloodInvoiceDto;

import java.util.stream.Collectors;

/**
 * Blood Donation Delivery DTO Builder
 *
 * Created by Alex Elkin on 22.09.2017.
 */
public class BloodInvoiceDtoBuilder extends AbstractDtoBuilder<BloodInvoiceEntity, BloodInvoiceDto> {

    public BloodInvoiceDtoBuilder() {
        super(BloodInvoiceDto.class);
    }

    @Override
    public BloodInvoiceDtoBuilder apply(BloodInvoiceEntity entity) {
        super.apply(entity);
        if (entity == null) return this;
        dto.setDeliveryDate(entity.getDeliveryDate());
        dto.setTotalAmount(entity.getTotalAmount());
        dto.setBloodPoolExternalId(entity.getBloodPool() != null ? entity.getBloodPool().getExternalId() : null);
        dto.setBloodDonationExternalIds(entity.getBloodDonations() != null ? entity.getBloodDonations().stream().map(BloodDonationEntity::getExternalId).collect(Collectors.toSet()) : null);
        return this;
    }
}
