package com.alelk.bcpt.storage.exception;

/**
 * Bcpt Storage Exception
 *
 * Created by Alex Elkin on 02.11.2017.
 */
public class BcptStorageException extends RuntimeException {

    public BcptStorageException(String message) {
        super(message);
    }

    public BcptStorageException(String message, Throwable cause) {
        super(message, cause);
    }

    public BcptStorageException(Throwable cause) {
        super(cause);
    }

    public BcptStorageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
