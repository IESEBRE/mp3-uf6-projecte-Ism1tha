package com.insebre.project.controller;

import com.insebre.project.exception.FileCorruptDataException;
import com.insebre.project.exception.FileDataGenerateErrorException;
import com.insebre.project.exception.FileNullOnSaveException;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

import java.io.FileNotFoundException;

public class ExceptionController {

    private static final Map<Class<? extends Exception>, String> exceptionMessages = new HashMap<>();

    static {
        exceptionMessages.put(NullPointerException.class, "Null pointer exception occurred.");
        exceptionMessages.put(FileNotFoundException.class, "File not found exception occurred.");
        exceptionMessages.put(FileNullOnSaveException.class, "No file was found to save the data.");
        exceptionMessages.put(FileCorruptDataException.class, "The file data is corrupt.");
        exceptionMessages.put(FileDataGenerateErrorException.class, "An error occurred while generating the file data.");
    }

    public static void handleException(Exception ex) {
        String errorMessage = exceptionMessages.get(ex.getClass());
        if (errorMessage == null) {
            errorMessage = "An unexpected error occurred: " + ex.getMessage();
        }
        JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

}
