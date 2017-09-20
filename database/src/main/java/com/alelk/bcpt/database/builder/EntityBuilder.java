package com.alelk.bcpt.database.builder;

import com.alelk.bcpt.database.model.AbstractEntity;
import com.alelk.bcpt.model.dto.BcptDto;

/**
 * Entity Builder
 *
 * Created by Alex Elkin on 12.09.2017.
 */
public interface EntityBuilder<DTO extends BcptDto, E extends AbstractEntity> {

    EntityBuilder<DTO, E> apply(DTO dto);

    E build();
}
