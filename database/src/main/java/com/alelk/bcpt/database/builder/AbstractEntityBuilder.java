package com.alelk.bcpt.database.builder;

import com.alelk.bcpt.database.exception.BcptDatabaseException;
import com.alelk.bcpt.database.model.AbstractEntity;
import com.alelk.bcpt.model.dto.BcptDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Abstract Entity Builder
 *
 * Created by Alex Elkin on 12.09.2017.
 */
public abstract class AbstractEntityBuilder<DTO extends BcptDto, E extends AbstractEntity> implements EntityBuilder<DTO, E> {

    private final Logger log;
    protected E entity;
    boolean mergeWithNullValues;
    boolean softMerge;


    private AbstractEntityBuilder(boolean mergeWithNullValues, boolean softMerge) {
        log = LoggerFactory.getLogger(this.getClass());
        this.mergeWithNullValues = mergeWithNullValues;
        this.softMerge = softMerge;
    }

    AbstractEntityBuilder(Class<E> clazz, boolean mergeWithNullValues, boolean softMerge) {
        this(mergeWithNullValues, softMerge);
        try {
            final Constructor<E> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            entity = constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new BcptDatabaseException("Error creating instance of class " + clazz.getName(), e);
        }
    }

    AbstractEntityBuilder(Class<E> clazz, boolean mergeWithNullValues) {
        this(clazz, mergeWithNullValues, false);
    }

    AbstractEntityBuilder(E existingEntity, boolean mergeWithNullValues) {
        this(mergeWithNullValues, false);
        entity = existingEntity;
    }

    AbstractEntityBuilder(E existingEntity, boolean mergeWithNullValues, boolean softMerge) {
        this(mergeWithNullValues, softMerge);
        entity = existingEntity;
    }

    public AbstractEntityBuilder<DTO, E> apply(DTO dto) {
        if (dto == null) return this;
        if (entity.getExternalId() == null || mergeWithNullValues || !softMerge && dto.getExternalId() != null)
            entity.setExternalId(dto.getExternalId());
        return this;
    }

    @Override
    public E build() {
        return entity;
    }

    void setEntityFieldValue(String fieldName, Object value) {
        if (fieldName == null) return;
        try {
            Field entityField = entity.getClass().getDeclaredField(fieldName);
            boolean isAccessibleEntityField = entityField.isAccessible();
            entityField.setAccessible(true);
            Object currentValue = entityField.get(entity);
            if (softMerge && !mergeWithNullValues && value != null && Set.class.isAssignableFrom(value.getClass())
                    && currentValue != null && Set.class.isAssignableFrom(currentValue.getClass())) {
                Set<?> newSet = Stream.concat(((Set<?>) currentValue).stream(), ((Set<?>) value).stream()).collect(Collectors.toSet());
                entityField.set(entity, newSet);
            }
            if (currentValue == null || mergeWithNullValues || !softMerge && value != null)
                entityField.set(entity, value);
            entityField.setAccessible(isAccessibleEntityField);
        } catch (Exception e) {
            log.error("Unable to set field '" + fieldName + "' with value '" + value +
                    "' to the entity '" + entity.getClass() + "\' ", e.getClass().getName() + ": " + e.getLocalizedMessage());
        }
    }
}
