package com.alelk.bcpt.restapi.validator;

import com.alelk.bcpt.database.service.BloodInvoiceService;
import com.alelk.bcpt.database.service.BloodPoolService;
import com.alelk.bcpt.database.service.ProductBatchService;
import com.alelk.bcpt.restapi.request.BloodPoolAbstractRequest;
import com.alelk.bcpt.restapi.request.BloodPoolCreateRequest;
import com.alelk.bcpt.restapi.request.BloodPoolDeleteRequest;
import com.alelk.bcpt.restapi.request.BloodPoolUpdateRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Blood Pool Validator
 *
 * Created by Alex Elkin on 27.09.2017.
 */
@Component
public class BloodPoolValidator implements Validator {

    private BloodPoolService bloodPoolService;
    private BloodInvoiceService bloodInvoiceService;
    private ProductBatchService productBatchService;

    public BloodPoolValidator(BloodPoolService bloodPoolService, BloodInvoiceService bloodInvoiceService, ProductBatchService productBatchService) {
        this.bloodPoolService = bloodPoolService;
        this.bloodInvoiceService = bloodInvoiceService;
        this.productBatchService = productBatchService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return BloodPoolAbstractRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "externalId", "bloodPool.externalId.empty");
        if (errors.hasErrors()) return;
        BloodPoolAbstractRequest request = (BloodPoolAbstractRequest) target;
        boolean isIdExists = bloodPoolService.isIdExists(request.getExternalId());
        if (target instanceof BloodPoolUpdateRequest && !isIdExists)
            errors.rejectValue("externalId", "bloodPool.externalId.notFound");
        if (target instanceof BloodPoolCreateRequest && isIdExists)
            errors.rejectValue("externalId", "bloodPool.externalId.exists");
        if (target instanceof BloodPoolCreateRequest || target instanceof BloodPoolUpdateRequest) {
            if (request.getBloodInvoices() != null)
                request.getBloodInvoices().forEach(id -> {
                    if (!StringUtils.isEmpty(id) && !bloodInvoiceService.isIdExists(id))
                        errors.rejectValue(
                                "bloodInvoiceIds",
                                "bloodPool.bloodInvoiceIds.notFound",
                                new String[]{id}, null
                        );
                });
            if (!StringUtils.isEmpty(request.getProductBatch()) && !productBatchService.isIdExists(request.getProductBatch())) {
                errors.rejectValue("productBatchExternalId", "productBatch.externalId.notFound");
            }
        }
        if (target instanceof BloodPoolDeleteRequest) {
            if (!isIdExists) errors.rejectValue("externalId", "bloodPool.externalId.notFound");
        }
    }
}
