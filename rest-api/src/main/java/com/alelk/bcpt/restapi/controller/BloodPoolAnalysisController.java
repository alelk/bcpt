package com.alelk.bcpt.restapi.controller;

import com.alelk.bcpt.common.util.StringUtil;
import com.alelk.bcpt.database.service.BloodPoolAnalysisService;
import com.alelk.bcpt.model.dto.BloodPoolAnalysisDto;
import com.alelk.bcpt.model.pagination.Filter;
import com.alelk.bcpt.model.pagination.Page;
import com.alelk.bcpt.model.pagination.SortBy;
import com.alelk.bcpt.restapi.request.BloodPoolAnalysisCreateRequest;
import com.alelk.bcpt.restapi.request.BloodPoolAnalysisDeleteRequest;
import com.alelk.bcpt.restapi.request.BloodPoolAnalysisUpdateRequest;
import com.alelk.bcpt.restapi.util.RestApiUtil;
import com.alelk.bcpt.restapi.validator.BloodPoolAnalysisValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Blood Pool Analysis Controller
 *
 * Created by Alex Elkin on 23.11.2017.
 */
@RestController
@RequestMapping("/bloodPoolAnalysis")
public class BloodPoolAnalysisController {

    private final static Logger log = LoggerFactory.getLogger(BloodPoolAnalysisController.class);
    private BloodPoolAnalysisService bloodPoolAnalysisService;
    private BloodPoolAnalysisValidator validator;

    public BloodPoolAnalysisController(BloodPoolAnalysisService bloodPoolAnalysisService, BloodPoolAnalysisValidator validator) {
        this.bloodPoolAnalysisService = bloodPoolAnalysisService;
        this.validator = validator;
    }

    @InitBinder
    public void setupBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @GetMapping("/")
    public List<BloodPoolAnalysisDto> getAll() {
        return bloodPoolAnalysisService.findAll();
    }

    @GetMapping("/page/{pageNumber}")
    public Page<BloodPoolAnalysisDto> getAll(@PathVariable int pageNumber,
                                     @RequestParam(required = false) Integer itemsPerPage,
                                     @RequestParam(value = "sortBy", required = false) List<String> sortBy,
                                     @RequestParam(value = "filter", required = false) List<String> filter) {
        final List<SortBy> sortByList = RestApiUtil.parseSortParams(sortBy);
        final List<Filter> filterList = RestApiUtil.parseFilterParams(filter);
        log.debug("Request /bloodPoolAnalysis/page/" + pageNumber + "?itemsPerPage=" + itemsPerPage + ": sortBy=" +
                StringUtil.toString(sortByList) + " filter=" + StringUtil.toString(filterList));
        return bloodPoolAnalysisService.findAll(pageNumber, itemsPerPage == null ? 100 : itemsPerPage, sortByList, filterList);
    }

    @PostMapping("/")
    public ResponseEntity<BloodPoolAnalysisDto> create(@Validated @RequestBody BloodPoolAnalysisCreateRequest request) {
        return ResponseEntity.ok(bloodPoolAnalysisService.create(request.toDto()));
    }

    @PutMapping("/{externalId}")
    public ResponseEntity<BloodPoolAnalysisDto> update(@PathVariable String externalId, @Validated @RequestBody BloodPoolAnalysisUpdateRequest request) {
        return ResponseEntity.ok(bloodPoolAnalysisService.update(externalId, request.toDto(), true, false));
    }

    @GetMapping("/{externalId}")
    public BloodPoolAnalysisDto get(@PathVariable String externalId) {
        return bloodPoolAnalysisService.findByExternalId(externalId);
    }

    @DeleteMapping("/")
    public ResponseEntity<BloodPoolAnalysisDto> delete(@Validated @RequestBody BloodPoolAnalysisDeleteRequest request) {
        return ResponseEntity.ok(bloodPoolAnalysisService.removeByExternalId(request.getExternalId()));
    }
}
