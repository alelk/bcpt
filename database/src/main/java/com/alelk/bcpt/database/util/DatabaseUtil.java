package com.alelk.bcpt.database.util;

import com.alelk.bcpt.database.builder.PersonDtoBuilder;
import com.alelk.bcpt.database.model.PersonEntity;
import com.alelk.bcpt.model.dto.PersonDto;

/**
 * DatabaseUtil
 *
 * Created by Alex Elkin on 10.10.2017.
 */
public class DatabaseUtil {

    public static PersonDto mapEntityToDto(PersonEntity entity) {
        return new PersonDtoBuilder().apply(entity).build();
    }
}
