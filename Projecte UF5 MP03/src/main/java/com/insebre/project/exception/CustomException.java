package com.insebre.project.exception;

public class CustomException extends Exception {

    public CustomException() {
        super("An error occurred in the project.");
    }

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }
}
