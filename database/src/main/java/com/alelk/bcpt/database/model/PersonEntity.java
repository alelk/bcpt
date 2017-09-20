package com.alelk.bcpt.database.model;

import com.alelk.bcpt.model.BloodType;
import com.alelk.bcpt.model.RhFactor;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import static com.alelk.bcpt.database.model.PersonEntity.*;

/**
 * PersonEntity Entity
 *
 * Created by Alex Elkin on 07.09.2017.
 */

@Entity
@Table(name = "persons")
@NamedQuery(name = QUERY_FIND_ALL, query = "select p from PersonEntity p")
public class PersonEntity extends AbstractEntity {

    public static final String QUERY_FIND_ALL = "findAll";

    private String firstName;
    private String lastName;
    private String middleName;
    private BloodType bloodType;
    private RhFactor rhFactor;

    public PersonEntity() {}

    public PersonEntity(String externalId, String firstName, String lastName, String middleName, String bloodInfo, BloodType bloodType, RhFactor rhFactor) {
        super(externalId);
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.bloodType = bloodType;
        this.rhFactor = rhFactor;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public RhFactor getRhFactor() {
        return rhFactor;
    }

    public void setRhFactor(RhFactor rhFactor) {
        this.rhFactor = rhFactor;
    }

    @Override
    public String toString() {
        return "PersonEntity{" +
                "id='" + getId() + '\'' +
                " externalId='" + getExternalId() + '\'' +
                " firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", bloodType='" + bloodType + '\'' +
                ", rhFactor='" + rhFactor + '\'' +
                ", creationTimestamp=" + getCreationTimestamp() +
                ", updateTimestamp=" + getUpdateTimestamp() +
                '}';
    }
}
