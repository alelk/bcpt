package com.alelk.bcpt.restapi.controller;

import com.alelk.bcpt.common.util.StringUtil;
import com.alelk.bcpt.database.service.BloodDonationService;
import com.alelk.bcpt.model.dto.BloodDonationDto;
import com.alelk.bcpt.model.pagination.Filter;
import com.alelk.bcpt.model.pagination.Page;
import com.alelk.bcpt.model.pagination.SortBy;
import com.alelk.bcpt.restapi.request.BloodDonationCreateRequest;
import com.alelk.bcpt.restapi.request.BloodDonationDeleteRequest;
import com.alelk.bcpt.restapi.request.BloodDonationUpdateRequest;
import com.alelk.bcpt.restapi.util.RestApiUtil;
import com.alelk.bcpt.restapi.validator.BloodDonationValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Blood Donation Controller
 *
 * Created by Alex Elkin on 20.09.2017.
 */
@RestController
@RequestMapping("/bloodDonations")
public class BloodDonationController {

    private static final Logger log = LoggerFactory.getLogger(BloodDonationController.class);
    private BloodDonationService bloodDonationService;
    private BloodDonationValidator bloodDonationValidator;

    @Autowired
    public BloodDonationController(BloodDonationService bloodDonationService, BloodDonationValidator bloodDonationValidator) {
        this.bloodDonationService = bloodDonationService;
        this.bloodDonationValidator = bloodDonationValidator;
    }

    @InitBinder
    public void setupBinder(WebDataBinder binder) {
        binder.addValidators(bloodDonationValidator);
    }

    @GetMapping("/")
    public List<BloodDonationDto> getAll() {
        return bloodDonationService.findAll();
    }

    @GetMapping("/page/{pageNumber}")
    public Page<BloodDonationDto> getAll(@PathVariable int pageNumber,
                                         @RequestParam(required = false) Integer itemsPerPage,
                                         @RequestParam(value = "sortBy", required = false) List<String> sortBy,
                                         @RequestParam(value = "filter", required = false) List<String> filter) {
        final List<SortBy> sortByList = RestApiUtil.parseSortParams(sortBy);
        final List<Filter> filterList = RestApiUtil.parseFilterParams(filter);
        log.debug("Request /bloodDonations/page/" + pageNumber + "?itemsPerPage=" + itemsPerPage + ": sortBy=" +
                StringUtil.toString(sortByList) + " filter=" + StringUtil.toString(filterList));
        return bloodDonationService.findAll(pageNumber, itemsPerPage == null ? 100 : itemsPerPage, sortByList, filterList);
    }

    @PostMapping("/")
    public ResponseEntity<BloodDonationDto> create(@Validated @RequestBody BloodDonationCreateRequest request) {
        return ResponseEntity.ok(bloodDonationService.create(request.toDto()));
    }

    @PutMapping("/{externalId}")
    public ResponseEntity<BloodDonationDto> update(@PathVariable String externalId, @Validated @RequestBody BloodDonationUpdateRequest request) {
        return ResponseEntity.ok(bloodDonationService.update(externalId, request.toDto(), true, false));
    }

    @GetMapping("/{externalId}")
    public BloodDonationDto get(@PathVariable String externalId) {
        return bloodDonationService.findByExternalId(externalId);
    }

    @DeleteMapping("/")
    public ResponseEntity<BloodDonationDto> delete(@Validated @RequestBody BloodDonationDeleteRequest request) {
        return ResponseEntity.ok(bloodDonationService.removeByExternalId(request.getExternalId()));
    }
}
