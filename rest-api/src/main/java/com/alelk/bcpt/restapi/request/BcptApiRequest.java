package com.alelk.bcpt.restapi.request;

import com.alelk.bcpt.model.dto.BcptDto;

/**
 * Bcpt Rest Api Request
 *
 * Created by Alex Elkin on 19.09.2017.
 */
public interface BcptApiRequest<T extends BcptDto> {

    T toDto();
}
