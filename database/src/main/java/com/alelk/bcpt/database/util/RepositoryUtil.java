package com.alelk.bcpt.database.util;

import com.alelk.bcpt.database.model.AbstractEntity;
import com.alelk.bcpt.database.model.AbstractEntity_;
import com.alelk.bcpt.model.pagination.SortBy;
import com.alelk.bcpt.model.pagination.SortDirection;
import com.alelk.bcpt.model.util.Util;
import org.hibernate.hql.internal.ast.util.PathHelper;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.TypeElementsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Repository Util
 *
 * Created by Alex Elkin on 12.10.2017.
 */
public class RepositoryUtil {

    private static final Logger log = LoggerFactory.getLogger(RepositoryUtil.class);

    private static Reflections reflections = new Reflections(
            new ConfigurationBuilder().setUrls(ClasspathHelper.forPackage(AbstractEntity.class.getPackage().getName()))
                    .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(AbstractEntity.class.getPackage().getName())))
    );

    /**
     * Modifies query with sort criteria
     *
     * @param clazz class on entity
     * @param query original query
     * @param root JPA root object
     * @param cb criteria builder
     * @param sortBy a list of {@link SortBy} elements which contains sorting rules (fields and appropriate directions)
     * @param <E> entity type
     * @return modified query
     */
    public static  <E extends AbstractEntity> CriteriaQuery<E> sort(
            Class<E> clazz,
            CriteriaQuery<E> query,
            Root<E> root,
            CriteriaBuilder cb,
            List<SortBy> sortBy
    ) {
        if (sortBy == null || sortBy.size() == 0) return query;
        List<String> allowedFieldNames = getSortableFields(clazz).stream().map(Field::getName).collect(Collectors.toList());
        List<Order> orders = sortBy.stream().filter(order -> allowedFieldNames.contains(order.getFieldName()))
                .map(order ->
                        SortDirection.DESC.equals(order.getDirection()) ? cb.desc(root.get(order.getFieldName()))
                                : cb.asc(root.get(order.getFieldName()))
                ).collect(Collectors.toList());
        return query.orderBy(orders);
    }

    @SuppressWarnings("unchecked")
    public static <E extends AbstractEntity> Set<Field> getSortableMetamodelFields(Class<E> clazz) {
        Class<? extends AbstractEntity_> metaModel = getMetaModel(clazz);
        List<String> sortableFieldNames = getSortableFields(clazz).stream()
                .map(Field::getName).collect(Collectors.toList());
        return ReflectionUtils.getAllFields(metaModel, Objects::nonNull).stream()
                .filter(field -> sortableFieldNames.contains(field.getName())).collect(Collectors.toSet());
    }

    @SuppressWarnings("unchecked")
    public static <E extends AbstractEntity> Set<Field> getSortableFields(Class<E> clazz) {
        return ReflectionUtils.getAllFields(clazz, field -> field != null && field.isAnnotationPresent(Sortable.class));
    }

    @SuppressWarnings("unchecked")
    public static <E extends AbstractEntity> Class<? extends AbstractEntity_> getMetaModel(Class<E> clazz) {
        return (Class<? extends AbstractEntity_>) reflections.getTypesAnnotatedWith(StaticMetamodel.class).stream()
                .filter(annotatedClass -> clazz.equals(annotatedClass.getAnnotation(StaticMetamodel.class).value()))
                .findFirst().orElse(null);
    }
}
