package com.insebre.project.exception;

public class FileNullOnSaveException extends Exception {

    public FileNullOnSaveException() {
        super();
    }

    public FileNullOnSaveException(String message) {
        super(message);
    }

    public FileNullOnSaveException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileNullOnSaveException(Throwable cause) {
        super(cause);
    }
}
