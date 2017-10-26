package com.alelk.bcpt.restapi.validator;

import com.alelk.bcpt.database.service.BloodDonationService;
import com.alelk.bcpt.database.service.BloodInvoiceService;
import com.alelk.bcpt.database.service.BloodPoolService;
import com.alelk.bcpt.restapi.request.BloodInvoiceAbstractRequest;
import com.alelk.bcpt.restapi.request.BloodInvoiceCreateRequest;
import com.alelk.bcpt.restapi.request.BloodInvoiceDeleteRequest;
import com.alelk.bcpt.restapi.request.BloodInvoiceUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Blood Invoice Validator
 *
 * Created by Alex Elkin on 25.09.2017.
 */
@Component
public class BloodInvoiceValidator implements Validator{

    private BloodInvoiceService bloodInvoiceService;
    private BloodDonationService bloodDonationService;
    private BloodPoolService bloodPoolService;

    @Autowired
    public BloodInvoiceValidator(BloodInvoiceService bloodInvoiceService, BloodDonationService bloodDonationService, BloodPoolService bloodPoolService) {
        this.bloodInvoiceService = bloodInvoiceService;
        this.bloodDonationService = bloodDonationService;
        this.bloodPoolService = bloodPoolService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return BloodInvoiceAbstractRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "externalId", "bloodInvoice.externalId.empty");
        if (errors.hasErrors()) return;
        BloodInvoiceAbstractRequest request = (BloodInvoiceAbstractRequest) target;
        boolean isIdExists = bloodInvoiceService.isIdExists(request.getExternalId());
        if (target instanceof BloodInvoiceUpdateRequest && !isIdExists)
            errors.rejectValue("externalId", "bloodInvoice.externalId.notFound");
        if (target instanceof BloodInvoiceCreateRequest && isIdExists)
            errors.rejectValue("externalId", "bloodInvoice.externalId.exists");
        if (target instanceof BloodInvoiceCreateRequest || target instanceof BloodInvoiceUpdateRequest) {
            if (request.getBloodDonations() != null)
                request.getBloodDonations().forEach(bloodDonationId -> {
                    if (!StringUtils.isEmpty(bloodDonationId) && !bloodDonationService.isIdExists(bloodDonationId))
                        errors.rejectValue(
                            "bloodDonations",
                            "bloodInvoice.bloodDonations.notFound",
                            new String[]{bloodDonationId}, null
                    );
            });
            if (!StringUtils.isEmpty(request.getBloodPool()) && !bloodPoolService.isIdExists(request.getBloodPool())) {
                errors.rejectValue("bloodPool", "bloodPool.externalId.notFound");
            }
        }
        if (target instanceof BloodInvoiceDeleteRequest) {
            if (!isIdExists) errors.rejectValue("externalId", "bloodInvoice.externalId.notFound");
        }
    }
}
