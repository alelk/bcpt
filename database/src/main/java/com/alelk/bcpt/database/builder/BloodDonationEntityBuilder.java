package com.alelk.bcpt.database.builder;

import com.alelk.bcpt.database.model.BloodDonationEntity;
import com.alelk.bcpt.database.model.BloodInvoiceEntity;
import com.alelk.bcpt.database.model.PersonEntity;
import com.alelk.bcpt.model.dto.BloodDonationDto;

/**
 * Blood Donation Entity Builder
 *
 * Created by Alex Elkin on 20.09.2017.
 */
public class BloodDonationEntityBuilder extends AbstractEntityBuilder<BloodDonationDto, BloodDonationEntity> {

    public BloodDonationEntityBuilder() {
        this(false);
    }

    public BloodDonationEntityBuilder(boolean mergeWithNullValues) {
        super(BloodDonationEntity.class, mergeWithNullValues);
    }

    public BloodDonationEntityBuilder(BloodDonationEntity existingEntity, boolean mergeWithNullValues) {
        super(existingEntity, mergeWithNullValues);
    }

    @Override
    public BloodDonationEntityBuilder apply(BloodDonationDto dto) {
        super.apply(dto);
        if (dto == null) return this;
        if (mergeWithNullValues || dto.getAmount() != null)
            entity.setAmount(dto.getAmount());
        if (mergeWithNullValues || dto.getDonationDate() != null)
            entity.setDonationDate(dto.getDonationDate());
        if (mergeWithNullValues || dto.getQuarantineDate() != null)
            entity.setQuarantineDate(dto.getQuarantineDate());
        return this;
    }

    public BloodDonationEntityBuilder apply(PersonEntity personEntity) {
        if (mergeWithNullValues || personEntity != null)
            entity.setDonor(personEntity);
        return this;
    }

    public BloodDonationEntityBuilder apply(BloodInvoiceEntity bloodInvoiceEntity) {
        if (mergeWithNullValues || bloodInvoiceEntity != null)
            entity.setBloodInvoice(bloodInvoiceEntity);
        return this;
    }
}
