package com.alelk.bcpt.restapi.request;

/**
 * Blood Donation Delete Request
 *
 * Created by Alex Elkin on 21.09.2017.
 */
public class BloodDonationDeleteRequest extends BloodDonationAbstractRequest {
    @Override
    public String toString() {
        return "BloodDonationDeleteRequest{" +
                "externalId='" + getExternalId() + '\'' +
                ", donorExternalId='" + getDonorExternalId() + '\'' +
                ", amount=" + getAmount() +
                ", donationDate=" + getDonationDate() +
                ", quarantineDate=" + getQuarantineDate() +
                '}';
    }
}
