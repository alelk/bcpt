package com.alelk.bcpt.database.specifications;

import com.alelk.bcpt.database.model.*;
import com.alelk.bcpt.model.BloodType;
import com.alelk.bcpt.model.DonationType;
import com.alelk.bcpt.model.RhFactor;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Join;

/**
 * Blood Donation Specification
 *
 * Created by Alex Elkin on 20.10.2017.
 */
@Component
public class BloodDonationSpecifications extends AbstractSpecifications<BloodDonationEntity, BloodDonationEntity_> {

    public Specification<BloodDonationEntity> donorExternalIdStartsWith(Join<BloodDonationEntity, PersonEntity> persons, String donorExternalId) {
        return stringStartsWith(persons.get(PersonEntity_.externalId), donorExternalId);
    }

    public Specification<BloodDonationEntity> invoiceExternalIdStartsWith(Join<BloodDonationEntity, BloodInvoiceEntity> invoices, String invoiceExternalId) {
        return stringStartsWith(invoices.get(BloodInvoiceEntity_.externalId), invoiceExternalId);
    }

    public Specification<BloodDonationEntity> poolExternalIdStartsWith(Join<BloodDonationEntity, BloodPoolEntity> pools, String poolExternalId) {
        return stringStartsWith(pools.get(BloodPoolEntity_.externalId), poolExternalId);
    }

    public Specification<BloodDonationEntity> donationTypeEqual(DonationType donationType) {
        return valueEqual(BloodDonationEntity_.donationType, donationType);
    }

    public Specification<BloodDonationEntity> bloodTypeEqual(Join<BloodDonationEntity, PersonEntity> persons, BloodType bloodType) {
        return valueEqual(persons.get(PersonEntity_.bloodType), bloodType);
    }

    public Specification<BloodDonationEntity> rhFactorEqual(Join<BloodDonationEntity, PersonEntity> persons, RhFactor rhFactor) {
        return valueEqual(persons.get(PersonEntity_.rhFactor), rhFactor);
    }
}
