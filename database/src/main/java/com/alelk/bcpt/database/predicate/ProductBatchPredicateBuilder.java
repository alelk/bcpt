package com.alelk.bcpt.database.predicate;

import com.alelk.bcpt.database.model.ProductBatchEntity;
import com.alelk.bcpt.database.model.ProductBatchEntity_;
import com.alelk.bcpt.database.specifications.ProductBatchSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Product Batch Predicate Builder
 *
 * Created by Alex Elkin on 26.10.2017.
 */
@Component
public class ProductBatchPredicateBuilder
        extends AbstractPredicateBuilder<ProductBatchEntity, ProductBatchEntity_, ProductBatchSpecification> {

    @Autowired
    public ProductBatchPredicateBuilder(ProductBatchSpecification specifications) {
        super(specifications);
    }
}
