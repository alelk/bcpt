package com.alelk.bcpt.common.process;

/**
 * Process State
 *
 * Created by Alex Elkin on 13.11.2017.
 */
public enum ProcessState {

    IN_PROGRESS (5),
    SUCCESS(4),
    WITH_WARNINGS (3),
    FAILED (0);

    private int level;

    public boolean isLessThan(ProcessState processState) {
        return this.level < processState.level;
    }

    public boolean isGreatherThan(ProcessState processState) {
        return this.level > processState.level;
    }

    ProcessState(int level) {
        this.level = level;
    }
}
