package com.alelk.bcpt.common.process;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Process Progress
 *
 * Created by Alex Elkin on 05.10.2017.
 */
public class Progress<RESULT> {

    private RESULT result;
    private String processName;
    private Double progress;
    private ProcessState state;
    private List<Throwable> errors;

    public Progress() {
        this.progress = 0.0;
        this.state = ProcessState.IN_PROGRESS;
    }

    public Progress(RESULT result, Double progress, ProcessState state, List<Throwable> errors) {
        this.result = result;
        this.progress = progress;
        this.state = state;
        this.errors = errors;
    }

    public void setResult(RESULT result) {
        this.result = result;
    }

    public RESULT getResult() {
        return result;
    }

    public Double getProgress() {
        return progress;
    }

    public ProcessState getState() {
        return state;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }

    public void setState(ProcessState state) {
        this.state = state;
    }

    public void setErrors(List<Throwable> errors) {
        this.errors = errors;
    }

    public List<Throwable> getErrors() {
        return errors;
    }

    public void addError(Throwable error) {
        if (errors == null) errors = new ArrayList<>();
        errors.add(error);

    }

    @Override
    public String toString() {
        return "Progress{" +
                "result=" + result +
                ", processName=" + processName +
                ", progress=" + progress +
                ", state=" + state +
                ", errorMessages=" + (errors != null && errors.size() > 0
                ? "[\n   " + errors.stream().map(Throwable::getLocalizedMessage).collect(Collectors.joining("\n   ")) + " ]" : "[]") +
                '}';
    }
}
