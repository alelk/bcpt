package com.alelk.bcpt.database.repository;

import com.alelk.bcpt.database.model.BloodInvoiceEntity;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    public void remove(BloodInvoiceEntity entity) {
        em.remove(entity);
    }
}
