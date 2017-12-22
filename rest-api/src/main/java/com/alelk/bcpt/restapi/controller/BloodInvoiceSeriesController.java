package com.alelk.bcpt.restapi.controller;

import com.alelk.bcpt.common.util.StringUtil;
import com.alelk.bcpt.database.service.BloodInvoiceSeriesService;
import com.alelk.bcpt.model.dto.BloodInvoiceSeriesDto;
import com.alelk.bcpt.model.pagination.Filter;
import com.alelk.bcpt.model.pagination.Page;
import com.alelk.bcpt.model.pagination.SortBy;
import com.alelk.bcpt.restapi.request.BloodInvoiceSeriesCreateRequest;
import com.alelk.bcpt.restapi.request.BloodInvoiceSeriesDeleteRequest;
import com.alelk.bcpt.restapi.request.BloodInvoiceSeriesUpdateRequest;
import com.alelk.bcpt.restapi.util.RestApiUtil;
import com.alelk.bcpt.restapi.validator.BloodInvoiceSeriesValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Blood Invoice Series Controller
 *
 * Created by Alex Elkin on 22.11.2017.
 */
@RestController
@RequestMapping("/bloodInvoiceSeries")
public class BloodInvoiceSeriesController {
    private final static Logger log = LoggerFactory.getLogger(BloodInvoiceSeriesController.class);
    private BloodInvoiceSeriesService bloodInvoiceSeriesService;
    private BloodInvoiceSeriesValidator bloodInvoiceSeriesValidator;

    @Autowired
    public BloodInvoiceSeriesController(BloodInvoiceSeriesService bloodInvoiceSeriesService, BloodInvoiceSeriesValidator bloodInvoiceSeriesValidator) {
        this.bloodInvoiceSeriesService = bloodInvoiceSeriesService;
        this.bloodInvoiceSeriesValidator = bloodInvoiceSeriesValidator;
    }

    @InitBinder
    public void setupBinder(WebDataBinder binder) {
        binder.addValidators(bloodInvoiceSeriesValidator);
    }

    @GetMapping("/")
    public List<BloodInvoiceSeriesDto> getAll() {
        return bloodInvoiceSeriesService.findAll();
    }

    @GetMapping("/page/{pageNumber}")
    public Page<BloodInvoiceSeriesDto> getAll(@PathVariable int pageNumber,
                                        @RequestParam(required = false) Integer itemsPerPage,
                                        @RequestParam(value = "sortBy", required = false) List<String> sortBy,
                                        @RequestParam(value = "filter", required = false) List<String> filter) {
        final List<SortBy> sortByList = RestApiUtil.parseSortParams(sortBy);
        final List<Filter> filterList = RestApiUtil.parseFilterParams(filter);
        log.debug("Request /bloodInvoiceSeries/page/" + pageNumber + "?itemsPerPage=" + itemsPerPage + ": sortBy=" +
                StringUtil.toString(sortByList) + " filter=" + StringUtil.toString(filterList));
        return bloodInvoiceSeriesService.findAll(pageNumber, itemsPerPage == null ? 100 : itemsPerPage, sortByList, filterList);
    }

    @PostMapping("/")
    public ResponseEntity<BloodInvoiceSeriesDto> create(@Validated @RequestBody BloodInvoiceSeriesCreateRequest request) {
        return ResponseEntity.ok(bloodInvoiceSeriesService.create(request.toDto()));
    }

    @PutMapping("/{externalId}")
    public ResponseEntity<BloodInvoiceSeriesDto> update(@PathVariable String externalId, @Validated @RequestBody BloodInvoiceSeriesUpdateRequest request) {
        return ResponseEntity.ok(bloodInvoiceSeriesService.update(externalId, request.toDto(), true,false));
    }

    @GetMapping("/{externalId}")
    public ResponseEntity<BloodInvoiceSeriesDto> get(@PathVariable String externalId) {
        final BloodInvoiceSeriesDto dto = bloodInvoiceSeriesService.findByExternalId(externalId);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/")
    public ResponseEntity<BloodInvoiceSeriesDto> delete(@Validated @RequestBody BloodInvoiceSeriesDeleteRequest request) {
        return ResponseEntity.ok(bloodInvoiceSeriesService.removeByExternalId(request.getExternalId()));
    }
}
