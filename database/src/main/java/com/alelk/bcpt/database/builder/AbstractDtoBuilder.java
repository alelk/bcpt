package com.alelk.bcpt.database.builder;

import com.alelk.bcpt.database.exception.BcptDatabaseException;
import com.alelk.bcpt.database.model.AbstractEntity;
import com.alelk.bcpt.model.dto.BcptDto;

/**
 * Abstract DTO Builder
 *
 * Created by Alex Elkin on 12.09.2017.
 */
public abstract class AbstractDtoBuilder<E extends AbstractEntity, DTO extends BcptDto> implements DtoBuilder<E, DTO> {

    DTO dto;

    AbstractDtoBuilder(Class<DTO> clazz) {
        try {
            dto = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new BcptDatabaseException("Error creating instance of the class " + clazz.getName(), e);
        }
    }

    @Override
    public AbstractDtoBuilder<E, DTO> apply(E entity) {
        if (entity == null) return this;
        dto.setExternalId(entity.getExternalId());
        dto.setCreationTimestamp(entity.getCreationTimestamp());
        dto.setUpdateTimestamp(entity.getUpdateTimestamp());
        return this;
    }

    @Override
    public DTO build() {
        return dto;
    }
}
