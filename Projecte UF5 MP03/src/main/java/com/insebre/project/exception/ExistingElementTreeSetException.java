package com.insebre.project.exception;

/**
 * Exception thrown when attempting to add an existing element to a TreeSet.
 * This exception is typically thrown when trying to add an element to a TreeSet
 * that already contains an equivalent element, violating the unique element constraint of TreeSet.
 * The exception message provides details about the specific element or context of the error.
 *
 * @author Ismael Semmar Galvez
 */
public class ExistingElementTreeSetException extends Exception {

    /**
     * Constructs a new ExistingElementTreeSetException with no detail message.
     */
    public ExistingElementTreeSetException() {
        super();
    }

    /**
     * Constructs a new ExistingElementTreeSetException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method).
     */
    public ExistingElementTreeSetException(String message) {
        super(message);
    }

    /**
     * Constructs a new ExistingElementTreeSetException with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method).
     * @param cause   the cause (which is saved for later retrieval by the getCause() method).
     */
    public ExistingElementTreeSetException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new ExistingElementTreeSetException with the specified cause and a detail message of
     * (cause==null ? null : cause.toString()) (which typically contains the class and detail message of cause).
     *
     * @param cause the cause (which is saved for later retrieval by the getCause() method).
     */
    public ExistingElementTreeSetException(Throwable cause) {
        super(cause);
    }
}
