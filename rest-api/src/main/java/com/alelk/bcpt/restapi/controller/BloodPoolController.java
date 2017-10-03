package com.alelk.bcpt.restapi.controller;

import com.alelk.bcpt.database.service.BloodPoolService;
import com.alelk.bcpt.model.dto.BloodPoolDto;
import com.alelk.bcpt.restapi.request.BloodPoolCreateRequest;
import com.alelk.bcpt.restapi.request.BloodPoolDeleteRequest;
import com.alelk.bcpt.restapi.request.BloodPoolUpdateRequest;
import com.alelk.bcpt.restapi.validator.BloodPoolValidator;
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

    private BloodPoolService bloodPoolService;
    private BloodPoolValidator bloodPoolValidator;

    @Autowired
    public BloodPoolController(BloodPoolService bloodPoolService, BloodPoolValidator bloodPoolValidator) {
        this.bloodPoolService = bloodPoolService;
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

    @PostMapping("/")
    public ResponseEntity<BloodPoolDto> create(@Validated @RequestBody BloodPoolCreateRequest request) {
        return ResponseEntity.ok(bloodPoolService.create(request.toDto()));
    }

    @PutMapping("/{externalId}")
    public ResponseEntity<BloodPoolDto> update(@PathVariable String externalId, @Validated @RequestBody BloodPoolUpdateRequest request) {
        return ResponseEntity.ok(bloodPoolService.update(externalId, request.toDto(), true));
    }

    @GetMapping("/{externalId}")
    public BloodPoolDto get(@PathVariable String externalId) {
        return bloodPoolService.findByExternalId(externalId);
    }

    @DeleteMapping("/")
    public ResponseEntity<BloodPoolDto> delete(@Validated @RequestBody BloodPoolDeleteRequest request) {
        return ResponseEntity.ok(bloodPoolService.removeByExternalId(request.getExternalId()));
    }
}
