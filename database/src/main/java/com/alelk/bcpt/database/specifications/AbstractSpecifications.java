package com.alelk.bcpt.database.specifications;

import com.alelk.bcpt.database.model.AbstractEntity;
import com.alelk.bcpt.database.model.AbstractEntity_;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Path;
import javax.persistence.metamodel.SingularAttribute;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * AbstractSpecifications
 *
 * Created by Alex Elkin on 12.10.2017.
 */
@Component
public abstract class AbstractSpecifications<E extends AbstractEntity, M extends AbstractEntity_> {

    private static String normalizeSearchString(String stringValue) {
       return stringValue == null ? null : stringValue
               .replaceAll("-", "\\-")
               .replaceAll("%", "\\%")
               .replaceAll("_", "\\_");
    }

    public Specification<E> externalIdStartsWith(String externalId) {
        return stringFieldStartsWith(M.externalId, externalId);
    }

    Specification<E> stringFieldStartsWith(SingularAttribute<? super E, String> field, String stringValue) {
        return (root, query, cb) -> stringValue == null ? null
                : cb.like(root.get(field), normalizeSearchString(stringValue) + '%', '\\');
    }

    Specification<E> stringStartsWith(Path<String> path, String stringValue) {
        return (root, query, cb) -> stringValue == null ? null
                : cb.like(path, normalizeSearchString(stringValue) + '%', '\\');
    }

    Specification<E> valueEqual(SingularAttribute<? super E, ?> field, Object value) {
        return (root, query, cb) -> value == null ? null : cb.equal(root.get(field), value);
    }

    <T> Specification<E> valueEqual(Path<T> path, T value) {
        return (root, query, cb) -> value == null ? null : cb.equal(path, value);
    }
}
