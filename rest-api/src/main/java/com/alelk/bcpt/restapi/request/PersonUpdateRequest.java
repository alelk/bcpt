package com.alelk.bcpt.restapi.request;

/**
 * Person Update Request
 *
 * Created by Alex Elkin on 19.09.2017.
 */
public class PersonUpdateRequest extends PersonAbstractRequest {

    @Override
    public String toString() {
        return "PersonUpdateRequest{" +
                "externalId='" + getExternalId() + '\'' +
                " firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", middleName='" + getMiddleName() + '\'' +
                ", bloodType=" + getBloodType() +
                ", rhFactor=" + getRhFactor() +
                "}";
    }
}
