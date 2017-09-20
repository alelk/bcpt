package com.alelk.bcpt.database.repository;

import com.alelk.bcpt.database.model.BloodDonationPoolEntity;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Blood Donation Pool Repository
 *
 * Created by Alex Elkin on 11.09.2017.
 */
@Repository
public class BloodDonationPoolRepository {

    @PersistenceContext
    private EntityManager em;

    public BloodDonationPoolEntity save(BloodDonationPoolEntity entity) {
        return em.merge(entity);
    }

    public BloodDonationPoolEntity findById(Long id) {
        return em.find(BloodDonationPoolEntity.class, id);
    }

    public BloodDonationPoolEntity findByExternalId(String externalId) {
        Session session = em.unwrap(Session.class);
        return session.byNaturalId(BloodDonationPoolEntity.class).using("externalId", externalId).load();
    }
}
