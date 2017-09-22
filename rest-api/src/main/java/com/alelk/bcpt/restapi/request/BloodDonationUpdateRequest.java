package com.alelk.bcpt.restapi.request;

/**
 * Blood Donation Update Request
 *
 * Created by Alex Elkin on 21.09.2017.
 */
public class BloodDonationUpdateRequest extends BloodDonationAbstractRequest {
    @Override
    public String toString() {
        return "BloodDonationUpdateRequest{" +
                "externalId='" + getExternalId() + '\'' +
                ", donorExternalId='" + getDonorExternalId() + '\'' +
                ", amount=" + getAmount() +
                ", donationDate=" + getDonationDate() +
                ", quarantineDate=" + getQuarantineDate() +
                '}';
    }
}
