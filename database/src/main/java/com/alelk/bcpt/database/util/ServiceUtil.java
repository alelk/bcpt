package com.alelk.bcpt.database.util;

import com.alelk.bcpt.database.model.BloodDonationEntity;
import com.alelk.bcpt.database.model.BloodInvoiceEntity;
import com.alelk.bcpt.database.model.BloodPoolEntity;
import com.alelk.bcpt.database.model.ProductBatchEntity;
import com.alelk.bcpt.database.repository.BloodDonationRepository;
import com.alelk.bcpt.database.repository.BloodInvoiceRepository;
import com.alelk.bcpt.database.repository.BloodPoolRepository;
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
        final Set<BloodDonationEntity> entities = new HashSet<>();
        externalIds.forEach(externalId -> {
            if (StringUtils.isEmpty(externalId)) return;
            BloodDonationEntity entity = repository.findByExternalId(externalId);
            validateNotNull(entity, message + "Cannot find blood donation entity for the external id '" + externalId + '\'');
            entities.add(entity);
        });
        return entities.size() > 0 ? entities : null;
    }

    public static Set<BloodInvoiceEntity> getBloodInvoiceEntitiesByExternslIds(BloodInvoiceRepository repository, Set<String> externalIds, String message) {
        if (externalIds == null) return null;
        final Set<BloodInvoiceEntity> entities = new HashSet<>();
        externalIds.forEach(externalId -> {
            if (StringUtils.isEmpty(externalId)) return;
            BloodInvoiceEntity entity = repository.findByExternalId(externalId);
            validateNotNull(entity, message + "Cannot find blood invoice entity for the external id '" + externalId + '\'');
            entities.add(entity);
        });
        return entities.size() > 0 ? entities : null;
    }

    public static ProductBatchEntity getProductBatchEntityByExternalId(ProductBatchRepository repository, String externalId, String message) {
        if (StringUtils.isEmpty(externalId)) return null;
        ProductBatchEntity pbe = repository.findByExternalId(externalId);
        validateNotNull(pbe, message + "Cannot find the product batch for the external id '" + externalId + '\'');
        return pbe;
    }

    public static BloodPoolEntity getBloodPoolEntytyByExternalId(BloodPoolRepository repository, String externalId, String message) {
        if (StringUtils.isEmpty(externalId)) return null;
        BloodPoolEntity bpe = repository.findByExternalId(externalId);
        validateNotNull(bpe, message + "Cannot find the blood pool for the external id = '" + externalId + '\'');
        return bpe;
    }
}
