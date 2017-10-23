package com.alelk.bcpt.database.repository;

import com.alelk.bcpt.database.model.BloodDonationEntity;
import com.alelk.bcpt.database.model.PersonEntity;
import com.alelk.bcpt.database.predicate.BloodDonationPredicateBuilder;
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
 * Blood Donation Repository
 *
 * Created by Alex Elkin on 11.09.2017.
 */
@Repository
public class BloodDonationRepository {

    @PersistenceContext
    private EntityManager em;
    private BloodDonationPredicateBuilder predicateBuilder;

    @Autowired
    public BloodDonationRepository(BloodDonationPredicateBuilder predicateBuilder) {
        this.predicateBuilder = predicateBuilder;
    }

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

    public Long countItems() {
        return countItems(null);
    }

    public List<BloodDonationEntity> findAll(int pageNumber, int itemsPerPage, List<SortBy> sortBy, List<Filter> filters) {
        TypedQuery<BloodDonationEntity> query =  em.createQuery(
                RepositoryUtil.query(BloodDonationEntity.class, em.getCriteriaBuilder(), sortBy, filters, predicateBuilder).distinct(true)
        );
        query.setFirstResult((pageNumber-1) * itemsPerPage);
        query.setMaxResults(itemsPerPage);
        return query.getResultList();
    }

    public Long countItems(List<Filter> filters) {
        return em.createQuery(
                RepositoryUtil.countItemsQuery(BloodDonationEntity.class, em.getCriteriaBuilder(), filters, predicateBuilder)
        ).getSingleResult();
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
