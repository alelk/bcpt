package com.alelk.bcpt.restapi.controller;

import com.alelk.bcpt.database.service.BloodInvoiceService;
import com.alelk.bcpt.model.dto.BloodInvoiceDto;
import com.alelk.bcpt.restapi.request.BloodInvoiceCreateRequest;
import com.alelk.bcpt.restapi.request.BloodInvoiceDeleteRequest;
import com.alelk.bcpt.restapi.request.BloodInvoiceUpdateRequest;
import com.alelk.bcpt.restapi.validator.BloodInvoiceValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Blood Invoice Controller
 *
 * Created by Alex Elkin on 25.09.2017.
 */
@RestController
@RequestMapping("/bloodInvoices")
public class BloodInvoiceController {

    private final static Logger log = LoggerFactory.getLogger(BloodInvoiceController.class);
    private BloodInvoiceService bloodInvoiceService;
    private BloodInvoiceValidator bloodInvoiceValidator;

    public BloodInvoiceController(BloodInvoiceService bloodInvoiceService, BloodInvoiceValidator bloodInvoiceValidator) {
        this.bloodInvoiceService = bloodInvoiceService;
        this.bloodInvoiceValidator = bloodInvoiceValidator;
    }

    @InitBinder
    public void setupBinder(WebDataBinder binder) {
        binder.addValidators(bloodInvoiceValidator);
    }

    @GetMapping("/")
    public List<BloodInvoiceDto> getAll() {
        return bloodInvoiceService.findAll();
    }

    @PostMapping("/")
    public ResponseEntity<BloodInvoiceDto> create(@Validated @RequestBody BloodInvoiceCreateRequest request) {
        return ResponseEntity.ok(bloodInvoiceService.create(request.toDto()));
    }

    @PutMapping("/{externalId}")
    public ResponseEntity<BloodInvoiceDto> update(@PathVariable String externalId, @Validated @RequestBody BloodInvoiceUpdateRequest request) {
        return ResponseEntity.ok(bloodInvoiceService.update(externalId, request.toDto(), true,false));
    }

    @GetMapping("/{externalId}")
    public BloodInvoiceDto get(@PathVariable String externalId) {
        return bloodInvoiceService.findByExternalId(externalId);
    }

    @DeleteMapping("/")
    public ResponseEntity<BloodInvoiceDto> delete(@Validated @RequestBody BloodInvoiceDeleteRequest request) {
        return ResponseEntity.ok(bloodInvoiceService.removeByExternalId(request.getExternalId()));
    }
}
