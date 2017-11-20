package com.alelk.bcpt.database.repository;

import com.alelk.bcpt.database.model.BloodPoolEntity;
import com.alelk.bcpt.database.model.ProductBatchEntity;
import com.alelk.bcpt.database.predicate.BloodPoolPredicateBuilder;
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
 * Blood Donation Pool Repository
 *
 * Created by Alex Elkin on 11.09.2017.
 */
@Repository
public class BloodPoolRepository {

    @PersistenceContext
    private EntityManager em;

    private BloodPoolPredicateBuilder predicateBuilder;

    @Autowired
    public BloodPoolRepository(BloodPoolPredicateBuilder predicateBuilder) {
        this.predicateBuilder = predicateBuilder;
    }

    public BloodPoolEntity save(BloodPoolEntity entity) {
        return em.merge(entity);
    }

    public BloodPoolEntity findById(Long id) {
        return em.find(BloodPoolEntity.class, id);
    }

    public BloodPoolEntity findByExternalId(String externalId) {
        Session session = em.unwrap(Session.class);
        return session.byNaturalId(BloodPoolEntity.class).using("externalId", externalId).load();
    }

    public List<BloodPoolEntity> findByProductBatch(ProductBatchEntity productBatchEntity) {
        return em.createNamedQuery(BloodPoolEntity.QUERY_FIND_BY_PRODUCT_BATCH, BloodPoolEntity.class)
                .setParameter(BloodPoolEntity.PARAMETER_PRODUCT_BATCH, productBatchEntity)
                .getResultList();
    }

    public List<BloodPoolEntity> findAll() {
        return em.createNamedQuery(BloodPoolEntity.QUERY_FIND_ALL, BloodPoolEntity.class).getResultList();
    }

    public Long countItems() {
        return countItems(null);
    }

    public List<BloodPoolEntity> findAll(int pageNumber, int itemsPerPage, List<SortBy> sortBy, List<Filter> filters) {
        TypedQuery<BloodPoolEntity> query =  em.createQuery(
                RepositoryUtil.query(BloodPoolEntity.class, em.getCriteriaBuilder(), sortBy, filters, predicateBuilder).distinct(true)
        );
        query.setFirstResult((pageNumber-1) * itemsPerPage);
        query.setMaxResults(itemsPerPage);
        return query.getResultList();
    }

    public Long countItems(List<Filter> filters) {
        return em.createQuery(
                RepositoryUtil.countItemsQuery(BloodPoolEntity.class, em.getCriteriaBuilder(), filters, predicateBuilder)
        ).getSingleResult();
    }

    public void remove(BloodPoolEntity entity) {
        em.remove(entity);
    }
}
