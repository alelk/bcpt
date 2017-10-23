package com.alelk.bcpt.database.repository;

import com.alelk.bcpt.database.model.PersonEntity;
import com.alelk.bcpt.database.predicate.PersonPredicateBuilder;
import com.alelk.bcpt.database.util.RepositoryUtil;
import com.alelk.bcpt.model.pagination.Filter;
import com.alelk.bcpt.model.pagination.SortBy;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

/**
 * Person Repository
 *
 * Created by Alex Elkin on 07.09.2017.
 */
@Repository
public class PersonRepository {

    @PersistenceContext
    private EntityManager em;
    private PersonPredicateBuilder predicateBuilder;

    @Autowired
    public PersonRepository(PersonPredicateBuilder predicateBuilder) {
        this.predicateBuilder = predicateBuilder;
    }

    public PersonEntity save(PersonEntity personEntity) {
        return em.merge(personEntity);
    }

    public PersonEntity findById(Long id) {
        return em.find(PersonEntity.class, id);
    }

    public List<PersonEntity> findAll() {
        return em.createQuery(queryPerson(em.getCriteriaBuilder())).getResultList();
    }

    public Long countItems() {
        return countItems(null);
    }

    public List<PersonEntity> findAll(int pageNumber, int itemsPerPage, List<SortBy> sortBy, List<Filter> filters) {
        TypedQuery<PersonEntity> query =  em.createQuery(
                RepositoryUtil.query(PersonEntity.class, em.getCriteriaBuilder(), sortBy, filters, predicateBuilder)
        );
        query.setFirstResult((pageNumber-1) * itemsPerPage);
        query.setMaxResults(itemsPerPage);
        return query.getResultList();
    }

    public Long countItems(List<Filter> filters) {
        return em.createQuery(
                RepositoryUtil.countItemsQuery(PersonEntity.class, em.getCriteriaBuilder(), filters, predicateBuilder)
        ).getSingleResult();
    }

    public PersonEntity findByExternalId(String externalId) {
        Session session = em.unwrap(Session.class);
        return session.byNaturalId(PersonEntity.class).using("externalId", externalId).load();
    }

    public void remove(PersonEntity pe) {
        em.remove(pe);
    }

    private CriteriaQuery<PersonEntity> queryPerson(CriteriaBuilder cb) {
        CriteriaQuery<PersonEntity> query = cb.createQuery(PersonEntity.class);
        return query.select(query.from(PersonEntity.class));
    }
}
