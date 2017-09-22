package com.alelk.bcpt.restapi.request;

import com.alelk.bcpt.model.BloodType;
import com.alelk.bcpt.model.RhFactor;
import com.alelk.bcpt.model.dto.PersonDto;

/**
 * Person Creation Request
 *
 * Created by Alex Elkin on 19.09.2017.
 */
public abstract class PersonAbstractRequest extends BcptDtoApiRequest<PersonDto> {

    private String firstName;
    private String lastName;
    private String middleName;
    private BloodType bloodType;
    private RhFactor rhFactor;

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
    public PersonDto toDto() {
        return new PersonDto(getExternalId(), null, null, firstName, lastName, middleName, bloodType, rhFactor);
    }

    @Override
    public String toString() {
        return "PersonAbstractRequest{" +
                "externalId='" + getExternalId() + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", bloodType=" + bloodType +
                ", rhFactor=" + rhFactor +
                '}';
    }
}
