package com.alelk.bcpt.database.service;

import com.alelk.bcpt.database.model.BloodInvoiceEntity;
import com.alelk.bcpt.database.model.BloodPoolEntity;
import com.alelk.bcpt.database.repository.BloodInvoiceRepository;
import com.alelk.bcpt.database.repository.BloodPoolRepository;
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

    private BloodPoolRepository bloodPoolRepository;
    private BloodInvoiceRepository bloodInvoiceRepository;

    @Autowired
    public BloodDonationPoolService(BloodPoolRepository bloodPoolRepository, BloodInvoiceRepository bloodInvoiceRepository) {
        this.bloodPoolRepository = bloodPoolRepository;
        this.bloodInvoiceRepository = bloodInvoiceRepository;
    }

    @Transactional
    public BloodPoolEntity create(String externalId, int poolNumber, String[] bloodDonationDeliveryExternalIds) {
        final String message = "Cannot add new blood donation pool info '" + externalId + ':';
        validateNotEmpty(externalId, message + " external id does'nt provided.");
        Set<BloodInvoiceEntity> entities = bloodDonationDeliveryExternalIds != null
                ? Arrays.stream(bloodDonationDeliveryExternalIds)
                .map(bddExternalId -> bloodInvoiceRepository.findByExternalId(bddExternalId))
                .collect(Collectors.toSet())
                : null;
        return bloodPoolRepository.save(new BloodPoolEntity(externalId, poolNumber, entities));
    }

    @Transactional(readOnly = true)
    public BloodPoolEntity findById(Long id) {
        validateNotNull(id, "Cannot find blood donation pool by id: no id provided.");
        return bloodPoolRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public BloodPoolEntity findByExternalId(String externalId) {
        validateNotEmpty(externalId, "Cannot find blood donation pool by external id: no id provided.");
        return bloodPoolRepository.findByExternalId(externalId);
    }
}
