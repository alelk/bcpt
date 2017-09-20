package com.alelk.bcpt.restapi.request;

/**
 * Person Create Request
 *
 * Created by Alex Elkin on 20.09.2017.
 */
public class PersonCreateRequest extends PersonAbstractRequest {

    @Override
    public String toString() {
        return "PersonCreateRequest{" +
                "externalId='" + getExternalId() + '\'' +
                " firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", middleName='" + getMiddleName() + '\'' +
                ", bloodType=" + getBloodType() +
                ", rhFactor=" + getRhFactor() +
                "}";
    }
}
