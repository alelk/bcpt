package com.alelk.bcpt.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Donation Type
 *
 * Created by Alex Elkin on 04.10.2017.
 */
public enum DonationType {

    PLASMA_FRESH_FROZEN("plasma-fresh-frozen");

    private String signature;

    DonationType(String signature) {
        this.signature = signature;
    }

    @JsonValue
    public String getSignature() {
        return signature;
    }

    @JsonCreator
    public static DonationType forSignature(String signature) {
        for (DonationType dt : DonationType.values())
            if (dt.signature.equalsIgnoreCase(signature)) return dt;
        return null;
    }
}
