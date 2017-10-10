package com.alelk.bcpt.importer.result;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Operation Result
 *
 * Created by Alex Elkin on 05.10.2017.
 */
public class OperationResult<SUBJECT> {

    public enum Result {
        IN_PROGRESS,
        SUCCESS,
        WITH_WARNINGS,
        FAILED
    }

    private SUBJECT subject;
    private Double progress;
    private Result result;
    private List<Throwable> errors;

    public OperationResult(SUBJECT subject, Double progress, Result result, List<Throwable> errors) {
        this.subject = subject;
        this.progress = progress;
        this.result = result;
        this.errors = errors;
    }

    public SUBJECT get() {
        return subject;
    }

    public Double getProgress() {
        return progress;
    }

    public Result getResult() {
        return result;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public List<Throwable> getErrors() {
        return errors;
    }

    public void addErrror(Throwable error) {
        if (errors == null) errors = new ArrayList<>();
        errors.add(error);

    }

    @Override
    public String toString() {
        return "OperationResult{" +
                "subject=" + subject +
                ", progress=" + progress +
                ", result=" + result +
                ", errorMessages=" + (errors != null && errors.size() > 0
                ? "[\n   " + errors.stream().map(Throwable::getLocalizedMessage).collect(Collectors.joining("\n   ")) + " ]" : "[]") +
                '}';
    }
}
