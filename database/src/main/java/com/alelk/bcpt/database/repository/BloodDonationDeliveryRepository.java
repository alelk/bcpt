package com.alelk.bcpt.database.repository;

import com.alelk.bcpt.database.model.BloodDonationDeliveryEntity;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Blood Donation Delivery Repository
 *
 * Created by Alex Elkin on 11.09.2017.
 */
@Repository
public class BloodDonationDeliveryRepository {
    @PersistenceContext
    private EntityManager em;

    public BloodDonationDeliveryEntity save(BloodDonationDeliveryEntity entity) {
        return em.merge(entity);
    }

    public BloodDonationDeliveryEntity findById(Long id) {
        return em.find(BloodDonationDeliveryEntity.class, id);
    }

    public BloodDonationDeliveryEntity findByExternalId(String externalId) {
        Session session = em.unwrap(Session.class);
        return session.byNaturalId(BloodDonationDeliveryEntity.class).using("externalId", externalId).load();
    }
}
