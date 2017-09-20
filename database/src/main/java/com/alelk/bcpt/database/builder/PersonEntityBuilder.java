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

    @Override
    public PersonEntityBuilder apply(PersonDto dto) {
        super.apply(dto);
        if (mergeWithNullValues || dto.getFirstName() != null)
            entity.setFirstName(dto.getFirstName());
        if (mergeWithNullValues || dto.getLastName() != null)
            entity.setLastName(dto.getLastName());
        if (mergeWithNullValues || dto.getMiddleName() != null)
            entity.setMiddleName(dto.getMiddleName());
        if (mergeWithNullValues || dto.getBloodType() != null)
            entity.setBloodType(dto.getBloodType());
        if (mergeWithNullValues || dto.getRhFactor() != null)
            entity.setRhFactor(dto.getRhFactor());
        return this;
    }
}
