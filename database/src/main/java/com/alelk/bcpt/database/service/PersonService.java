package com.alelk.bcpt.database.service;

import com.alelk.bcpt.database.builder.PersonEntityBuilder;
import com.alelk.bcpt.database.model.PersonEntity;
import com.alelk.bcpt.database.repository.PersonRepository;
import com.alelk.bcpt.database.util.DatabaseUtil;
import com.alelk.bcpt.model.dto.PersonDto;
import com.alelk.bcpt.model.pagination.Filter;
import com.alelk.bcpt.model.pagination.Page;
import com.alelk.bcpt.model.pagination.SortBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.alelk.bcpt.database.util.ValidationUtil.validateNotEmpty;
import static com.alelk.bcpt.database.util.ValidationUtil.validateNotNull;

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
        return DatabaseUtil.mapPersonEntityToDto(
                personRepository.save(new PersonEntityBuilder().apply(person).build())
        );
    }

    @Transactional
    public PersonDto update(String externalId, PersonDto person, boolean mergeWithNullValues, boolean softUpdate) {
        final String message = "Error updating person " + person + ": ";
        validateNotNull(person, message + "Person DTO object must be not null.");
        PersonEntity pe = findEntityByExternalId(externalId, message);
        validateNotNull(pe, message + "Person external id does'nt exist.");
        return DatabaseUtil.mapPersonEntityToDto(
                new PersonEntityBuilder(pe, mergeWithNullValues, softUpdate).apply(person).build()
        );
    }

    @Transactional(readOnly = true)
    public List<PersonDto> findAll() {
        return personRepository.findAll().stream()
                .map(DatabaseUtil::mapPersonEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<PersonDto> findAll(int pageNumber, int itemsPerPage, List<SortBy> sortByList, List<Filter> filterList) {
        return new Page<>(
                pageNumber,
                itemsPerPage,
                personRepository.findAll(pageNumber, itemsPerPage, sortByList, filterList)
                        .stream().map(DatabaseUtil::mapPersonEntityToDto).collect(Collectors.toList()),
                personRepository.countItems(filterList),
                sortByList,
                filterList);
    }

    @Transactional(readOnly = true)
    public PersonDto findByExternalId(String externalId) {
        return DatabaseUtil.mapPersonEntityToDto(findEntityByExternalId(externalId, ""));
    }

    @Transactional
    public PersonDto removeByExternalId(String externalId) {
        final String message = "Cannot remove person by external id: ";
        final PersonEntity pe = findEntityByExternalId(externalId, message);
        validateNotNull(pe, message + "no entity found for external id '" + externalId + '\'');
        personRepository.remove(pe);
        return DatabaseUtil.mapPersonEntityToDto(pe);
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
