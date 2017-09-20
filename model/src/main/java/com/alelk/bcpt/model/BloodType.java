package com.alelk.bcpt.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Blood Group
 *
 * Created by Alex Elkin on 13.09.2017.
 */
public enum BloodType {

    O ("I", "0"),
    A ("II", "A"),
    B ("III", "B"),
    AB ("IV", "AB");

    private String signature;
    private String ab0signature;

    BloodType(String signature, String ab0signature) {
        this.signature = signature;
        this.ab0signature = ab0signature;
    }

    public String getSignature() {
        return signature;
    }

    public String getAb0signature() {
        return ab0signature;
    }

    @JsonValue
    public String getFullSignature() {
        return ab0signature + '(' + signature + ')';
    }

    @JsonCreator
    public static BloodType forString(String signature) {
        for (BloodType bloodType : BloodType.values())
            if (bloodType.getFullSignature().equalsIgnoreCase(signature)
                    || bloodType.getSignature().equalsIgnoreCase(signature)
                    || bloodType.getAb0signature().equalsIgnoreCase(signature)) return bloodType;
        return null;
    }
}
