package com.insebre.project.exception;

/**
 * Exception thrown when an invalid version name is encountered.
 * This exception is typically thrown when a version name does not conform to the expected format
 * or fails validation checks.
 * The exception message provides details about the specific error encountered.
 *
 * @author Ismael Semmar Galvez
 */
public class InvalidVersionNameException extends Exception {

    /**
     * Constructs a new InvalidVersionNameException with no detail message.
     */
    public InvalidVersionNameException() {
        super();
    }

    /**
     * Constructs a new InvalidVersionNameException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method).
     */
    public InvalidVersionNameException(String message) {
        super(message);
    }

    /**
     * Constructs a new InvalidVersionNameException with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method).
     * @param cause   the cause (which is saved for later retrieval by the getCause() method).
     */
    public InvalidVersionNameException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new InvalidVersionNameException with the specified cause and a detail message of
     * (cause==null ? null : cause.toString()) (which typically contains the class and detail message of cause).
     *
     * @param cause the cause (which is saved for later retrieval by the getCause() method).
     */
    public InvalidVersionNameException(Throwable cause) {
        super(cause);
    }
}
