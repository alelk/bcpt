package com.alelk.bcpt.importer.exception;

/**
 * Bcpt Importer Exception
 *
 * Created by Alex Elkin on 05.10.2017.
 */
public class BcptImporterException extends Exception {
    public BcptImporterException(String message) {
        super(message);
    }

    public BcptImporterException(String message, Throwable cause) {
        super(message, cause);
    }

    public BcptImporterException(Throwable cause) {
        super(cause);
    }

    public BcptImporterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
