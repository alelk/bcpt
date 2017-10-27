package com.alelk.bcpt.restapi.controller;

import com.alelk.bcpt.database.service.ProductBatchService;
import com.alelk.bcpt.model.dto.ProductBatchDto;
import com.alelk.bcpt.model.pagination.Filter;
import com.alelk.bcpt.model.pagination.Page;
import com.alelk.bcpt.model.pagination.SortBy;
import com.alelk.bcpt.model.util.Util;
import com.alelk.bcpt.restapi.request.ProductBatchCreateRequest;
import com.alelk.bcpt.restapi.request.ProductBatchDeleteRequest;
import com.alelk.bcpt.restapi.request.ProductBatchUpdateRequest;
import com.alelk.bcpt.restapi.util.RestApiUtil;
import com.alelk.bcpt.restapi.validator.ProductBatchValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Product Batch Controller
 *
 * Created by Alex Elkin on 02.10.2017.
 */
@Controller
@RequestMapping("/productBatches")
public class ProductBatchController {

    private final static Logger log = LoggerFactory.getLogger(ProductBatchController.class);
    private ProductBatchService productBatchService;
    private ProductBatchValidator productBatchValidator;

    @Autowired
    public ProductBatchController(ProductBatchService productBatchService, ProductBatchValidator productBatchValidator) {
        this.productBatchService = productBatchService;
        this.productBatchValidator = productBatchValidator;
    }

    @InitBinder
    public void setupBinder(WebDataBinder binder) {
        binder.addValidators(productBatchValidator);
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductBatchDto>> getAll() {
        return ResponseEntity.ok(productBatchService.findAll());
    }

    @GetMapping("/page/{pageNumber}")
    public ResponseEntity<Page<ProductBatchDto>> getAll(@PathVariable int pageNumber,
                                        @RequestParam(required = false) Integer itemsPerPage,
                                        @RequestParam(value = "sortBy", required = false) List<String> sortBy,
                                        @RequestParam(value = "filter", required = false) List<String> filter) {
        final List<SortBy> sortByList = RestApiUtil.parseSortParams(sortBy);
        final List<Filter> filterList = RestApiUtil.parseFilterParams(filter);
        log.debug("Request /productBatches/page/" + pageNumber + "?itemsPerPage=" + itemsPerPage + ": sortBy=" +
                Util.toString(sortByList) + " filter=" + Util.toString(filterList));
        return ResponseEntity.ok(productBatchService.findAll(pageNumber, itemsPerPage == null ? 100 : itemsPerPage, sortByList, filterList));
    }

    @PostMapping("/")
    public ResponseEntity<ProductBatchDto> create(@Validated @RequestBody ProductBatchCreateRequest request) {
        return ResponseEntity.ok(productBatchService.create(request.toDto()));
    }

    @PutMapping("/{externalId}")
    public ResponseEntity<ProductBatchDto> update(@PathVariable String externalId, @Validated @RequestBody ProductBatchUpdateRequest request) {
        return ResponseEntity.ok(productBatchService.update(externalId, request.toDto(), true, false));
    }

    @GetMapping("/{externalId}")
    public ProductBatchDto get(@PathVariable String externalId) {
        return productBatchService.findByExternalId(externalId);
    }

    @DeleteMapping("/")
    public ResponseEntity<ProductBatchDto> delete(@Validated @RequestBody ProductBatchDeleteRequest request) {
        return ResponseEntity.ok(productBatchService.removeByExternalId(request.getExternalId()));
    }
}
