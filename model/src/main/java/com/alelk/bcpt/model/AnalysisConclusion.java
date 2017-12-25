package com.alelk.bcpt.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

/**
 * AnalysisConclusion
 *
 * Created by Alex Elkin on 25.12.2017.
 */
public enum AnalysisConclusion {

    FAIL(new String[]{"reject", "fail"}, 2),
    CONVERSION(new String[]{"conversion"}, 1),
    PASS(new String[]{"pass", "ok"}, 0);

    private String[] signatures;
    private int priority;

    AnalysisConclusion(String[] signatures, int priority) {
        this.signatures = signatures;
        this.priority = priority;
    }

    @JsonValue
    public String getSignature() {
        return signatures[0];
    }

    public int getPriority() {
        return priority;
    }

    public boolean morePriorityThan(AnalysisConclusion analysisConclusion) {
        return analysisConclusion == null || this.priority > analysisConclusion.priority;
    }

    @JsonCreator
    public static AnalysisConclusion forSignature(String signature) {
        for (AnalysisConclusion analysisConclusion : AnalysisConclusion.values())
            if (Arrays.asList(analysisConclusion.signatures).contains(signature))
                return analysisConclusion;
        return null;
    }
}
