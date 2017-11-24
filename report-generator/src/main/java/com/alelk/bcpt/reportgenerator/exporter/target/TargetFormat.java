package com.alelk.bcpt.reportgenerator.exporter.target;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Target Format
 *
 * Created by Alex Elkin on 16.11.2017.
 */
public enum TargetFormat {
    PDF("PDF", "pdf"),
    XLS("XLS", "xls"),
    DOCX("DOCX", "docx");

    private String mSignature;
    private String mFileExtension;

    TargetFormat(String signature, String fileExtension) {
        mSignature = signature;
        mFileExtension = fileExtension;
    }

    @JsonValue
    public String getSignature() {
        return mSignature;
    }

    public String getFileExtension() {
        return mFileExtension;
    }

    @JsonCreator
    public static TargetFormat forValue(String signature) {
        for (TargetFormat format : TargetFormat.values()) {
            if (format.getSignature().equalsIgnoreCase(signature)) return format;
        }
        return null;
    }
}
