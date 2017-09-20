package com.alelk.bcpt.database.builder;

import com.alelk.bcpt.database.model.AbstractEntity;
import com.alelk.bcpt.model.dto.BcptDto;

/**
 * DTO Builder
 *
 * Created by Alex Elkin on 12.09.2017.
 */
public interface DtoBuilder<E extends AbstractEntity, DTO extends BcptDto> {

    DtoBuilder<E, DTO> apply(E entity);

    DTO build();
}
