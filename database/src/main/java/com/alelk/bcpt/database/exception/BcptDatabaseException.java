package com.alelk.bcpt.database.exception;

/**
 * Bcpt Database Exception
 *
 * Created by Alex Elkin on 11.09.2017.
 */
public class BcptDatabaseException extends RuntimeException {

    public BcptDatabaseException() {
    }

    public BcptDatabaseException(String message) {
        super(message);
    }

    public BcptDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BcptDatabaseException(Throwable cause) {
        super(cause);
    }
}
