package com.alelk.bcpt.database.builder;

import com.alelk.bcpt.database.model.BloodDonationEntity;
import com.alelk.bcpt.model.dto.BloodDonationDto;

/**
 * Blood Donation Dto Builder
 *
 * Created by Alex Elkin on 20.09.2017.
 */
public class BloodDonationDtoBuilder extends AbstractDtoBuilder<BloodDonationEntity, BloodDonationDto> {

    public BloodDonationDtoBuilder() {
        super(BloodDonationDto.class);
    }

    @Override
    public BloodDonationDtoBuilder apply(BloodDonationEntity entity) {
        super.apply(entity);
        if (entity == null) return this;
        dto.setDonor(entity.getDonor() != null ? entity.getDonor().getExternalId() : null);
        dto.setBloodInvoice(entity.getBloodInvoice() != null ? entity.getBloodInvoice().getExternalId() : null);
        dto.setBloodPool(entity.getBloodPool() != null ? entity.getBloodPool().getExternalId() : null);
        dto.setAmount(entity.getAmount());
        dto.setDonationType(entity.getDonationType());
        dto.setBloodType(entity.getBloodType());
        dto.setRhFactor(entity.getRhFactor());
        dto.setAnalysisConclusion(entity.getAnalysisConclusion());
        dto.setDonationDate(entity.getDonationDate());
        dto.setExpirationDate(entity.getExpirationDate());
        dto.setQuarantineDate(entity.getQuarantineDate());
        return this;
    }
}
