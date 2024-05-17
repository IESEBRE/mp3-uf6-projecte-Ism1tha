package com.insebre.project.exception;

/**
 * Exception thrown when data in a file is found to be corrupted or invalid.
 * This exception is typically thrown when attempting to read or process data from a file
 * that is not in the expected format or contains corrupt/inconsistent data.
 * The exception message provides details about the specific error encountered.
 *
 * @author Ismael Semmar Galvez
 */
public class FileCorruptDataException extends Exception {

    /**
     * Constructs a new FileCorruptDataException with no detail message.
     */
    public FileCorruptDataException() {
        super();
    }

    /**
     * Constructs a new FileCorruptDataException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method).
     */
    public FileCorruptDataException(String message) {
        super(message);
    }

    /**
     * Constructs a new FileCorruptDataException with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method).
     * @param cause   the cause (which is saved for later retrieval by the getCause() method).
     */
    public FileCorruptDataException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new FileCorruptDataException with the specified cause and a detail message of
     * (cause==null ? null : cause.toString()) (which typically contains the class and detail message of cause).
     *
     * @param cause the cause (which is saved for later retrieval by the getCause() method).
     */
    public FileCorruptDataException(Throwable cause) {
        super(cause);
    }
}
