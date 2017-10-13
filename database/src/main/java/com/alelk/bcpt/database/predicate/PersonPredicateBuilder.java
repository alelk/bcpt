package com.alelk.bcpt.database.predicate;

import com.alelk.bcpt.database.model.PersonEntity;
import com.alelk.bcpt.database.model.PersonEntity_;
import com.alelk.bcpt.database.specifications.PersonSpecifications;
import com.alelk.bcpt.database.specifications.Specification;
import com.alelk.bcpt.model.BloodType;
import com.alelk.bcpt.model.RhFactor;
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
 * Person Predicate Builder
 *
 * Created by Alex Elkin on 13.10.2017.
 */

@Component
public class PersonPredicateBuilder extends AbstractPredicateBuilder<PersonEntity, PersonEntity_, PersonSpecifications> {

    @Autowired
    public PersonPredicateBuilder(PersonSpecifications personSpecifications) {
        super(personSpecifications);
    }

    @Override
    public Predicate buildPredicate(Root<PersonEntity> root, CriteriaQuery query, CriteriaBuilder cb, List<Filter> filters) {
        if (filters == null || filters.size() == 0) return null;
        Predicate commonPredicate = super.buildPredicate(root, query, cb, filters);
        List<Specification<PersonEntity>> specifications = new ArrayList<>(filters.size());
        for (Filter filter: filters) {
            if ("firstName".equals(filter.getFieldName()))
                specifications.add(this.specifications.firstNameStartsWith(filter.getFilter()));
            if ("lastName".equals(filter.getFieldName()))
                specifications.add(this.specifications.lastNameStartsWith(filter.getFilter()));
            if ("middleName".equals(filter.getFieldName()))
                specifications.add(this.specifications.middleNameStartsWith(filter.getFilter()));
            if ("rhFactor".equals(filter.getFieldName()))
                specifications.add(this.specifications.rhFactorEqual(RhFactor.forSignature(filter.getFilter())));
            if ("bloodType".equals(filter.getFieldName()))
                specifications.add(this.specifications.bloodTypeEqual(BloodType.forString(filter.getFilter())));
        }
        return and(cb, commonPredicate, and(root, query, cb, specifications));
    }
}
