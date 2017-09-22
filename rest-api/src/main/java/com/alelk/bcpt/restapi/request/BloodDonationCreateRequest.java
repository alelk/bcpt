package com.alelk.bcpt.restapi.request;

/**
 * Blood Donation Create Request
 *
 * Created by Alex Elkin on 21.09.2017.
 */
public class BloodDonationCreateRequest extends BloodDonationAbstractRequest {
    @Override
    public String toString() {
        return "BloodDonationCreateRequest{" +
                "externalId='" + getExternalId() + '\'' +
                ", donorExternalId='" + getDonorExternalId() + '\'' +
                ", amount=" + getAmount() +
                ", donationDate=" + getDonationDate() +
                ", quarantineDate=" + getQuarantineDate() +
                '}';
    }
}
