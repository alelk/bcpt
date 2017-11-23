package com.alelk.bcpt.database.specifications;

import com.alelk.bcpt.database.model.*;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Join;

/**
 * Blood Pool Analysis Specification
 *
 * Created by Alex Elkin on 23.11.2017.
 */
@Component
public class BloodPoolAnalysisSpecification extends AbstractSpecifications<BloodPoolAnalysisEntity, BloodPoolAnalysisEntity_> {

    public Specification<BloodPoolAnalysisEntity> productBatchExternalIdEqual(Join<BloodPoolEntity, ProductBatchEntity> productBatches, String productBatchExternalId) {
        return stringStartsWith(productBatches.get(ProductBatchEntity_.externalId), productBatchExternalId);
    }
}
