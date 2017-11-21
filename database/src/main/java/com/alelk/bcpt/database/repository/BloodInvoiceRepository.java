package com.alelk.bcpt.database.repository;

import com.alelk.bcpt.database.model.BloodInvoiceEntity;
import com.alelk.bcpt.database.model.ProductBatchEntity;
import com.alelk.bcpt.database.predicate.BloodInvoicePredicateBuilder;
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
 * Blood Donation Delivery Repository
 *
 * Created by Alex Elkin on 11.09.2017.
 */
@Repository
public class BloodInvoiceRepository {

    @PersistenceContext
    private EntityManager em;
    private BloodInvoicePredicateBuilder predicateBuilder;

    @Autowired
    public BloodInvoiceRepository(BloodInvoicePredicateBuilder predicateBuilder) {
        this.predicateBuilder = predicateBuilder;
    }

    public BloodInvoiceEntity save(BloodInvoiceEntity entity) {
        return em.merge(entity);
    }

    public BloodInvoiceEntity findById(Long id) {
        return em.find(BloodInvoiceEntity.class, id);
    }

    public BloodInvoiceEntity findByExternalId(String externalId) {
        Session session = em.unwrap(Session.class);
        return session.byNaturalId(BloodInvoiceEntity.class).using("externalId", externalId).load();
    }

    public List<BloodInvoiceEntity> findAll() {
        return em.createNamedQuery(BloodInvoiceEntity.QUERY_FIND_ALL, BloodInvoiceEntity.class).getResultList();
    }

    public List<BloodInvoiceEntity> findByProductBatch(ProductBatchEntity productBatchEntity) {
        return em.createNamedQuery(BloodInvoiceEntity.QUERY_FIND_BY_PRODUCT_BATCH, BloodInvoiceEntity.class)
                .setParameter(BloodInvoiceEntity.PARAMETER_PRODUCT_BATCH, productBatchEntity)
                .getResultList();
    }

    public Long countItems() {
        return countItems(null);
    }

    public List<BloodInvoiceEntity> findAll(int pageNumber, int itemsPerPage, List<SortBy> sortBy, List<Filter> filters) {
        TypedQuery<BloodInvoiceEntity> query =  em.createQuery(
                RepositoryUtil.query(BloodInvoiceEntity.class, em.getCriteriaBuilder(), sortBy, filters, predicateBuilder).distinct(true)
        );
        query.setFirstResult((pageNumber-1) * itemsPerPage);
        query.setMaxResults(itemsPerPage);
        return query.getResultList();
    }

    public Long countItems(List<Filter> filters) {
        return em.createQuery(
                RepositoryUtil.countItemsQuery(BloodInvoiceEntity.class, em.getCriteriaBuilder(), filters, predicateBuilder)
        ).getSingleResult();
    }

    public void remove(BloodInvoiceEntity entity) {
        em.remove(entity);
    }
}
