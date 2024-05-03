package com.insebre.project.exception;

public class EmptyFieldFoundException extends Exception {

    public EmptyFieldFoundException() {
        super();
    }

    public EmptyFieldFoundException(String message) {
        super(message);
    }

    public EmptyFieldFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyFieldFoundException(Throwable cause) {
        super(cause);
    }
}
