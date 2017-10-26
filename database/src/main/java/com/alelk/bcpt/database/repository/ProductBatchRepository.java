package com.alelk.bcpt.database.repository;

import com.alelk.bcpt.database.model.ProductBatchEntity;
import com.alelk.bcpt.database.predicate.ProductBatchPredicateBuilder;
import com.alelk.bcpt.database.util.RepositoryUtil;
import com.alelk.bcpt.model.pagination.Filter;
import com.alelk.bcpt.model.pagination.SortBy;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Product Batch Repository
 *
 * Created by Alex Elkin on 11.09.2017.
 */
@Repository
public class ProductBatchRepository {

    @PersistenceContext
    private EntityManager em;
    private ProductBatchPredicateBuilder predicateBuilder;

    @Autowired
    public ProductBatchRepository(ProductBatchPredicateBuilder predicateBuilder) {
        this.predicateBuilder = predicateBuilder;
    }

    public ProductBatchEntity save(ProductBatchEntity entity) {
        return em.merge(entity);
    }

    public ProductBatchEntity findById(Long id) {
        return em.find(ProductBatchEntity.class, id);
    }

    public ProductBatchEntity findByExternalId(String externalId) {
        Session session = em.unwrap(Session.class);
        return session.byNaturalId(ProductBatchEntity.class).using("externalId", externalId).load();
    }

    public List<ProductBatchEntity> findAll() {
        return em.createNamedQuery(ProductBatchEntity.QUERY_FIND_ALL, ProductBatchEntity.class).getResultList();
    }

    public Long countItems() {
        return countItems(null);
    }

    public List<ProductBatchEntity> findAll(int pageNumber, int itemsPerPage, List<SortBy> sortBy, List<Filter> filters) {
        TypedQuery<ProductBatchEntity> query =  em.createQuery(
                RepositoryUtil.query(ProductBatchEntity.class, em.getCriteriaBuilder(), sortBy, filters, predicateBuilder).distinct(true)
        );
        query.setFirstResult((pageNumber-1) * itemsPerPage);
        query.setMaxResults(itemsPerPage);
        return query.getResultList();
    }

    public Long countItems(List<Filter> filters) {
        return em.createQuery(
                RepositoryUtil.countItemsQuery(ProductBatchEntity.class, em.getCriteriaBuilder(), filters, predicateBuilder)
        ).getSingleResult();
    }

    public void remove(ProductBatchEntity entity) {
        em.remove(entity);
    }
}
