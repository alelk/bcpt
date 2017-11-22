package com.alelk.bcpt.database.repository;

import com.alelk.bcpt.database.model.BloodInvoiceSeriesEntity;
import com.alelk.bcpt.database.model.ProductBatchEntity;
import com.alelk.bcpt.database.predicate.BloodInvoiceSeriesPredicateBuilder;
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
 * Blood Invoice Series Repository
 *
 * Created by Alex Elkin on 22.11.2017.
 */
@Repository
public class BloodInvoiceSeriesRepository {

    @PersistenceContext
    private EntityManager em;

    private BloodInvoiceSeriesPredicateBuilder predicateBuilder;

    @Autowired
    public BloodInvoiceSeriesRepository(BloodInvoiceSeriesPredicateBuilder predicateBuilder) {
        this.predicateBuilder = predicateBuilder;
    }

    public BloodInvoiceSeriesEntity save(BloodInvoiceSeriesEntity entity) {
        return em.merge(entity);
    }

    public BloodInvoiceSeriesEntity findById(Long id) {
        return em.find(BloodInvoiceSeriesEntity.class, id);
    }

    public BloodInvoiceSeriesEntity findByExternalId(String externalId) {
        Session session = em.unwrap(Session.class);
        return session.byNaturalId(BloodInvoiceSeriesEntity.class).using("externalId", externalId).load();
    }

    public List<BloodInvoiceSeriesEntity> findAll() {
        return em.createNamedQuery(BloodInvoiceSeriesEntity.QUERY_FIND_ALL, BloodInvoiceSeriesEntity.class).getResultList();
    }

    public List<BloodInvoiceSeriesEntity> findByProductBatch(ProductBatchEntity productBatchEntity) {
        return em.createNamedQuery(BloodInvoiceSeriesEntity.QUERY_FIND_BY_PRODUCT_BATCH, BloodInvoiceSeriesEntity.class)
                .setParameter(BloodInvoiceSeriesEntity.PARAMETER_PRODUCT_BATCH, productBatchEntity)
                .getResultList();
    }

    public Long countItems() {
        return countItems(null);
    }

    public List<BloodInvoiceSeriesEntity> findAll(int pageNumber, int itemsPerPage, List<SortBy> sortBy, List<Filter> filters) {
        TypedQuery<BloodInvoiceSeriesEntity> query =  em.createQuery(
                RepositoryUtil.query(BloodInvoiceSeriesEntity.class, em.getCriteriaBuilder(), sortBy, filters, predicateBuilder).distinct(true)
        );
        query.setFirstResult((pageNumber-1) * itemsPerPage);
        query.setMaxResults(itemsPerPage);
        return query.getResultList();
    }

    public Long countItems(List<Filter> filters) {
        return em.createQuery(
                RepositoryUtil.countItemsQuery(BloodInvoiceSeriesEntity.class, em.getCriteriaBuilder(), filters, predicateBuilder)
        ).getSingleResult();
    }

    public void remove(BloodInvoiceSeriesEntity entity) {
        em.remove(entity);
    }
}
