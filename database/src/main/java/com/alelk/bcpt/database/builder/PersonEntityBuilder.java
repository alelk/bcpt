package com.alelk.bcpt.database.builder;

import com.alelk.bcpt.database.model.PersonEntity;
import com.alelk.bcpt.model.dto.PersonDto;

/**
 * Person Entity Builder
 *
 * Created by Alex Elkin on 12.09.2017.
 */
public class PersonEntityBuilder extends AbstractEntityBuilder<PersonDto, PersonEntity> {

    public PersonEntityBuilder() {
        super(PersonEntity.class, false);
    }

    public PersonEntityBuilder(PersonEntity existingEntity, boolean mergeWithNullValues) {
        super(existingEntity, mergeWithNullValues);
    }

    public PersonEntityBuilder(PersonEntity existingEntity, boolean mergeWithNullValues, boolean softMerge) {
        super(existingEntity, mergeWithNullValues, softMerge);
    }

    public PersonEntityBuilder(boolean mergeWithNullValues, boolean softMerge) {
        super(PersonEntity.class, mergeWithNullValues, softMerge);
    }

    @Override
    public PersonEntityBuilder apply(PersonDto dto) {
        super.apply(dto);
        setEntityFieldValue("firstName", dto.getFirstName());
        setEntityFieldValue("lastName", dto.getLastName());
        setEntityFieldValue("middleName", dto.getMiddleName());
        setEntityFieldValue("bloodType", dto.getBloodType());
        setEntityFieldValue("rhFactor", dto.getRhFactor());
        return this;
    }
}
