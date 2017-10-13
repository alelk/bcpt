package com.alelk.bcpt.model.pagination;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

/**
 * Sort Direction
 *
 * Created by Alex Elkin on 11.10.2017.
 */
public enum  SortDirection {
    DESC(new String[]{"desc", "descending"}),
    ASC(new String[]{"asc", "ascending"});

    private String[] signatures;

    SortDirection(String[] signatures) {
        this.signatures = signatures;
    }

    @JsonValue
    public String getSignature() {
        return signatures[0];
    }

    @JsonCreator
    public static SortDirection forSignature(String signature) {
        if (signature == null) return null;
        for (SortDirection direction : SortDirection.values()) {
            if (Arrays.asList(direction.signatures).contains(signature.toLowerCase()))
                return direction;
        }
        return null;
    }
}
