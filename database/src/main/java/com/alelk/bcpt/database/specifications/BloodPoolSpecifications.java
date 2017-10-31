package com.alelk.bcpt.database.specifications;

import com.alelk.bcpt.database.model.BloodPoolEntity;
import com.alelk.bcpt.database.model.BloodPoolEntity_;
import com.alelk.bcpt.database.model.ProductBatchEntity;
import com.alelk.bcpt.database.model.ProductBatchEntity_;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Join;

/**
 * Blood Pool Specifications
 *
 * Created by Alex Elkin on 26.10.2017.
 */
@Component
public class BloodPoolSpecifications extends AbstractSpecifications<BloodPoolEntity, BloodPoolEntity_> {

    public Specification<BloodPoolEntity> productBatchExternalIdEqual(Join<BloodPoolEntity, ProductBatchEntity> productBatches, String productBatchExternalId) {
        return valueEqual(productBatches.get(ProductBatchEntity_.externalId), productBatchExternalId);
    }

    public Specification<BloodPoolEntity> poolNumberEqual(String poolNumber) {
        return valueEqual(BloodPoolEntity_.poolNumber, poolNumber);
    }
}
