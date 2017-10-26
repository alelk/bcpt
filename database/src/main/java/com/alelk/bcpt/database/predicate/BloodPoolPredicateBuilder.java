package com.alelk.bcpt.database.predicate;

import com.alelk.bcpt.database.model.BloodPoolEntity;
import com.alelk.bcpt.database.model.BloodPoolEntity_;
import com.alelk.bcpt.database.specifications.BloodPoolSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Blood Pool Predicate Builder
 *
 * Created by Alex Elkin on 26.10.2017.
 */
@Component
public class BloodPoolPredicateBuilder extends AbstractPredicateBuilder<BloodPoolEntity, BloodPoolEntity_, BloodPoolSpecifications> {

    @Autowired
    public BloodPoolPredicateBuilder(BloodPoolSpecifications specifications) {
        super(specifications);
    }
}
