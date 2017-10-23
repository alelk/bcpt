package com.alelk.bcpt.database.util;

import com.alelk.bcpt.database.builder.BloodDonationDtoBuilder;
import com.alelk.bcpt.database.builder.BloodInvoiceDtoBuilder;
import com.alelk.bcpt.database.builder.PersonDtoBuilder;
import com.alelk.bcpt.database.model.BloodDonationEntity;
import com.alelk.bcpt.database.model.BloodInvoiceEntity;
import com.alelk.bcpt.database.model.PersonEntity;
import com.alelk.bcpt.model.dto.BloodDonationDto;
import com.alelk.bcpt.model.dto.BloodInvoiceDto;
import com.alelk.bcpt.model.dto.PersonDto;

/**
 * DatabaseUtil
 *
 * Created by Alex Elkin on 10.10.2017.
 */
public class DatabaseUtil {

    public static PersonDto mapPersonEntityToDto(PersonEntity entity) {
        return new PersonDtoBuilder().apply(entity).build();
    }

    public static BloodDonationDto mapBloodDonationEntityToDto(BloodDonationEntity entity) {
        return new BloodDonationDtoBuilder().apply(entity).build();
    }

    public static BloodInvoiceDto mapBloodInvoiceEntityToDto(BloodInvoiceEntity entity) {
        return new BloodInvoiceDtoBuilder().apply(entity).build();
    }
}
