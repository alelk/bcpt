package com.alelk.bcpt.database.predicate;

import com.alelk.bcpt.database.model.BloodPoolEntity;
import com.alelk.bcpt.database.model.BloodPoolEntity_;
import com.alelk.bcpt.database.model.ProductBatchEntity;
import com.alelk.bcpt.database.specifications.BloodPoolSpecifications;
import com.alelk.bcpt.database.specifications.Specification;
import com.alelk.bcpt.model.pagination.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public Predicate buildPredicate(Root<BloodPoolEntity> root, CriteriaQuery query, CriteriaBuilder cb, List<Filter> filters) {
        if (filters == null || filters.size() == 0) return null;
        List<Specification<BloodPoolEntity>> specifications = new ArrayList<>(filters.size());
        Join<BloodPoolEntity, ProductBatchEntity> productBatches = root.join(BloodPoolEntity_.productBatch, JoinType.LEFT);
        Predicate commonPredicate = super.buildPredicate(root, query, cb, filters);
        for (Filter filter: filters) {
            if ("productBatch".equals(filter.getFieldName()))
                specifications.add(this.specifications.productBatchExternalIdEqual(productBatches, filter.getFilter()));
            if ("poolNumber".equals(filter.getFieldName()))
                specifications.add(this.specifications.poolNumberEqual(filter.getFilter()));
        }
        return and(cb, commonPredicate, and(root, query, cb, specifications));
    }
}
