package com.alelk.bcpt.database.service;

import com.alelk.bcpt.database.builder.PersonDtoBuilder;
import com.alelk.bcpt.database.builder.PersonEntityBuilder;
import com.alelk.bcpt.database.model.PersonEntity;
import com.alelk.bcpt.database.repository.PersonRepository;
import com.alelk.bcpt.model.dto.PersonDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.alelk.bcpt.database.util.ValidationUtil.*;

/**
 * Person Service
 *
 * Created by Alex Elkin on 11.09.2017.
 */
@Service
public class PersonService {

    private PersonRepository personRepository;

    @Autowired
    PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional
    public PersonDto create(PersonDto person) {
        final String message = "Error inserting person " + person + " to the database: ";
        validateNotNull(person, message + "Person DTO object must be not null.");
        validateNotEmpty(person.getExternalId(), message + "Person's external id must be not empty.");
        return new PersonDtoBuilder().apply(personRepository.save(new PersonEntityBuilder().apply(person).build())).build();
    }

    @Transactional
    public PersonDto update(String externalId, PersonDto person, boolean mergeWithNullValues, boolean softUpdate) {
        final String message = "Error updating person " + person + ": ";
        validateNotNull(person, message + "Person DTO object must be not null.");
        PersonEntity pe = findEntityByExternalId(externalId, message);
        validateNotNull(pe, message + "Person external id does'nt exist.");
        return new PersonDtoBuilder().apply(
                new PersonEntityBuilder(pe, mergeWithNullValues, softUpdate).apply(person).build()
        ).build();
    }

    @Transactional(readOnly = true)
    public List<PersonDto> findAll() {
        return personRepository.findAll().stream()
                .map(personEntity -> new PersonDtoBuilder().apply(personEntity).build())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PersonDto findByExternalId(String externalId) {
        return new PersonDtoBuilder().apply(findEntityByExternalId(externalId, "")).build();
    }

    @Transactional
    public PersonDto removeByExternalId(String externalId) {
        final String message = "Cannot remove person by external id: ";
        final PersonEntity pe = findEntityByExternalId(externalId, message);
        validateNotNull(pe, message + "no entity found for external id '" + externalId + '\'');
        personRepository.remove(pe);
        return new PersonDtoBuilder().apply(pe).build();
    }

    @Transactional
    public boolean isIdExists(String externalId) {
        return !StringUtils.isEmpty(externalId) && findEntityByExternalId(externalId, "Error checking if the person exists: ") != null;
    }


    private PersonEntity findEntityByExternalId(String externalId, String message) {
        validateNotEmpty(externalId, message + "Cannot find person by external id: no id provided.");
        return personRepository.findByExternalId(externalId);
    }
}
