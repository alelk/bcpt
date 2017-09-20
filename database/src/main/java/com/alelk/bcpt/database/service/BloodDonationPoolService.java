package com.alelk.bcpt.database.service;

import com.alelk.bcpt.database.model.BloodDonationDeliveryEntity;
import com.alelk.bcpt.database.model.BloodDonationPoolEntity;
import com.alelk.bcpt.database.repository.BloodDonationDeliveryRepository;
import com.alelk.bcpt.database.repository.BloodDonationPoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static com.alelk.bcpt.database.util.ValidationUtil.*;

/**
 * Blood Donation Pool Service
 *
 * Created by Alex Elkin on 11.09.2017.
 */
@Service
public class BloodDonationPoolService {

    private BloodDonationPoolRepository bloodDonationPoolRepository;
    private BloodDonationDeliveryRepository bloodDonationDeliveryRepository;

    @Autowired
    public BloodDonationPoolService(BloodDonationPoolRepository bloodDonationPoolRepository, BloodDonationDeliveryRepository bloodDonationDeliveryRepository) {
        this.bloodDonationPoolRepository = bloodDonationPoolRepository;
        this.bloodDonationDeliveryRepository = bloodDonationDeliveryRepository;
    }

    @Transactional
    public BloodDonationPoolEntity create(String externalId, int poolNumber, String[] bloodDonationDeliveryExternalIds) {
        final String message = "Cannot add new blood donation pool info '" + externalId + ':';
        validateNotEmpty(externalId, message + " external id does'nt provided.");
        Set<BloodDonationDeliveryEntity> entities = bloodDonationDeliveryExternalIds != null
                ? Arrays.stream(bloodDonationDeliveryExternalIds)
                .map(bddExternalId -> bloodDonationDeliveryRepository.findByExternalId(bddExternalId))
                .collect(Collectors.toSet())
                : null;
        return bloodDonationPoolRepository.save(new BloodDonationPoolEntity(externalId, poolNumber, entities));
    }

    @Transactional(readOnly = true)
    public BloodDonationPoolEntity findById(Long id) {
        validateNotNull(id, "Cannot find blood donation pool by id: no id provided.");
        return bloodDonationPoolRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public BloodDonationPoolEntity findByExternalId(String externalId) {
        validateNotEmpty(externalId, "Cannot find blood donation pool by external id: no id provided.");
        return bloodDonationPoolRepository.findByExternalId(externalId);
    }
}
