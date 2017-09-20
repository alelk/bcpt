package com.alelk.bcpt.database.service;

import com.alelk.bcpt.database.model.BloodDonationPoolEntity;
import com.alelk.bcpt.database.model.ProductBatchEntity;
import com.alelk.bcpt.database.repository.BloodDonationPoolRepository;
import com.alelk.bcpt.database.repository.ProductBatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import static com.alelk.bcpt.database.util.ValidationUtil.*;

/**
 * Product Batch Service
 *
 * Created by Alex Elkin on 11.09.2017.
 */
@Service
public class ProductBatchService {

    private BloodDonationPoolRepository bloodDonationPoolRepository;
    private ProductBatchRepository productBatchRepository;

    @Autowired
    public ProductBatchService(BloodDonationPoolRepository bloodDonationPoolRepository, ProductBatchRepository productBatchRepository) {
        this.bloodDonationPoolRepository = bloodDonationPoolRepository;
        this.productBatchRepository = productBatchRepository;
    }

    @Transactional
    public ProductBatchEntity create(String externalId, Date batchDate, String[] bloodDonationPoolExternalIds) {
        final String message = "Cannot add new product batch info '" + externalId + ':';
        validateNotEmpty(externalId, message + " external id does'nt provided.");
        Set<BloodDonationPoolEntity> entities = bloodDonationPoolExternalIds != null
                ? Arrays.stream(bloodDonationPoolExternalIds)
                .map(bddExternalId -> bloodDonationPoolRepository.findByExternalId(bddExternalId))
                .collect(Collectors.toSet())
                : null;
        return productBatchRepository.save(new ProductBatchEntity(externalId, batchDate, entities));
    }

    @Transactional(readOnly = true)
    public ProductBatchEntity findById(Long id) {
        validateNotNull(id, "Cannot find product batch by id: no id provided.");
        return productBatchRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public ProductBatchEntity findByExternalId(String externalId) {
        validateNotEmpty(externalId, "Cannot find product batch by external id: no id provided.");
        return productBatchRepository.findByExternalId(externalId);
    }
}
