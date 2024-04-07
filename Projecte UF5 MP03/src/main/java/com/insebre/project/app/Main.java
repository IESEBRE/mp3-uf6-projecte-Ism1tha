package com.insebre.project.app;

import com.insebre.project.controller.ExceptionController;
import com.insebre.project.util.ExceptionHandler;

import javax.swing.JOptionPane;


public class Main {
    public static void main(String[] args) {
        ExceptionController exceptionController = new ExceptionController();
        com.insebre.project.util.ExceptionHandler exceptionHandler = new ExceptionHandler();

        try {
            exceptionController.throwCustomException();
        } catch (Exception e) {
            String errorMessage = exceptionHandler.handleException(e);
            JOptionPane.showMessageDialog(null, "Error: " + errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}