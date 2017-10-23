package com.alelk.bcpt.database.predicate;

import com.alelk.bcpt.database.model.BloodDonationEntity;
import com.alelk.bcpt.database.model.BloodDonationEntity_;
import com.alelk.bcpt.database.model.BloodInvoiceEntity;
import com.alelk.bcpt.database.model.PersonEntity;
import com.alelk.bcpt.database.specifications.BloodDonationSpecifications;
import com.alelk.bcpt.database.specifications.Specification;
import com.alelk.bcpt.model.DonationType;
import com.alelk.bcpt.model.pagination.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Blood Donation Predicate Builder
 *
 * Created by Alex Elkin on 20.10.2017.
 */
@Component
public class BloodDonationPredicateBuilder extends AbstractPredicateBuilder<BloodDonationEntity, BloodDonationEntity_, BloodDonationSpecifications> {

    @Autowired
    public BloodDonationPredicateBuilder(BloodDonationSpecifications specifications) {
        super(specifications);
    }

    @Override
    public Predicate buildPredicate(Root<BloodDonationEntity> root, CriteriaQuery query, CriteriaBuilder cb, List<Filter> filters) {
        if (filters == null || filters.size() == 0) return null;
        List<Specification<BloodDonationEntity>> specifications = new ArrayList<>(filters.size());
        Join<BloodDonationEntity, PersonEntity> persons = root.join(BloodDonationEntity_.donor, JoinType.LEFT);
        Join<BloodDonationEntity, BloodInvoiceEntity> invoices = root.join(BloodDonationEntity_.bloodInvoice, JoinType.LEFT);
        Predicate commonPredicate = super.buildPredicate(root, query, cb, filters);
        for (Filter filter: filters) {
            if ("donationType".equals(filter.getFieldName()))
                specifications.add(this.specifications.donationTypeEqual(DonationType.forSignature(filter.getFilter())));
            if ("donorExternalId".equals(filter.getFieldName()))
                specifications.add(this.specifications.donorExternalIdStartsWith(persons, filter.getFilter()));
            if ("bloodInvoiceExternalId".equals(filter.getFieldName()))
                specifications.add(this.specifications.invoiceExternalIdStartsWith(invoices, filter.getFilter()));
        }
        return and(cb, commonPredicate, and(root, query, cb, specifications));
    }
}
