package com.insebre.project.exception;

/**
 * Exception thrown when attempting to save a null file or file data.
 * This exception is typically thrown when an attempt is made to save a file
 * or file data that is null or empty, which is not allowed in the context of the operation.
 * The exception message provides details about the specific error encountered.
 *
 * @author Ismael Semmar Galvez
 */
public class FileNullOnSaveException extends Exception {

    /**
     * Constructs a new FileNullOnSaveException with no detail message.
     */
    public FileNullOnSaveException() {
        super();
    }

    /**
     * Constructs a new FileNullOnSaveException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method).
     */
    public FileNullOnSaveException(String message) {
        super(message);
    }

    /**
     * Constructs a new FileNullOnSaveException with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method).
     * @param cause   the cause (which is saved for later retrieval by the getCause() method).
     */
    public FileNullOnSaveException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new FileNullOnSaveException with the specified cause and a detail message of
     * (cause==null ? null : cause.toString()) (which typically contains the class and detail message of cause).
     *
     * @param cause the cause (which is saved for later retrieval by the getCause() method).
     */
    public FileNullOnSaveException(Throwable cause) {
        super(cause);
    }
}
