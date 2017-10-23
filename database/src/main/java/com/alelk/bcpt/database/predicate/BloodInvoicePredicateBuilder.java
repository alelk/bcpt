package com.alelk.bcpt.database.predicate;

import com.alelk.bcpt.database.model.BloodInvoiceEntity;
import com.alelk.bcpt.database.model.BloodInvoiceEntity_;
import com.alelk.bcpt.database.specifications.BloodInvoiceSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Blood Invoice Predicate Builder
 *
 * Created by Alex Elkin on 20.10.2017.
 */
@Component
public class BloodInvoicePredicateBuilder extends AbstractPredicateBuilder<BloodInvoiceEntity, BloodInvoiceEntity_, BloodInvoiceSpecifications> {

    @Autowired
    public BloodInvoicePredicateBuilder(BloodInvoiceSpecifications specifications) {
        super(specifications);
    }
}
