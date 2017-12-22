package com.alelk.bcpt.restapi.controller;

import com.alelk.bcpt.common.util.StringUtil;
import com.alelk.bcpt.database.service.BloodPoolAnalysisService;
import com.alelk.bcpt.database.service.BloodPoolService;
import com.alelk.bcpt.model.dto.BloodPoolAnalysisDto;
import com.alelk.bcpt.model.dto.BloodPoolDto;
import com.alelk.bcpt.model.pagination.Filter;
import com.alelk.bcpt.model.pagination.Page;
import com.alelk.bcpt.model.pagination.SortBy;
import com.alelk.bcpt.restapi.request.BloodPoolCreateRequest;
import com.alelk.bcpt.restapi.request.BloodPoolDeleteRequest;
import com.alelk.bcpt.restapi.request.BloodPoolUpdateRequest;
import com.alelk.bcpt.restapi.util.RestApiUtil;
import com.alelk.bcpt.restapi.validator.BloodPoolValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Blood Pool Controller
 *
 * Created by Alex Elkin on 02.10.2017.
 */
@RestController
@RequestMapping("/bloodPools")
public class BloodPoolController {

    private final static Logger log = LoggerFactory.getLogger(BloodPoolController.class);
    private BloodPoolService bloodPoolService;
    private BloodPoolAnalysisService bloodPoolAnalysisService;
    private BloodPoolValidator bloodPoolValidator;

    @Autowired
    public BloodPoolController(BloodPoolService bloodPoolService, BloodPoolAnalysisService bloodPoolAnalysisService, BloodPoolValidator bloodPoolValidator) {
        this.bloodPoolService = bloodPoolService;
        this.bloodPoolAnalysisService = bloodPoolAnalysisService;
        this.bloodPoolValidator = bloodPoolValidator;
    }

    @InitBinder
    public void setupBinder(WebDataBinder binder) {
        binder.addValidators(bloodPoolValidator);
    }

    @GetMapping("/")
    public List<BloodPoolDto> getAll() {
        return bloodPoolService.findAll();
    }

    @GetMapping("/page/{pageNumber}")
    public Page<BloodPoolDto> getAll(@PathVariable int pageNumber,
                                        @RequestParam(required = false) Integer itemsPerPage,
                                        @RequestParam(value = "sortBy", required = false) List<String> sortBy,
                                        @RequestParam(value = "filter", required = false) List<String> filter) {
        final List<SortBy> sortByList = RestApiUtil.parseSortParams(sortBy);
        final List<Filter> filterList = RestApiUtil.parseFilterParams(filter);
        log.debug("Request /bloodPools/page/" + pageNumber + "?itemsPerPage=" + itemsPerPage + ": sortBy=" +
                StringUtil.toString(sortByList) + " filter=" + StringUtil.toString(filterList));
        return bloodPoolService.findAll(pageNumber, itemsPerPage == null ? 100 : itemsPerPage, sortByList, filterList);
    }

    @PostMapping("/")
    public ResponseEntity<BloodPoolDto> create(@Validated @RequestBody BloodPoolCreateRequest request) {
        final BloodPoolDto dto = bloodPoolService.create(request.toDto());
        bloodPoolAnalysisService.create(new BloodPoolAnalysisDto(dto.getExternalId(), null, null, dto.getExternalId(), null, null, null, null, null));
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{externalId}")
    public ResponseEntity<BloodPoolDto> update(@PathVariable String externalId, @Validated @RequestBody BloodPoolUpdateRequest request) {
        return ResponseEntity.ok(bloodPoolService.update(externalId, request.toDto(), true, false));
    }

    @GetMapping("/{externalId}")
    public ResponseEntity<BloodPoolDto> get(@PathVariable String externalId) {
        final BloodPoolDto dto = bloodPoolService.findByExternalId(externalId);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/")
    public ResponseEntity<BloodPoolDto> delete(@Validated @RequestBody BloodPoolDeleteRequest request) {
        bloodPoolAnalysisService.removeByExternalId(request.getExternalId());
        final BloodPoolDto dto = bloodPoolService.removeByExternalId(request.getExternalId());
        return ResponseEntity.ok(dto);
    }
}
