package com.alelk.bcpt.model.dto;

import com.alelk.bcpt.model.BloodType;
import com.alelk.bcpt.model.RhFactor;

import java.util.Date;

/**
 * Person Dto
 *
 * Created by Alex Elkin on 12.09.2017.
 */
public class PersonDto extends AbstractBcptDto {

    private String firstName;
    private String lastName;
    private String middleName;
    private BloodType bloodType;
    private RhFactor rhFactor;

    public PersonDto() {
    }

    public PersonDto(String externalId, Date creationTimestamp, Date updateTimestamp) {
        super(externalId, creationTimestamp, updateTimestamp);
    }

    public PersonDto(String externalId, Date creationTimestamp, Date updateTimestamp, String firstName, String lastName, String middleName, BloodType bloodType, RhFactor rhFactor) {
        super(externalId, creationTimestamp, updateTimestamp);
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.bloodType = bloodType;
        this.rhFactor = rhFactor;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
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
        return "PersonDto{" +
                "externalId='" + getExternalId() + '\'' +
                " firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", bloodType='" + bloodType + '\'' +
                ", rhFactor='" + rhFactor + '\'' +
                ", creationTimestamp='" + getCreationTimestamp() + '\'' +
                ", updateTimestamp='" + getUpdateTimestamp() + '\'' +
                '}';
    }
}
