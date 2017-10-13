package com.alelk.bcpt.database.specifications;

import com.alelk.bcpt.database.model.AbstractEntity;
import com.alelk.bcpt.database.model.AbstractEntity_;
import org.springframework.stereotype.Component;

import javax.persistence.metamodel.SingularAttribute;

/**
 * AbstractSpecifications
 *
 * Created by Alex Elkin on 12.10.2017.
 */
@Component
public abstract class AbstractSpecifications<E extends AbstractEntity, M extends AbstractEntity_> {
    public Specification<E> externalIdStartsWith(String externalId) {
        return stringFieldStartsWith(M.externalId, externalId);
    }

    Specification<E> stringFieldStartsWith(SingularAttribute<? super E, String> field, String stringValue) {
        return (root, query, cb) -> stringValue == null ? null : cb.like(root.get(field), stringValue + '%');
    }

    <T> Specification<E> valueEqual(SingularAttribute<? super E, T> field, T value) {
        return (root, query, cb) -> value == null ? null : cb.equal(root.get(field), value);
    }
}
