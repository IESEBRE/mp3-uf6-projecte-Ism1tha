package com.insebre.project.controller;

import com.insebre.project.exception.*;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.io.FileNotFoundException;

/**
 * Controller class responsible for handling exceptions and displaying error messages.
 * This class maps specific exception types to corresponding error messages for user-friendly error handling.
 *
 * @author Ismael Semmar Galvez
 * @version 1.0
 */
public class ExceptionController {

    private static final Map<Class<? extends Exception>, String> exceptionMessages = new HashMap<>();

    static {
        exceptionMessages.put(NullPointerException.class, "Null pointer exception occurred");
        exceptionMessages.put(FileNotFoundException.class, "File not found exception occurred");
        exceptionMessages.put(FileNullOnSaveException.class, "No file was found to save the data");
        exceptionMessages.put(FileCorruptDataException.class, "The file data is corrupt");
        exceptionMessages.put(FileDataGenerateErrorException.class, "An error occurred while generating the file data");
        exceptionMessages.put(InvalidPasswordException.class, "Forbidden access. Invalid password");
        exceptionMessages.put(EmptyFieldFoundException.class, "An empty field was found. Please fill all fields");
        exceptionMessages.put(InvalidVersionNameException.class, "Invalid version name");
        exceptionMessages.put(ExistingElementTreeSetException.class, "The element already exists in the TreeSet");
        exceptionMessages.put(InvalidInputFormatException.class, "Invalid input format");
    }

    /**
     * Handles the given exception by building an error message and displaying it using a message dialog.
     *
     * @param ex The exception to handle.
     */
    public static void handleException(Exception ex) {
        String errorMessage = buildErrorMessage(ex);
        showMessageDialog(errorMessage);
    }

    /**
     * Builds an error message based on the given exception.
     *
     * @param ex The exception for which to generate the error message.
     * @return The formatted error message.
     */
    private static String buildErrorMessage(Exception ex) {
        Class<? extends Exception> exClass = ex.getClass();
        String defaultMessage = "An unexpected error occurred";

        String exceptionMessage = exceptionMessages.getOrDefault(exClass, "Unknown exception occurred");

        String detailedMessage = ex.getMessage();

        StringBuilder errorMessageBuilder = new StringBuilder();

        if (exceptionMessage != null) {
            errorMessageBuilder.append(exceptionMessage);
        } else {
            errorMessageBuilder.append(defaultMessage);
        }

        if (detailedMessage != null && !detailedMessage.isEmpty()) {
            errorMessageBuilder.append(": ").append(detailedMessage);
        } else {
            errorMessageBuilder.append(".");
        }

        return errorMessageBuilder.toString();
    }

    /**
     * Displays the given error message in a message dialog with the title "Error".
     *
     * @param errorMessage The error message to display.
     */
    private static void showMessageDialog(String errorMessage) {
        JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

}
