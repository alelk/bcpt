package com.alelk.bcpt.restapi.validator;

import com.alelk.bcpt.database.service.BloodPoolAnalysisService;
import com.alelk.bcpt.database.service.BloodPoolService;
import com.alelk.bcpt.restapi.request.BloodPoolAnalysisAbstractRequest;
import com.alelk.bcpt.restapi.request.BloodPoolAnalysisCreateRequest;
import com.alelk.bcpt.restapi.request.BloodPoolAnalysisDeleteRequest;
import com.alelk.bcpt.restapi.request.BloodPoolAnalysisUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Blood Pool Analysis Validator
 *
 * Created by Alex Elkin on 23.11.2017.
 */
@Component
public class BloodPoolAnalysisValidator implements Validator {

    private BloodPoolService bloodPoolService;
    private BloodPoolAnalysisService bloodPoolAnalysisService;

    @Autowired
    public BloodPoolAnalysisValidator(BloodPoolService bloodPoolService, BloodPoolAnalysisService bloodPoolAnalysisService) {
        this.bloodPoolService = bloodPoolService;
        this.bloodPoolAnalysisService = bloodPoolAnalysisService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return BloodPoolAnalysisAbstractRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "externalId", "bloodPool.externalId.empty");
        if (errors.hasErrors()) return;
        BloodPoolAnalysisAbstractRequest request = (BloodPoolAnalysisAbstractRequest) target;
        boolean isIdExists = bloodPoolAnalysisService.isIdExists(request.getExternalId());
        boolean isPoolIdExists = bloodPoolService.isIdExists(request.getExternalId());
        if (target instanceof BloodPoolAnalysisUpdateRequest && (!isIdExists || !isPoolIdExists))
                errors.rejectValue("externalId", "bloodPool.externalId.notFound");
        if (target instanceof BloodPoolAnalysisCreateRequest) {
            if (isIdExists) errors.rejectValue("externalId", "bloodPool.externalId.exists");
            else if (!isPoolIdExists) errors.rejectValue("externalId", "bloodPool.externalId.notFound");
        }
        if (target instanceof BloodPoolAnalysisDeleteRequest) {
            if (!isIdExists) errors.rejectValue("externalId", "bloodPool.externalId.notFound");
        }
    }
}
