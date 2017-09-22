package com.alelk.bcpt.database.repository;

import com.alelk.bcpt.database.model.ProductBatchEntity;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    public void remove(ProductBatchEntity entity) {
        em.remove(entity);
    }
}
