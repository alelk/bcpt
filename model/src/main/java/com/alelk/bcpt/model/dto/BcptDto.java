package com.alelk.bcpt.model.dto;

import java.util.Date;

/**
 * Bcpt Data Transfer Object interface
 *
 * Created by Alex Elkin on 12.09.2017.
 */
public interface BcptDto {

    String getExternalId();
    void setExternalId(String externalId);
    Date getCreationTimestamp();
    void setCreationTimestamp(Date creationTimestamp);
    Date getUpdateTimestamp();
    void setUpdateTimestamp(Date updateTimestamp);
}
