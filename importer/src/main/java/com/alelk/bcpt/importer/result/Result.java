package com.alelk.bcpt.importer.result;

/**
 * Result
 *
 * Created by Alex Elkin on 13.11.2017.
 */
public enum  Result {

    IN_PROGRESS (5),
    SUCCESS(4),
    WITH_WARNINGS (3),
    FAILED (0);

    private int level;

    public boolean isLessThan(Result result) {
        return this.level < result.level;
    }

    public boolean isGreatherThan(Result result) {
        return this.level > result.level;
    }

    Result(int level) {
        this.level = level;
    }
}
