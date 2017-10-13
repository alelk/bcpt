package com.alelk.bcpt.database.specifications;

import com.alelk.bcpt.database.model.AbstractEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Specification
 *
 * Created by Alex Elkin on 12.10.2017.
 */
public interface Specification<E extends AbstractEntity> {
    Predicate toPredicate(Root<E> root, CriteriaQuery query, CriteriaBuilder cb);
}
