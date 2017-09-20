package com.alelk.bcpt.restapi.request;

/**
 * Person Delete Request
 *
 * Created by Alex Elkin on 20.09.2017.
 */
public class PersonDeleteRequest extends PersonAbstractRequest {

    @Override
    public String toString() {
        return "PersonDeleteRequest{" +
                "externalId='" + getExternalId() + '\'' +
                " firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", middleName='" + getMiddleName() + '\'' +
                ", bloodType=" + getBloodType() +
                ", rhFactor=" + getRhFactor() +
                "}";
    }
}
