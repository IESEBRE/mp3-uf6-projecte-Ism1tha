package com.insebre.project.exception;

public class FileCorruptDataException extends Exception {

    public FileCorruptDataException() {
        super();
    }

    public FileCorruptDataException(String message) {
        super(message);
    }

    public FileCorruptDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileCorruptDataException(Throwable cause) {
        super(cause);
    }
}
