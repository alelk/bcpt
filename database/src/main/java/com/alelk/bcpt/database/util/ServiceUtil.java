package com.alelk.bcpt.database.util;

import com.alelk.bcpt.database.model.BloodDonationEntity;
import com.alelk.bcpt.database.model.ProductBatchEntity;
import com.alelk.bcpt.database.repository.BloodDonationRepository;
import com.alelk.bcpt.database.repository.ProductBatchRepository;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

import static com.alelk.bcpt.database.util.ValidationUtil.validateNotNull;

/**
 * Service Util
 *
 * Created by Alex Elkin on 26.10.2017.
 */
public class ServiceUtil {

    public static Set<BloodDonationEntity> getBloodDonationEntitiesByExternalIds(BloodDonationRepository repository, Set<String> externalIds, String message) {
        if (externalIds == null) return null;
        final Set<BloodDonationEntity> bloodDonationEntities = new HashSet<>();
        externalIds.forEach(externalId -> {
            if (StringUtils.isEmpty(externalId)) return;
            BloodDonationEntity bde = repository.findByExternalId(externalId);
            validateNotNull(bde, message + "Cannot find blood donation entity for the external id '" + externalId + '\'');
            bloodDonationEntities.add(bde);
        });
        return bloodDonationEntities.size() > 0 ? bloodDonationEntities : null;
    }

    public static ProductBatchEntity getProductBatchEntityByExternalId(ProductBatchRepository repository, String externalId, String message) {
        if (StringUtils.isEmpty(externalId)) return null;
        ProductBatchEntity pbe = repository.findByExternalId(externalId);
        validateNotNull(pbe, message + "Cannot find product batch for external id '" + externalId + '\'');
        return pbe;
    }
}
