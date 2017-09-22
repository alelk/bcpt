package com.alelk.bcpt.database.repository;

import com.alelk.bcpt.database.model.BloodPoolEntity;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    public List<BloodPoolEntity> findAll() {
        return em.createNamedQuery(BloodPoolEntity.QUERY_FIND_ALL, BloodPoolEntity.class).getResultList();
    }

    public void remove(BloodPoolEntity entity) {
        em.remove(entity);
    }
}
