package com.insebre.project.exception;

/**
 * Exception thrown when an invalid password is detected.
 * This exception is typically thrown when a password does not meet certain criteria
 * or fails validation checks.
 * The exception message provides details about the specific error encountered.
 *
 * @author Ismael Semmar Galvez
 */
public class InvalidPasswordException extends Exception {

    /**
     * Constructs a new InvalidPasswordException with no detail message.
     */
    public InvalidPasswordException() {
        super();
    }

    /**
     * Constructs a new InvalidPasswordException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method).
     */
    public InvalidPasswordException(String message) {
        super(message);
    }

    /**
     * Constructs a new InvalidPasswordException with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method).
     * @param cause   the cause (which is saved for later retrieval by the getCause() method).
     */
    public InvalidPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new InvalidPasswordException with the specified cause and a detail message of
     * (cause==null ? null : cause.toString()) (which typically contains the class and detail message of cause).
     *
     * @param cause the cause (which is saved for later retrieval by the getCause() method).
     */
    public InvalidPasswordException(Throwable cause) {
        super(cause);
    }
}
