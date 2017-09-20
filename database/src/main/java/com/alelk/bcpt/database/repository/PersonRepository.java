package com.alelk.bcpt.database.repository;

import com.alelk.bcpt.database.model.PersonEntity;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    public PersonEntity save(PersonEntity personEntity) {
        return em.merge(personEntity);
    }

    public PersonEntity findById(Long id) {
        return em.find(PersonEntity.class, id);
    }

    public List<PersonEntity> findAll() {
        return em.createNamedQuery(PersonEntity.QUERY_FIND_ALL, PersonEntity.class).getResultList();
    }

    public PersonEntity findByExternalId(String externalId) {
        Session session = em.unwrap(Session.class);
        return session.byNaturalId(PersonEntity.class).using("externalId", externalId).load();
    }

    public void remove(PersonEntity pe) {
        em.remove(pe);
    }
}
