package com.alelk.bcpt.database.specifications;

import com.alelk.bcpt.database.model.BloodInvoiceEntity;
import com.alelk.bcpt.database.model.BloodInvoiceEntity_;
import com.alelk.bcpt.database.model.BloodInvoiceSeriesEntity;
import com.alelk.bcpt.database.model.BloodInvoiceSeriesEntity_;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Join;

/**
 * Blood Invoice Specifications
 *
 * Created by Alex Elkin on 20.10.2017.
 */

@Component
public class BloodInvoiceSpecifications extends AbstractSpecifications<BloodInvoiceEntity, BloodInvoiceEntity_> {

    public Specification<BloodInvoiceEntity> bloodInvoiceSeriesExternalIdStartsWith(
            Join<BloodInvoiceEntity, BloodInvoiceSeriesEntity> bloodInvoiceSeries, String bloodInvoiceSeriesExternalId
    ) {
        return stringStartsWith(bloodInvoiceSeries.get(BloodInvoiceSeriesEntity_.externalId), bloodInvoiceSeriesExternalId);
    }
}
