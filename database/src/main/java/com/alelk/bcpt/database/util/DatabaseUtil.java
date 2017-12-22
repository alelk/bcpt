package com.alelk.bcpt.database.util;

import com.alelk.bcpt.database.builder.*;
import com.alelk.bcpt.database.model.*;
import com.alelk.bcpt.model.dto.*;

/**
 * DatabaseUtil
 *
 * Created by Alex Elkin on 10.10.2017.
 */
public class DatabaseUtil {

    public static PersonDto mapPersonEntityToDto(PersonEntity entity) {
        return entity == null ? null : new PersonDtoBuilder().apply(entity).build();
    }

    public static BloodDonationDto mapBloodDonationEntityToDto(BloodDonationEntity entity) {
        return entity == null ? null : new BloodDonationDtoBuilder().apply(entity).build();
    }

    public static BloodInvoiceDto mapBloodInvoiceEntityToDto(BloodInvoiceEntity entity) {
        return entity == null ? null : new BloodInvoiceDtoBuilder().apply(entity).build();
    }

    public static BloodInvoiceSeriesDto mapBloodInvoiceSeriesEntityToDto(BloodInvoiceSeriesEntity entity) {
        return entity == null ? null : new BloodInvoiceSeriesDtoBuilder().apply(entity).build();
    }

    public static BloodPoolDto mapBloodPoolEntityToDto(BloodPoolEntity entity) {
        return entity == null ? null : new BloodPoolDtoBuilder().apply(entity).build();
    }

    public static BloodPoolAnalysisDto mapBloodPoolAnalysisEntityToDto(BloodPoolAnalysisEntity entity) {
        return entity == null ? null : new BloodPoolAnalysisDtoBuilder().apply(entity).build();
    }

    public static ProductBatchDto mapProductBatchEntityToDto(ProductBatchEntity entity) {
        return entity == null ? null : new ProductBatchDtoBuilder().apply(entity).build();
    }
}
