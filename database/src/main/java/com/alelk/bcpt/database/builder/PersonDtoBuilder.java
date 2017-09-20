package com.alelk.bcpt.database.builder;

import com.alelk.bcpt.database.model.PersonEntity;
import com.alelk.bcpt.model.dto.PersonDto;

/**
 * Person DTO Builder
 *
 * Created by Alex Elkin on 12.09.2017.
 */
public class PersonDtoBuilder extends AbstractDtoBuilder<PersonEntity, PersonDto> {

    public PersonDtoBuilder() {
        super(PersonDto.class);
    }

    @Override
    public PersonDtoBuilder apply(PersonEntity entity) {
        super.apply(entity);
        if (entity == null) return this;
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setMiddleName(entity.getMiddleName());
        dto.setBloodType(entity.getBloodType());
        dto.setRhFactor(entity.getRhFactor());
        return this;
    }
}
