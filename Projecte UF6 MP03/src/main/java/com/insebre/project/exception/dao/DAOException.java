package com.insebre.project.exception.dao;

/**
 * Exception thrown when an error occurs in the data access object (DAO) layer.
 * This exception is typically thrown when an error occurs while interacting with the database
 * or when an operation on the database fails.
 * The exception message provides details about the specific element or context of the error.
 *
 * @author Ismael Semmar Galvez
 */
public class DAOException extends Exception {

    /**
     * Constructs a new DAOException with no detail message.
     */
    public DAOException() {
        super();
    }

    /**
     * Constructs a new DAOException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method).
     */
    public DAOException(String message) {
        super(message);
    }

    /**
     * Constructs a new DAOException with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method).
     * @param cause   the cause (which is saved for later retrieval by the getCause() method).
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new DAOException with the specified cause and a detail message of
     * (cause==null ? null : cause.toString()) (which typically contains the class and detail message of cause).
     *
     * @param cause the cause (which is saved for later retrieval by the getCause() method).
     */
    public DAOException(Throwable cause) {
        super(cause);
    }
}
