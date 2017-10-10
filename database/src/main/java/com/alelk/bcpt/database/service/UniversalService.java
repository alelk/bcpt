package com.alelk.bcpt.database.service;

import com.alelk.bcpt.model.dto.BcptDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static com.alelk.bcpt.database.util.ValidationUtil.*;

import java.util.List;
import java.util.Map;

/**
 * UniversalService
 *
 * Created by Alex Elkin on 09.10.2017.
 */
@Component
public class UniversalService {

    private Map<Class<?>, BcptService<?>> serviceAdapters;

    @Autowired
    public UniversalService(Map<Class<?>, BcptService<?>> serviceAdapters) {
        this.serviceAdapters = serviceAdapters;
    }

    public <T extends BcptDto> T create(T dto) {
        return getService(dto).create(dto);
    }

    public <T extends BcptDto> T update(String externalId, T dto, boolean mergeWithNulls, boolean softUpdate) {
        return getService(dto).update(externalId, dto, mergeWithNulls, softUpdate);
    }

    public <T extends BcptDto> T removeByExternalId(Class<T> dtoClass, String externalId) {
        return getService(dtoClass).removeByExternalId(externalId);
    }

    public <T extends BcptDto> T findByExternalId(Class<T> dtoClass, String externalId) {
        return getService(dtoClass).findByExternalId(externalId);
    }

    public <T extends BcptDto> List<T> findAll(Class<T> dtoClass) {
        return getService(dtoClass).findAll();
    }

    public <T extends BcptDto> boolean isExternalIdExists(Class<T> dtoClass, String externalId) {
        return getService(dtoClass).isIdExists(externalId);
    }

    @SuppressWarnings("unchecked")
    private <T extends BcptDto> BcptService<T> getService(T dto) {
        validateNotNull(dto, "Unable to save null dto object.");
        return (BcptService<T>) getService(dto.getClass());
    }

    @SuppressWarnings("unchecked")
    private <T extends BcptDto> BcptService<T> getService(Class<T> dtoClass) {
        validateTrue(serviceAdapters.containsKey(dtoClass), "Cannot find Service adapter for the DTO class '" + dtoClass + "'");
        return ((BcptService<T>) serviceAdapters.get(dtoClass));
    }
}
