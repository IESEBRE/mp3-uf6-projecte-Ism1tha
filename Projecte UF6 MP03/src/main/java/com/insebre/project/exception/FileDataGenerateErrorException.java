package com.insebre.project.exception;

/**
 * Exception thrown when an error occurs during the generation of file data.
 * This exception is typically thrown when there are issues with generating or formatting data
 * intended for a file, such as CSV, XML, JSON, etc.
 * The exception message provides details about the specific error encountered.
 *
 * @author Ismael Semmar Galvez
 */
public class FileDataGenerateErrorException extends Exception {

    /**
     * Constructs a new FileDataGenerateErrorException with no detail message.
     */
    public FileDataGenerateErrorException() {
        super();
    }

    /**
     * Constructs a new FileDataGenerateErrorException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method).
     */
    public FileDataGenerateErrorException(String message) {
        super(message);
    }

    /**
     * Constructs a new FileDataGenerateErrorException with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method).
     * @param cause   the cause (which is saved for later retrieval by the getCause() method).
     */
    public FileDataGenerateErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new FileDataGenerateErrorException with the specified cause and a detail message of
     * (cause==null ? null : cause.toString()) (which typically contains the class and detail message of cause).
     *
     * @param cause the cause (which is saved for later retrieval by the getCause() method).
     */
    public FileDataGenerateErrorException(Throwable cause) {
        super(cause);
    }
}
