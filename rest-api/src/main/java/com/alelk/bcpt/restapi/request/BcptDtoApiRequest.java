package com.alelk.bcpt.restapi.request;

import com.alelk.bcpt.model.dto.BcptDto;

/**
 * Bcpt DTO Api Request
 *
 * Created by Alex Elkin on 19.09.2017.
 */
public abstract class BcptDtoApiRequest<T extends BcptDto> implements BcptApiRequest<T> {

    private String externalId;

    BcptDtoApiRequest() {}

    BcptDtoApiRequest(String externalId) {
        this.externalId = externalId;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }
}
