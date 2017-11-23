package com.alelk.bcpt.database.repository;

import com.alelk.bcpt.database.model.BloodPoolAnalysisEntity;
import com.alelk.bcpt.database.model.ProductBatchEntity;
import com.alelk.bcpt.database.predicate.BloodPoolAnalysisPredicateBuilder;
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
 * Blood Pool Analysis Repository
 *
 * Created by Alex Elkin on 23.11.2017.
 */
@Repository
public class BloodPoolAnalysisRepository {

    @PersistenceContext
    private EntityManager em;

    private BloodPoolAnalysisPredicateBuilder predicateBuilder;

    @Autowired
    public BloodPoolAnalysisRepository(BloodPoolAnalysisPredicateBuilder predicateBuilder) {
        this.predicateBuilder = predicateBuilder;
    }

    public BloodPoolAnalysisEntity save(BloodPoolAnalysisEntity entity) {
        return em.merge(entity);
    }

    public BloodPoolAnalysisEntity findById(Long id) {
        return em.find(BloodPoolAnalysisEntity.class, id);
    }

    public BloodPoolAnalysisEntity findByExternalId(String externalId) {
        Session session = em.unwrap(Session.class);
        return session.byNaturalId(BloodPoolAnalysisEntity.class).using("externalId", externalId).load();
    }

    public List<BloodPoolAnalysisEntity> findByProductBatch(ProductBatchEntity productBatchEntity) {
        return em.createNamedQuery(BloodPoolAnalysisEntity.QUERY_FIND_BY_PRODUCT_BATCH, BloodPoolAnalysisEntity.class)
                .setParameter(BloodPoolAnalysisEntity.PARAMETER_PRODUCT_BATCH, productBatchEntity)
                .getResultList();
    }

    public List<BloodPoolAnalysisEntity> findAll() {
        return em.createNamedQuery(BloodPoolAnalysisEntity.QUERY_FIND_ALL, BloodPoolAnalysisEntity.class).getResultList();
    }

    public Long countItems() {
        return countItems(null);
    }

    public List<BloodPoolAnalysisEntity> findAll(int pageNumber, int itemsPerPage, List<SortBy> sortBy, List<Filter> filters) {
        TypedQuery<BloodPoolAnalysisEntity> query =  em.createQuery(
                RepositoryUtil.query(BloodPoolAnalysisEntity.class, em.getCriteriaBuilder(), sortBy, filters, predicateBuilder).distinct(true)
        );
        query.setFirstResult((pageNumber-1) * itemsPerPage);
        query.setMaxResults(itemsPerPage);
        return query.getResultList();
    }

    public Long countItems(List<Filter> filters) {
        return em.createQuery(
                RepositoryUtil.countItemsQuery(BloodPoolAnalysisEntity.class, em.getCriteriaBuilder(), filters, predicateBuilder)
        ).getSingleResult();
    }

    public void remove(BloodPoolAnalysisEntity entity) {
        em.remove(entity);
    }
}
