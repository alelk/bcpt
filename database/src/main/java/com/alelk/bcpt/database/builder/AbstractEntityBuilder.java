package com.alelk.bcpt.database.builder;

import com.alelk.bcpt.database.exception.BcptDatabaseException;
import com.alelk.bcpt.database.model.AbstractEntity;
import com.alelk.bcpt.model.dto.BcptDto;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Abstract Entity Builder
 *
 * Created by Alex Elkin on 12.09.2017.
 */
public abstract class AbstractEntityBuilder<DTO extends BcptDto, E extends AbstractEntity> implements EntityBuilder<DTO, E> {

    protected E entity;
    boolean mergeWithNullValues;

    private AbstractEntityBuilder(boolean mergeWithNullValues) {
        this.mergeWithNullValues = mergeWithNullValues;
    }

    AbstractEntityBuilder(Class<E> clazz, boolean mergeWithNullValues) {
        this(mergeWithNullValues);
        try {
            final Constructor<E> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            entity = constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new BcptDatabaseException("Error creating instance of class " + clazz.getName(), e);
        }
    }

    AbstractEntityBuilder(E existingEntity, boolean mergeWithNullValues) {
        this(mergeWithNullValues);
        entity = existingEntity;
    }

    public AbstractEntityBuilder<DTO, E> apply(DTO dto) {
        if (dto == null) return this;
        if (mergeWithNullValues || dto.getExternalId() != null)
            entity.setExternalId(dto.getExternalId());
        return this;
    }

    @Override
    public E build() {
        return entity;
    }
}
