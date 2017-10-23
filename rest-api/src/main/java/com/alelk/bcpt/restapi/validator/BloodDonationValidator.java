package com.alelk.bcpt.restapi.validator;

import com.alelk.bcpt.database.service.BloodDonationService;
import com.alelk.bcpt.database.service.BloodInvoiceService;
import com.alelk.bcpt.database.service.PersonService;
import com.alelk.bcpt.restapi.request.BloodDonationAbstractRequest;
import com.alelk.bcpt.restapi.request.BloodDonationCreateRequest;
import com.alelk.bcpt.restapi.request.BloodDonationDeleteRequest;
import com.alelk.bcpt.restapi.request.BloodDonationUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Blood Donation Validator
 *
 * Created by Alex Elkin on 21.09.2017.
 */
@Component
public class BloodDonationValidator implements Validator {

    private static final String EXTERNAL_ID_REGEX = "\\d{14}";
    private PersonService personService;
    private BloodDonationService bloodDonationService;
    private BloodInvoiceService bloodInvoiceService;

    @Autowired
    public BloodDonationValidator(PersonService personService, BloodDonationService bloodDonationService, BloodInvoiceService bloodInvoiceService) {
        this.personService = personService;
        this.bloodDonationService = bloodDonationService;
        this.bloodInvoiceService = bloodInvoiceService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return BloodDonationAbstractRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "externalId", "bloodDonation.externalId.empty");
        if (errors.hasErrors()) return;
        BloodDonationAbstractRequest request = (BloodDonationAbstractRequest) target;
        boolean isIdExists = bloodDonationService.isIdExists(request.getExternalId());
        if (target instanceof BloodDonationUpdateRequest && !isIdExists)
            errors.rejectValue("externalId", "bloodDonation.externalId.notFound");
        if (target instanceof BloodDonationCreateRequest && isIdExists)
            errors.rejectValue("externalId", "bloodDonation.externalId.exists");
        if ((target instanceof BloodDonationCreateRequest || target instanceof BloodDonationUpdateRequest)) {
            if (!request.getExternalId().matches(EXTERNAL_ID_REGEX))
                errors.rejectValue("externalId", "bloodDonation.externalId.invalid");
            if (!StringUtils.isEmpty(request.getDonor()) && !personService.isIdExists(request.getDonor()))
                errors.rejectValue("donorExternalId", "person.externalId.notFound");
            if (!StringUtils.isEmpty(request.getBloodInvoice()) && !bloodInvoiceService.isIdExists(request.getBloodInvoice()))
                errors.rejectValue("bloodInvoiceExternalId", "bloodInvoice.externalId.notFound");
        }
        if (target instanceof BloodDonationDeleteRequest) {
            if (!isIdExists) errors.rejectValue("externalId", "bloodDonation.externalId.notFound");
        }
    }
}
