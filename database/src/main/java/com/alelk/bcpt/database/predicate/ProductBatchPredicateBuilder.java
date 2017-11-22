package com.alelk.bcpt.database.predicate;

import com.alelk.bcpt.database.model.ProductBatchEntity;
import com.alelk.bcpt.database.model.ProductBatchEntity_;
import com.alelk.bcpt.database.specifications.ProductBatchSpecification;
import com.alelk.bcpt.database.specifications.Specification;
import com.alelk.bcpt.model.pagination.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public Predicate buildPredicate(Root<ProductBatchEntity> root, CriteriaQuery query, CriteriaBuilder cb, List<Filter> filters) {
        if (filters == null || filters.size() == 0) return null;
        List<Specification<ProductBatchEntity>> specifications = new ArrayList<>(filters.size());
        Predicate commonPredicate = super.buildPredicate(root, query, cb, filters);
        for (Filter filter: filters) {
            if ("batchNumber".equals(filter.getFieldName()))
                specifications.add(this.specifications.batchNumberEqual(filter.getFilter()));
            else if ("productName".equals(filter.getFieldName()))
                specifications.add(this.specifications.productNameStartsWith(filter.getFilter()));
            else if ("location".equals(filter.getFieldName()))
                specifications.add(this.specifications.locationStartsWith(filter.getFilter()));
            else if ("productProvider".equals(filter.getFieldName()))
                specifications.add(this.specifications.productProviderStartsWith(filter.getFilter()));
            else if ("batchAuthor".equals(filter.getFieldName()))
                specifications.add(this.specifications.batchAuthorStartsWith(filter.getFilter()));
        }
        return and(cb, commonPredicate, and(root, query, cb, specifications));
    }
}
