package com.alelk.bcpt.restapi.validator;

import com.alelk.bcpt.database.service.BloodDonationService;
import com.alelk.bcpt.database.service.PersonService;
import com.alelk.bcpt.model.dto.BloodDonationDto;
import com.alelk.bcpt.restapi.request.PersonAbstractRequest;
import com.alelk.bcpt.restapi.request.PersonCreateRequest;
import com.alelk.bcpt.restapi.request.PersonDeleteRequest;
import com.alelk.bcpt.restapi.request.PersonUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.List;

/**
 * Person Validator
 *
 * Created by Alex Elkin on 19.09.2017.
 */
@Component
public class PersonValidator implements Validator {

    private PersonService personService;
    private BloodDonationService bloodDonationService;

    @Autowired
    public PersonValidator(PersonService personService, BloodDonationService bloodDonationService) {
        this.personService = personService;
        this.bloodDonationService = bloodDonationService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return PersonAbstractRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "externalId", "externalId.empty");
        if (errors.hasErrors()) return;
        final PersonAbstractRequest request = (PersonAbstractRequest) target;
        boolean isIdExists = personService.isIdExists(request.getExternalId());
        if (target instanceof PersonUpdateRequest && !isIdExists)
            errors.rejectValue("externalId", "externalId.notFound");
        else if (target instanceof PersonCreateRequest && isIdExists)
            errors.rejectValue("externalId", "externalId.exists");
        else if (target instanceof PersonDeleteRequest) {
            if (!isIdExists) errors.rejectValue("externalId", "externalId.notFound");
            final List<BloodDonationDto> bloodDonations = bloodDonationService.findFor(((PersonDeleteRequest) target).toDto());
            if (bloodDonations != null && bloodDonations.size() > 0)
                errors.rejectValue("externalId", "externalId.hasDependencies");
        }

    }
}
