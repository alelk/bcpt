package com.alelk.bcpt.database.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Abstract Entity
 *
 * Created by Alex Elkin on 07.09.2017.
 */
@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    @NaturalId
    private String externalId;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTimestamp;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTimestamp;

    public AbstractEntity() {}

    public AbstractEntity(String externalId) {
        this.externalId = externalId;
    }

    public Long getId() {
        return id;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public Date getCreationTimestamp() {
        return creationTimestamp;
    }

    public Date getUpdateTimestamp() {
        return updateTimestamp;
    }
}
