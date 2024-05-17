package com.insebre.project.exception;

/**
 * Exception thrown when an empty field is encountered that should not be empty.
 * This exception is typically thrown when a required field is found to be empty.
 * The exception message provides details about the specific field or context of the error.
 *
 * @author Ismael Semmar Galvez
 */
public class EmptyFieldFoundException extends Exception {

    /**
     * Constructs a new EmptyFieldFoundException with no detail message.
     */
    public EmptyFieldFoundException() {
        super();
    }

    /**
     * Constructs a new EmptyFieldFoundException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method).
     */
    public EmptyFieldFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new EmptyFieldFoundException with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method).
     * @param cause   the cause (which is saved for later retrieval by the getCause() method).
     */
    public EmptyFieldFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new EmptyFieldFoundException with the specified cause and a detail message of
     * (cause==null ? null : cause.toString()) (which typically contains the class and detail message of cause).
     *
     * @param cause the cause (which is saved for later retrieval by the getCause() method).
     */
    public EmptyFieldFoundException(Throwable cause) {
        super(cause);
    }
}
