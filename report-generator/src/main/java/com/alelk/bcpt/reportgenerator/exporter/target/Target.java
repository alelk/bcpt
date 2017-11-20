package com.alelk.bcpt.reportgenerator.exporter.target;

/**
 * Report Target
 *
 * Created by Alex Elkin on 15.06.2017.
 */
public interface Target<T> {

    TargetFormat getTargetFormat();

    void setTargetFormat(TargetFormat targetFormat);

    T getTarget();

    void setTarget(T target);
}
