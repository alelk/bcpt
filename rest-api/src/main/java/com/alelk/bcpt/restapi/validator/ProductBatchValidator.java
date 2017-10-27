package com.alelk.bcpt.restapi.validator;

import com.alelk.bcpt.database.service.BloodPoolService;
import com.alelk.bcpt.database.service.ProductBatchService;
import com.alelk.bcpt.restapi.request.ProductBatchAbstractRequest;
import com.alelk.bcpt.restapi.request.ProductBatchCreateRequest;
import com.alelk.bcpt.restapi.request.ProductBatchDeleteRequest;
import com.alelk.bcpt.restapi.request.ProductBatchUpdateRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Product Batch Validator
 *
 * Created by Alex Elkin on 27.09.2017.
 */

@Component
public class ProductBatchValidator implements Validator {

    private ProductBatchService productBatchService;
    private BloodPoolService bloodPoolService;

    public ProductBatchValidator(ProductBatchService productBatchService, BloodPoolService bloodPoolService) {
        this.productBatchService = productBatchService;
        this.bloodPoolService = bloodPoolService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return ProductBatchAbstractRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "externalId", "productBatch.externalId.empty");
        if (errors.hasErrors()) return;
        ProductBatchAbstractRequest request = (ProductBatchAbstractRequest) target;
        boolean isIdExists = productBatchService.isIdExists(request.getExternalId());
        if (target instanceof ProductBatchUpdateRequest && !isIdExists)
            errors.rejectValue("externalId", "productBatch.externalId.notFound");
        if (target instanceof ProductBatchCreateRequest && isIdExists)
            errors.rejectValue("externalId", "productBatch.externalId.exists");
        if (target instanceof ProductBatchUpdateRequest || target instanceof ProductBatchCreateRequest) {
            if (request.getBloodPools() != null)
                request.getBloodPools().forEach(id -> {
                    if (!StringUtils.isEmpty(id) && !bloodPoolService.isIdExists(id))
                        errors.rejectValue(
                                "bloodPools",
                                "productBatch.bloodPools.notFound",
                                new String[]{id}, null
                        );
                });
        }
        if (target instanceof ProductBatchDeleteRequest) {
            if (!isIdExists) errors.rejectValue("externalId", "productBatch.externalId.notFound");
        }
    }
}
