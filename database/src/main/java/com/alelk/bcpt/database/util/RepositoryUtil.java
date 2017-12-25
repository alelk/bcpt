package com.alelk.bcpt.database.util;

import com.alelk.bcpt.database.model.AbstractEntity;
import com.alelk.bcpt.database.model.AbstractEntity_;
import com.alelk.bcpt.database.predicate.AbstractPredicateBuilder;
import com.alelk.bcpt.database.specifications.AbstractSpecifications;
import com.alelk.bcpt.model.AnalysisConclusion;
import com.alelk.bcpt.model.pagination.Filter;
import com.alelk.bcpt.model.pagination.SortBy;
import com.alelk.bcpt.model.pagination.SortDirection;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.StaticMetamodel;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Repository Util
 *
 * Created by Alex Elkin on 12.10.2017.
 */
public class RepositoryUtil {

    private static Reflections reflections = new Reflections(
            new ConfigurationBuilder().setUrls(ClasspathHelper.forPackage(AbstractEntity.class.getPackage().getName()))
                    .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(AbstractEntity.class.getPackage().getName())))
    );

    /**
     * Modifies query with sort criteria
     *
     * @param clazz class of entity
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

    /**
     * Build query with sort and filter criteria
     *
     * @param clazz the class of the result entity
     * @param cb criteria builder
     * @param sort sort criteria
     * @param filters filter criteria
     * @param predicateBuilder predicate builder
     * @param <E> the type of entity
     * @param <M> the type of entity JPA meta model
     * @return criteria query
     */
    public static <E extends AbstractEntity, M extends AbstractEntity_> CriteriaQuery<E> query(
            Class<E> clazz,
            CriteriaBuilder cb,
            List<SortBy> sort,
            List<Filter> filters,
            AbstractPredicateBuilder<E, M, ? extends AbstractSpecifications<E, M>> predicateBuilder
    ) {
        final CriteriaQuery<E> criteriaQuery = cb.createQuery(clazz);
        final Root<E> root = criteriaQuery.from(clazz);
        final Predicate predicate = predicateBuilder.buildPredicate(root, criteriaQuery, cb, filters);
        final CriteriaQuery<E> select = criteriaQuery.select(root);
        if (predicate != null) select.where(predicate);
        sort(clazz, criteriaQuery, root, cb, sort);
        return select;
    }

    /**
     * Query count items after filter criteria applying
     *
     * @param clazz the class of the target entity
     * @param cb criteria builder
     * @param filters filters
     * @param predicateBuilder predicate builder for the target entity
     * @param <E> the type of the target entity
     * @param <M> the type of entity JPA meta model
     * @return count query
     */
    public static <E extends AbstractEntity, M extends AbstractEntity_> CriteriaQuery<Long> countItemsQuery
            (Class<E> clazz,
             CriteriaBuilder cb,
             List<Filter> filters,
             AbstractPredicateBuilder<E, M, ? extends AbstractSpecifications<E, M>> predicateBuilder
            ) {
        final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        final Root<E> root = countQuery.from(clazz);
        countQuery.select(cb.count(root));
        final Predicate predicate = predicateBuilder.buildPredicate(root, countQuery, cb, filters);
        if (predicate != null) countQuery.where(predicate);
        return countQuery;
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

    public static AnalysisConclusion processAnalysisConclusion(Stream<AnalysisConclusion> stream) {
        return stream.reduce(AnalysisConclusion.PASS, (accumulator, analysis) -> {
            if (analysis == null) return AnalysisConclusion.PASS.equals(accumulator) ? null : accumulator;
            return analysis.morePriorityThan(accumulator) ?
                    AnalysisConclusion.PASS.equals(analysis) ? null : analysis : accumulator;
        });
    }
}
