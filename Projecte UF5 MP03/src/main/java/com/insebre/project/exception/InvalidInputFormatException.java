package com.insebre.project.exception;

public class InvalidInputFormatException extends Exception {

    public InvalidInputFormatException() {
        super();
    }

    public InvalidInputFormatException(String message) {
        super(message);
    }

    public InvalidInputFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidInputFormatException(Throwable cause) {
        super(cause);
    }
}
