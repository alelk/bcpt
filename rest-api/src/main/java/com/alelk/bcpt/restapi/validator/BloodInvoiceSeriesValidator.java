package com.alelk.bcpt.restapi.validator;

import com.alelk.bcpt.database.service.BloodInvoiceSeriesService;
import com.alelk.bcpt.database.service.BloodInvoiceService;
import com.alelk.bcpt.restapi.request.BloodInvoiceSeriesAbstractRequest;
import com.alelk.bcpt.restapi.request.BloodInvoiceSeriesCreateRequest;
import com.alelk.bcpt.restapi.request.BloodInvoiceSeriesDeleteRequest;
import com.alelk.bcpt.restapi.request.BloodInvoiceSeriesUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Blood Invoice Series Validator
 *
 * Created by Alex Elkin on 22.11.2017.
 */
@Component
public class BloodInvoiceSeriesValidator implements Validator {

    private BloodInvoiceSeriesService bloodInvoiceSeriesService;
    private BloodInvoiceService bloodInvoiceService;

    @Autowired
    public BloodInvoiceSeriesValidator(BloodInvoiceSeriesService bloodInvoiceSeriesService, BloodInvoiceService bloodInvoiceService) {
        this.bloodInvoiceSeriesService = bloodInvoiceSeriesService;
        this.bloodInvoiceService = bloodInvoiceService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return BloodInvoiceSeriesAbstractRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "externalId", "bloodInvoiceSeries.externalId.empty");
        if (errors.hasErrors()) return;
        BloodInvoiceSeriesAbstractRequest request = (BloodInvoiceSeriesAbstractRequest) target;
        boolean isIdExists = bloodInvoiceSeriesService.isIdExists(request.getExternalId());
        if (target instanceof BloodInvoiceSeriesUpdateRequest && !isIdExists)
            errors.rejectValue("externalId", "bloodInvoiceSeries.externalId.notFound");
        if (target instanceof BloodInvoiceSeriesCreateRequest && isIdExists)
            errors.rejectValue("externalId", "bloodInvoiceSeries.externalId.exists");
        if (target instanceof BloodInvoiceSeriesUpdateRequest || target instanceof BloodInvoiceSeriesCreateRequest) {
            if (request.getBloodInvoices() != null)
                request.getBloodInvoices().forEach(bloodInvoiceId -> {
                    if (!StringUtils.isEmpty(bloodInvoiceId) && !bloodInvoiceService.isIdExists(bloodInvoiceId))
                        errors.rejectValue(
                                "bloodInvoices",
                                "bloodInvoiceSeries.bloodInvoices.notFound",
                                new String[]{bloodInvoiceId}, null
                        );
                });
        }
        if (target instanceof BloodInvoiceSeriesDeleteRequest) {
            if (!isIdExists) errors.rejectValue("externalId", "bloodInvoiceSeries.externalId.notFound");
        }
    }
}
