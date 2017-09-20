package com.alelk.bcpt.database.service;

import com.alelk.bcpt.database.model.BloodDonationDeliveryEntity;
import com.alelk.bcpt.database.model.BloodDonationEntity;
import com.alelk.bcpt.database.repository.BloodDonationDeliveryRepository;
import com.alelk.bcpt.database.repository.BloodDonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import static com.alelk.bcpt.database.util.ValidationUtil.*;

/**
 * Blood Donation Delivery Service
 *
 * Created by Alex Elkin on 11.09.2017.
 */
@Service
public class BloodDonationDeliveryService {

    private BloodDonationDeliveryRepository bloodDonationDeliveryRepository;
    private BloodDonationRepository bloodDonationRepository;

    @Autowired
    BloodDonationDeliveryService(BloodDonationDeliveryRepository bloodDonationDeliveryRepository, BloodDonationRepository bloodDonationRepository) {
        this.bloodDonationDeliveryRepository = bloodDonationDeliveryRepository;
        this.bloodDonationRepository = bloodDonationRepository;
    }

    @Transactional
    public BloodDonationDeliveryEntity create(String externalId, Date deliveryDate, String[] bloodDonationExternalIds) {
        final String message = "Cannot add new blood donation delivery info '" + externalId + ':';
        validateNotEmpty(externalId, message + " external id does'nt provided.");
        Set<BloodDonationEntity> donationEntities = bloodDonationExternalIds != null
                ? Arrays.stream(bloodDonationExternalIds)
                .map(bdExternalId -> bloodDonationRepository.findByExternalId(bdExternalId))
                .collect(Collectors.toSet())
                : null;
        return bloodDonationDeliveryRepository.save(new BloodDonationDeliveryEntity(externalId, deliveryDate, donationEntities));
    }

    @Transactional(readOnly = true)
    public BloodDonationDeliveryEntity findById(Long id) {
        validateNotNull(id, "Cannot find blood donation delivery by id: no id provided.");
        return bloodDonationDeliveryRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public BloodDonationDeliveryEntity findByExternalId(String externalId) {
        return findByExternalId(externalId, false);
    }

    @Transactional(readOnly = true)
    public BloodDonationDeliveryEntity findByExternalId(String externalId, boolean withDonationSet) {
        validateNotEmpty(externalId, "Cannot find blood donation delivery by external id: no id provided.");
        return withDonationSet ? withDonationSet(bloodDonationDeliveryRepository.findByExternalId(externalId))
                : bloodDonationDeliveryRepository.findByExternalId(externalId);
    }

    private BloodDonationDeliveryEntity withDonationSet(BloodDonationDeliveryEntity entity) {
        if (entity.getBloodDonations() != null) entity.getBloodDonations().size();
        return entity;
    }
}
