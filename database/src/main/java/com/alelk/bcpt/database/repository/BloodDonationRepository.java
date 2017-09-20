package com.alelk.bcpt.database.repository;

import com.alelk.bcpt.database.model.BloodDonationEntity;
import com.alelk.bcpt.database.model.PersonEntity;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Blood Donation Repository
 *
 * Created by Alex Elkin on 11.09.2017.
 */
@Repository
public class BloodDonationRepository {

    @PersistenceContext
    private EntityManager em;

    public BloodDonationEntity save(BloodDonationEntity bloodDonationEntity) {
        return em.merge(bloodDonationEntity);
    }

    public BloodDonationEntity findById(Long id) {
        return em.find(BloodDonationEntity.class, id);
    }

    public BloodDonationEntity findByExternalId(String externalId) {
        Session session = em.unwrap(Session.class);
        return session.byNaturalId(BloodDonationEntity.class).using("externalId", externalId).load();
    }

    public List<BloodDonationEntity> findAll() {
        return em.createNamedQuery(BloodDonationEntity.QUERY_FIND_ALL, BloodDonationEntity.class).getResultList();
    }

    public List<BloodDonationEntity> findFor(PersonEntity personEntity) {
        return em.createNamedQuery(BloodDonationEntity.QUERY_FIND_BY_DONOR, BloodDonationEntity.class)
                .setParameter(BloodDonationEntity.PARAMETER_DONOR, personEntity)
                .getResultList();
    }

    public void remove(BloodDonationEntity entity) {
        em.remove(entity);
    }
}
