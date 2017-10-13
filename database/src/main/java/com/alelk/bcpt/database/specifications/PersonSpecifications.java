package com.alelk.bcpt.database.specifications;

import com.alelk.bcpt.database.model.PersonEntity;
import com.alelk.bcpt.database.model.PersonEntity_;
import com.alelk.bcpt.model.BloodType;
import com.alelk.bcpt.model.RhFactor;
import org.springframework.stereotype.Component;

/**
 * PersonSpecifications
 *
 * Created by Alex Elkin on 12.10.2017.
 */
@Component
public class PersonSpecifications extends AbstractSpecifications<PersonEntity, PersonEntity_> {

    public Specification<PersonEntity> firstNameStartsWith(String name) {
        return stringFieldStartsWith(PersonEntity_.firstName, name);
    }

    public Specification<PersonEntity> lastNameStartsWith(String name) {
        return stringFieldStartsWith(PersonEntity_.lastName, name);
    }

    public Specification<PersonEntity> middleNameStartsWith(String name) {
        return stringFieldStartsWith(PersonEntity_.middleName, name);
    }

    public Specification<PersonEntity> rhFactorEqual(RhFactor rhFactor) {
        return valueEqual(PersonEntity_.rhFactor, rhFactor);
    }

    public Specification<PersonEntity> bloodTypeEqual(BloodType bloodType) {
        return valueEqual(PersonEntity_.bloodType, bloodType);
    }
}
