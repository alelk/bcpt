package com.alelk.bcpt.database.predicate;

import com.alelk.bcpt.database.model.BloodInvoiceEntity;
import com.alelk.bcpt.database.model.BloodInvoiceEntity_;
import com.alelk.bcpt.database.model.BloodInvoiceSeriesEntity;
import com.alelk.bcpt.database.specifications.BloodInvoiceSpecifications;
import com.alelk.bcpt.database.specifications.Specification;
import com.alelk.bcpt.model.pagination.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public Predicate buildPredicate(Root<BloodInvoiceEntity> root, CriteriaQuery query, CriteriaBuilder cb, List<Filter> filters) {
        if (filters == null || filters.size() == 0) return null;
        List<Specification<BloodInvoiceEntity>> specifications = new ArrayList<>(filters.size());
        Join<BloodInvoiceEntity, BloodInvoiceSeriesEntity> invoiceSeries = root.join(BloodInvoiceEntity_.bloodInvoiceSeries, JoinType.LEFT);
        Predicate commonPredicate = super.buildPredicate(root, query, cb, filters);
        for (Filter filter: filters) {
            if ("bloodInvoiceSeries".equals(filter.getFieldName()))
                specifications.add(this.specifications.bloodInvoiceSeriesExternalIdStartsWith(invoiceSeries, filter.getFilter()));
        }
        return and(cb, commonPredicate, and(root, query, cb, specifications));
    }
}
