package com.alelk.bcpt.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Rh Factor
 *
 * Created by Alex Elkin on 13.09.2017.
 */
public enum RhFactor {
    POSITIVE("Rh+"),
    NEGATIVE("Rh-");

    private String signature;

    RhFactor(String signature) {
        this.signature = signature;
    }

    @JsonValue
    public String getSignature() {
        return signature;
    }

    @JsonCreator
    public static RhFactor forSignature(String signature) {
        for (RhFactor rhFactor : RhFactor.values())
            if (rhFactor.signature.equalsIgnoreCase(signature)) return rhFactor;
        return null;
    }
}
