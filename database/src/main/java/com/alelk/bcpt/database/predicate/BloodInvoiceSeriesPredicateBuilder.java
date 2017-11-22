package com.alelk.bcpt.database.predicate;

import com.alelk.bcpt.database.model.BloodInvoiceSeriesEntity;
import com.alelk.bcpt.database.model.BloodInvoiceSeriesEntity_;
import com.alelk.bcpt.database.specifications.BloodInvoiceSeriesSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Blood Invoice Series Predicate Builder
 *
 * Created by Alex Elkin on 22.11.2017.
 */
@Component
public class BloodInvoiceSeriesPredicateBuilder extends AbstractPredicateBuilder<BloodInvoiceSeriesEntity, BloodInvoiceSeriesEntity_, BloodInvoiceSeriesSpecification> {

    @Autowired
    public BloodInvoiceSeriesPredicateBuilder(BloodInvoiceSeriesSpecification specifications) {
        super(specifications);
    }
}
