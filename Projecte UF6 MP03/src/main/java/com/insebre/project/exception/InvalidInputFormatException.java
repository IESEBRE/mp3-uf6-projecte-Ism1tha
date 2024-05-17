package com.insebre.project.exception;

/**
 * Exception thrown when input data does not conform to the expected format.
 * This exception is typically thrown when input data is invalid or does not match the expected format
 * required by the application or operation.
 * The exception message provides details about the specific error encountered.
 *
 * @author Ismael Semmar Galvez
 */
public class InvalidInputFormatException extends Exception {

    /**
     * Constructs a new InvalidInputFormatException with no detail message.
     */
    public InvalidInputFormatException() {
        super();
    }

    /**
     * Constructs a new InvalidInputFormatException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method).
     */
    public InvalidInputFormatException(String message) {
        super(message);
    }

    /**
     * Constructs a new InvalidInputFormatException with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method).
     * @param cause   the cause (which is saved for later retrieval by the getCause() method).
     */
    public InvalidInputFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new InvalidInputFormatException with the specified cause and a detail message of
     * (cause==null ? null : cause.toString()) (which typically contains the class and detail message of cause).
     *
     * @param cause the cause (which is saved for later retrieval by the getCause() method).
     */
    public InvalidInputFormatException(Throwable cause) {
        super(cause);
    }
}
