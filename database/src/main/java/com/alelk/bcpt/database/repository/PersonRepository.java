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
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(PersonEntity.class)));
        return em.createQuery(countQuery).getSingleResult();
    }

    public List<PersonEntity> findAll(int pageNumber, int itemsPerPage, List<SortBy> sortBy, List<Filter> filters) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        TypedQuery<PersonEntity> query =  em.createQuery(createQuery(cb, sortBy, filters));
        query.setFirstResult((pageNumber-1) * itemsPerPage);
        query.setMaxResults(itemsPerPage);
        return query.getResultList();
    }

    public Long countItems(List<Filter> filters) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<PersonEntity> root = countQuery.from(PersonEntity.class);
        countQuery.select(cb.count(root));
        Predicate predicate = predicateBuilder.buildPredicate(root, countQuery, cb, filters);
        if (predicate != null) countQuery.where(predicate);
        return em.createQuery(countQuery).getSingleResult();
    }

    private CriteriaQuery<PersonEntity> createQuery(CriteriaBuilder cb, List<SortBy> sortBy, List<Filter> filters) {
        CriteriaQuery<PersonEntity> criteriaQuery = cb.createQuery(PersonEntity.class);
        Root<PersonEntity> root = criteriaQuery.from(PersonEntity.class);
        CriteriaQuery<PersonEntity> select = criteriaQuery.select(root);
        RepositoryUtil.sort(PersonEntity.class, criteriaQuery, root, cb, sortBy);
        Predicate predicate = predicateBuilder.buildPredicate(root, criteriaQuery, cb, filters);
        if (predicate != null) select.where(predicate);
        return select;
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
