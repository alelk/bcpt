package com.alelk.bcpt.database.service;

import com.alelk.bcpt.model.dto.BcptDto;

import java.util.List;

/**
 * BcptService
 *
 * Created by Alex Elkin on 06.10.2017.
 */
public interface BcptService<T extends BcptDto> {

    T create(T dto);

    T update(String externalId, T dto, boolean mergeWithNullValues, boolean softUpdate);

    List<T> findAll();

    T findByExternalId(String externalId);

    boolean isIdExists(String externalId);

    T removeByExternalId(String externalId);
}
