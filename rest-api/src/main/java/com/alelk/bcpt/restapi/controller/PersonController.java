package com.alelk.bcpt.restapi.controller;

import com.alelk.bcpt.common.util.StringUtil;
import com.alelk.bcpt.database.service.PersonService;
import com.alelk.bcpt.model.dto.PersonDto;
import com.alelk.bcpt.model.pagination.Filter;
import com.alelk.bcpt.model.pagination.Page;
import com.alelk.bcpt.model.pagination.SortBy;
import com.alelk.bcpt.restapi.request.PersonCreateRequest;
import com.alelk.bcpt.restapi.request.PersonDeleteRequest;
import com.alelk.bcpt.restapi.request.PersonUpdateRequest;
import com.alelk.bcpt.restapi.util.RestApiUtil;
import com.alelk.bcpt.restapi.validator.PersonValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Person Controller
 *
 * Created by Alex Elkin on 11.09.2017.
 */
@RestController
@RequestMapping("/persons")
public class PersonController {

    private static final Logger log = LoggerFactory.getLogger(PersonController.class);
    private PersonService personService;
    private PersonValidator personValidator;

    @Autowired
    PersonController(PersonService personService, PersonValidator personCreateRequestValidator) {
        this.personService = personService;
        this.personValidator = personCreateRequestValidator;
    }

    @InitBinder
    public void setupBinder(WebDataBinder binder) {
        binder.addValidators(personValidator);
    }

    @GetMapping("/")
    public List<PersonDto> getAll() {
        return personService.findAll();
    }

    @GetMapping("/page/{pageNumber}")
    public Page<PersonDto> getAll(@PathVariable int pageNumber,
                                  @RequestParam(required = false) Integer itemsPerPage,
                                  @RequestParam(value = "sortBy", required = false) List<String> sortBy,
                                  @RequestParam(value = "filter", required = false) List<String> filter) {
        final List<SortBy> sortByList = RestApiUtil.parseSortParams(sortBy);
        final List<Filter> filterList = RestApiUtil.parseFilterParams(filter);
        log.debug("Request /persons/page/" + pageNumber + "?itemsPerPage=" + itemsPerPage + ": sortBy=" +
                StringUtil.toString(sortByList) + " filter=" + StringUtil.toString(filterList));
        return personService.findAll(pageNumber, itemsPerPage == null ? 100 : itemsPerPage, sortByList, filterList);
    }

    @PostMapping("/")
    public ResponseEntity<PersonDto> create(@Validated @RequestBody PersonCreateRequest person) {
        return ResponseEntity.ok(personService.create(person.toDto()));
    }

    @PutMapping("/{externalId}")
    public ResponseEntity<PersonDto> update(@PathVariable String externalId, @Validated @RequestBody PersonUpdateRequest person) {
        return ResponseEntity.ok(personService.update(externalId, person.toDto(), true, false));
    }

    @GetMapping("/{externalId}")
    public ResponseEntity<PersonDto> get(@PathVariable String externalId) {
        final PersonDto dto = personService.findByExternalId(externalId);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/")
    public ResponseEntity<PersonDto> delete(@Validated @RequestBody PersonDeleteRequest person) {
        return ResponseEntity.ok(personService.removeByExternalId(person.getExternalId()));
    }
}
