package com.alelk.bcpt.reportgenerator.exporter.target;

import java.io.OutputStream;

/**
 * Output Stream Target
 *
 * Created by Alex Elkin on 15.06.2017.
 */
public class OutputStreamTarget extends AbstractTarget<OutputStream> {

    public OutputStreamTarget(TargetFormat targetFormat, OutputStream target) {
        super(targetFormat, target);
    }
}
