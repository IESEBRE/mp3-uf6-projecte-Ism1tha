package com.insebre.project.exception;

public class InvalidVersionNameException extends Exception {

    public InvalidVersionNameException() {
        super();
    }

    public InvalidVersionNameException(String message) {
        super(message);
    }

    public InvalidVersionNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidVersionNameException(Throwable cause) {
        super(cause);
    }
}
