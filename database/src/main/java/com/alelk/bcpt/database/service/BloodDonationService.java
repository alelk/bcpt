package com.alelk.bcpt.database.service;

import com.alelk.bcpt.database.builder.BloodDonationDtoBuilder;
import com.alelk.bcpt.database.builder.BloodDonationEntityBuilder;
import com.alelk.bcpt.database.model.BloodDonationEntity;
import com.alelk.bcpt.database.model.BloodInvoiceEntity;
import com.alelk.bcpt.database.model.PersonEntity;
import com.alelk.bcpt.database.repository.BloodDonationRepository;
import com.alelk.bcpt.database.repository.BloodInvoiceRepository;
import com.alelk.bcpt.database.repository.PersonRepository;
import com.alelk.bcpt.model.dto.BloodDonationDto;
import com.alelk.bcpt.model.dto.PersonDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import static com.alelk.bcpt.database.util.ValidationUtil.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Blood Donation Service
 *
 * Created by Alex Elkin on 11.09.2017.
 */
@Service
public class BloodDonationService {

    private BloodDonationRepository bloodDonationRepository;
    private PersonRepository personRepository;
    private BloodInvoiceRepository bloodInvoiceRepository;

    @Autowired
    BloodDonationService(BloodDonationRepository bloodDonationRepository, PersonRepository personRepository, BloodInvoiceRepository bloodInvoiceRepository) {
        this.bloodDonationRepository = bloodDonationRepository;
        this.personRepository = personRepository;
        this.bloodInvoiceRepository = bloodInvoiceRepository;
    }

    @Transactional
    public BloodDonationDto create(BloodDonationDto bloodDonation) {
        final String message = "Cannot add new blood donation info '" + bloodDonation + "' to the database: ";
        validateNotNull(bloodDonation, message + "BloodDonation DTO must be not null!");
        validateNotEmpty(bloodDonation.getExternalId(), message + "no external id provided!");
        return new BloodDonationDtoBuilder().apply(
                bloodDonationRepository.save(new BloodDonationEntityBuilder()
                        .apply(bloodDonation)
                        .apply(findPersonByExternalId(bloodDonation.getDonorExternalId(), message))
                        .apply(findBloodInvoiceByExternalId(bloodDonation.getBloodInvoiceExternalId(), message))
                        .build())
        ).build();
    }

    @Transactional
    public BloodDonationDto update(String externalId, BloodDonationDto dto, boolean mergeWithNullValues) {
        final String message = "Error updating blood donation info " + dto + ": ";
        validateNotNull(dto, message + "Blood Donation DTO object must be not null.");
        if (mergeWithNullValues)
            validateNotEmpty(dto.getExternalId(), message + "no Blood Donation external id provided!");
        BloodDonationEntity entity = findEntityByExternalId(externalId, message);
        validateNotNull(entity, message + "Blood Donation external id does'nt exist.");
        return new BloodDonationDtoBuilder().apply(
                new BloodDonationEntityBuilder(entity, mergeWithNullValues)
                        .apply(dto).apply(findPersonByExternalId(dto.getDonorExternalId(), message))
                        .apply(findBloodInvoiceByExternalId(dto.getBloodInvoiceExternalId(), message))
                        .build()
        ).build();
    }

    @Transactional(readOnly = true)
    public List<BloodDonationDto> findAll() {
        return bloodDonationRepository.findAll().stream()
                .map(entity -> new BloodDonationDtoBuilder().apply(entity).build())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BloodDonationDto> findFor(final PersonDto donor) {
        final String message = "Cannot find blood donations for donor '" + donor + "': ";
        validateNotNull(donor, message + "Donor DTO must be specified.");
        validateNotEmpty(donor.getExternalId(), message + "Donor external id must be specified.");
        final PersonEntity pe = personRepository.findByExternalId(donor.getExternalId());
        validateNotNull(pe, message + "No donor found for the external id '" + donor.getExternalId() + "'");
        return bloodDonationRepository.findFor(pe).stream()
                .map(entity -> new BloodDonationDtoBuilder().apply(entity).build())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BloodDonationDto findByExternalId(String externalId) {
        return new BloodDonationDtoBuilder().apply(findEntityByExternalId(externalId, "")).build();
    }

    @Transactional
    public BloodDonationDto removeByExternalId(String externalId) {
        final String message = "Cannot remove Blood Donation by external id: ";
        final BloodDonationEntity entity = findEntityByExternalId(externalId, message);
        validateNotNull(entity, message + "no entity found for the external id '" + externalId + '\'');
        bloodDonationRepository.remove(entity);
        return new BloodDonationDtoBuilder().apply(entity).build();
    }

    @Transactional
    public boolean isIdExists(String externalId) {
        return !StringUtils.isEmpty(externalId) && findEntityByExternalId(externalId, "Error checking if the blood donation exists: ") != null;
    }

    private PersonEntity findPersonByExternalId(String externalId, String message) {
        if (StringUtils.isEmpty(externalId)) return null;
        PersonEntity pe = personRepository.findByExternalId(externalId);
        validateNotNull(pe, message + "Cannot find person by external id '" + externalId + '\'');
        return pe;
    }

    private BloodInvoiceEntity findBloodInvoiceByExternalId(String externalId, String message) {
        if (StringUtils.isEmpty(externalId)) return null;
        BloodInvoiceEntity bie = bloodInvoiceRepository.findByExternalId(externalId);
        validateNotNull(bie, message + "No blood invoice found with the external id = '" + externalId + '\'');
        return bie;
    }

    private BloodDonationEntity findEntityByExternalId(String externalId, String message) {
        validateNotEmpty(externalId, message + "Cannot find blood donation by external id: no id provided.");
        return bloodDonationRepository.findByExternalId(externalId);
    }
}
