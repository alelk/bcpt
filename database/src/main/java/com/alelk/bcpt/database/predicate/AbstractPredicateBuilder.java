package com.alelk.bcpt.database.predicate;

import com.alelk.bcpt.database.model.AbstractEntity;
import com.alelk.bcpt.database.model.AbstractEntity_;
import com.alelk.bcpt.database.specifications.AbstractSpecifications;
import com.alelk.bcpt.database.specifications.Specification;
import com.alelk.bcpt.model.pagination.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Abstract Predicate Builder
 *
 * Created by Alex Elkin on 12.10.2017.
 */
public abstract class AbstractPredicateBuilder<E extends AbstractEntity, M extends AbstractEntity_, S extends AbstractSpecifications<E, M>> {

    protected S specifications;

    public AbstractPredicateBuilder(S specifications) {
        this.specifications = specifications;
    }

    public Predicate buildPredicate(Root<E> root, CriteriaQuery query, CriteriaBuilder cb, List<Filter> filters) {
        if (filters == null || filters.size() == 0) return null;
        List<Specification<E>> specifications = new ArrayList<>(filters.size());
        for (Filter filter: filters) {
            if ("externalId".equals(filter.getFieldName()))
                specifications.add(this.specifications.externalIdStartsWith(filter.getFilter()));
        }
        return and(root, query, cb, specifications);
    }

    Predicate and(Root<E> root, CriteriaQuery query, CriteriaBuilder cb, List<Specification<E>> specifications) {
        return specifications == null || specifications.size() == 0 ? null :
                and(
                        cb,
                        specifications.stream()
                                .filter(Objects::nonNull)
                                .map(s -> s.toPredicate(root, query, cb))
                                .toArray(Predicate[]::new)
                );
    }

    Predicate and(CriteriaBuilder cb, Predicate... predicates) {
        if (predicates == null || predicates.length == 0) return null;
        Predicate[] filtered = Arrays.stream(predicates).filter(Objects::nonNull).toArray(Predicate[]::new);
        if (filtered.length == 0) return null;
        return cb.and(filtered);
    }

    Filter getFilter(String fieldName, List<Filter> filters) {
        return filters.stream().filter(f -> fieldName.equals(f.getFieldName())).findFirst().orElse(null);
    }
}
