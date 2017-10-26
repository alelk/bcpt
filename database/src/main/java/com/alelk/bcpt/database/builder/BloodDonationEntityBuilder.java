package com.alelk.bcpt.database.builder;

import com.alelk.bcpt.database.model.BloodDonationEntity;
import com.alelk.bcpt.database.model.BloodInvoiceEntity;
import com.alelk.bcpt.database.model.BloodPoolEntity;
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

    public BloodDonationEntityBuilder(BloodDonationEntity existingEntity, boolean mergeWithNullValues, boolean softUpdate) {
        super(existingEntity, mergeWithNullValues, softUpdate);
    }

    @Override
    public BloodDonationEntityBuilder apply(BloodDonationDto dto) {
        super.apply(dto);
        if (dto == null) return this;
        setEntityFieldValue("amount", dto.getAmount());
        setEntityFieldValue("donationType", dto.getDonationType());
        setEntityFieldValue("donationDate", dto.getDonationDate());
        setEntityFieldValue("expirationDate", dto.getExpirationDate());
        setEntityFieldValue("quarantineDate", dto.getQuarantineDate());
        return this;
    }

    public BloodDonationEntityBuilder apply(PersonEntity personEntity) {
        setEntityFieldValue("donor", personEntity);
        return this;
    }

    public BloodDonationEntityBuilder apply(BloodInvoiceEntity bloodInvoiceEntity) {
        setEntityFieldValue("bloodInvoice", bloodInvoiceEntity);
        return this;
    }

    public BloodDonationEntityBuilder apply(BloodPoolEntity bloodPoolEntity) {
        setEntityFieldValue("bloodPool", bloodPoolEntity);
        return this;
    }
}
