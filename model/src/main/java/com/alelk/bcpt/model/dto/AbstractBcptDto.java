package com.alelk.bcpt.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Abstract Bcpt Data Transfer Object
 *
 * Created by Alex Elkin on 12.09.2017.
 */
public abstract class AbstractBcptDto implements BcptDto{

    private String externalId;
    private Date creationTimestamp;
    private Date updateTimestamp;

    public AbstractBcptDto() {
    }

    AbstractBcptDto(String externalId, Date creationTimestamp, Date updateTimestamp) {
        this.externalId = externalId;
        this.creationTimestamp = creationTimestamp;
        this.updateTimestamp = updateTimestamp;
    }

    @Override
    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    @Override
    public void setCreationTimestamp(Date creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    @Override
    public void setUpdateTimestamp(Date updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    @Override
    public String getExternalId() {
        return externalId;
    }

    @Override
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    public Date getCreationTimestamp() {
        return creationTimestamp;
    }

    @Override
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    public Date getUpdateTimestamp() {
        return updateTimestamp;
    }
}
