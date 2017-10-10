package com.alelk.bcpt.importer.parsed;

import com.alelk.bcpt.model.dto.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Parsed Bundle
 *
 * Created by Alex Elkin on 04.10.2017.
 */
public class BcptDtoBundle {

    private Map<String, PersonDto> persons;
    private Map<String, BloodDonationDto> bloodDonations;
    private Map<String, BloodInvoiceDto> bloodInvoices;
    private Map<String, BloodPoolDto> bloodPools;
    private Map<String, ProductBatchDto> productBatches;

    public BcptDtoBundle() {
    }

    public BcptDtoBundle(Map<String, PersonDto> persons, Map<String, BloodDonationDto> bloodDonations, Map<String, BloodInvoiceDto> bloodInvoices, Map<String, BloodPoolDto> bloodPools, Map<String, ProductBatchDto> productBatches) {
        this.persons = persons;
        this.bloodDonations = bloodDonations;
        this.bloodInvoices = bloodInvoices;
        this.bloodPools = bloodPools;
        this.productBatches = productBatches;
    }

    public Map<String, PersonDto> getPersons() {
        return persons;
    }

    public void setPersons(Map<String, PersonDto> persons) {
        this.persons = persons;
    }

    public Map<String, BloodDonationDto> getBloodDonations() {
        return bloodDonations;
    }

    public void setBloodDonations(Map<String, BloodDonationDto> bloodDonations) {
        this.bloodDonations = bloodDonations;
    }

    public Map<String, BloodInvoiceDto> getBloodInvoices() {
        return bloodInvoices;
    }

    public void setBloodInvoices(Map<String, BloodInvoiceDto> bloodInvoices) {
        this.bloodInvoices = bloodInvoices;
    }

    public Map<String, BloodPoolDto> getBloodPools() {
        return bloodPools;
    }

    public void setBloodPools(Map<String, BloodPoolDto> bloodPools) {
        this.bloodPools = bloodPools;
    }

    public Map<String, ProductBatchDto> getProductBatches() {
        return productBatches;
    }

    public void setProductBatches(Map<String, ProductBatchDto> productBatches) {
        this.productBatches = productBatches;
    }

    public void addPerson(PersonDto personDto) {
        if (personDto == null || personDto.getExternalId() == null) return;
        if (persons == null) persons = new HashMap<>();
        persons.put(personDto.getExternalId(), personDto);
    }

    public PersonDto getPerson(String id) {
        return persons != null ? persons.get(id) : null;
    }

    public BloodDonationDto getBloodDonation(String id) {
        return bloodDonations != null ? bloodDonations.get(id) : null;
    }

    public BloodInvoiceDto getBloodInvoice(String id) {
        return bloodInvoices != null ? bloodInvoices.get(id) : null;
    }

    public BloodPoolDto getBloodPool(String id) {
        return bloodPools != null ? bloodPools.get(id) : null;
    }

    public ProductBatchDto getProductBatch(String id) {
        return productBatches != null ? productBatches.get(id) : null;
    }

    public void addBloodDonation(BloodDonationDto dto) {
        if (dto == null || dto.getExternalId() == null) return;
        if (bloodDonations == null) bloodDonations = new HashMap<>();
        bloodDonations.put(dto.getExternalId(), dto);
    }

    public void addBloodInvoice(BloodInvoiceDto dto) {
        if (dto == null || dto.getExternalId() == null) return;
        if (bloodInvoices == null) bloodInvoices = new HashMap<>();
        bloodInvoices.put(dto.getExternalId(), dto);
    }

    public void addBloodPool(BloodPoolDto dto) {
        if (dto == null || dto.getExternalId() == null) return;
        if (bloodPools == null) bloodPools = new HashMap<>();
        bloodPools.put(dto.getExternalId(), dto);
    }

    public void addProductBatch(ProductBatchDto dto) {
        if (dto == null || dto.getExternalId() == null) return;
        if (productBatches == null) productBatches = new HashMap<>();
        productBatches.put(dto.getExternalId(), dto);
    }

    @Override
    public String toString() {
        return "BcptDtoBundle{" +
                "persons=" + (persons == null ? null : '[' + persons.size() + " items]") +
                ", bloodDonations=" + (bloodDonations == null ? null : '[' + bloodDonations.size() + " items]") +
                ", bloodInvoices=" + (bloodInvoices == null ? null : '[' + bloodInvoices.size() + " items]") +
                ", bloodPools=" + (bloodPools == null ? null : '[' + bloodPools.size() + " items]") +
                ", productBatches=" + (productBatches == null ? null : '[' + productBatches.size() + " items]") +
                '}';
    }
}
