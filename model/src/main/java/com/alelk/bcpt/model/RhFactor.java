package com.alelk.bcpt.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Rh Factor
 *
 * Created by Alex Elkin on 13.09.2017.
 */
public enum RhFactor implements Serializable {

    POSITIVE(new String[]{"Rh+", "positive", "pos", "p", "+"}),
    NEGATIVE(new String[]{"Rh-", "negative", "neg", "n", "-"});

    private String[] signatures;

    RhFactor(String[] signatures) {
        this.signatures = signatures;
    }

    @JsonValue
    public String getSignature() {
        return signatures[0];
    }

    @JsonCreator
    public static RhFactor forSignature(String signature) {
        for (RhFactor rhFactor : RhFactor.values())
            if (Arrays.asList(rhFactor.signatures).contains(signature))
                return rhFactor;
        return null;
    }
}
