package com.alelk.bcpt.database.predicate;

import com.alelk.bcpt.database.model.*;
import com.alelk.bcpt.database.specifications.BloodPoolAnalysisSpecification;
import com.alelk.bcpt.database.specifications.BloodPoolSpecifications;
import com.alelk.bcpt.database.specifications.Specification;
import com.alelk.bcpt.model.pagination.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Blood Pool Analysis Predicate Builder
 *
 * Created by Alex Elkin on 23.11.2017.
 */
@Component
public class BloodPoolAnalysisPredicateBuilder extends AbstractPredicateBuilder<BloodPoolAnalysisEntity, BloodPoolAnalysisEntity_, BloodPoolAnalysisSpecification> {

    @Autowired
    public BloodPoolAnalysisPredicateBuilder(BloodPoolAnalysisSpecification specifications) {
        super(specifications);
    }

    @Override
    public Predicate buildPredicate(Root<BloodPoolAnalysisEntity> root, CriteriaQuery query, CriteriaBuilder cb, List<Filter> filters) {
        if (filters == null || filters.size() == 0) return null;
        List<Specification<BloodPoolAnalysisEntity>> specifications = new ArrayList<>(filters.size());
        Join<BloodPoolAnalysisEntity, BloodPoolEntity> bloodPools = root.join(BloodPoolAnalysisEntity_.bloodPool, JoinType.LEFT);
        Join<BloodPoolEntity, ProductBatchEntity> productBatches = bloodPools.join(BloodPoolEntity_.productBatch, JoinType.LEFT);
        Predicate commonPredicate = super.buildPredicate(root, query, cb, filters);
        for (Filter filter: filters) {
            if ("productBatch".equals(filter.getFieldName()))
                specifications.add(this.specifications.productBatchExternalIdEqual(productBatches, filter.getFilter()));
        }
        return and(cb, commonPredicate, and(root, query, cb, specifications));
    }
}
