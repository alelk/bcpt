package com.alelk.bcpt.reportgenerator.exporter.target;

/**
 * Abstract Target
 *
 * Created by Alex Elkin on 15.06.2017.
 */
public abstract class AbstractTarget<T> implements Target<T> {

    private TargetFormat mTargetFormat;
    private T mTarget;

    AbstractTarget(TargetFormat targetFormat, T target) {
        mTargetFormat = targetFormat;
        mTarget = target;
    }

    @Override
    public TargetFormat getTargetFormat() {
        return mTargetFormat;
    }

    @Override
    public void setTargetFormat(TargetFormat targetFormat) {
        mTargetFormat = targetFormat;
    }

    @Override
    public T getTarget() {
        return mTarget;
    }

    @Override
    public void setTarget(T target) {
        mTarget = target;
    }
}
