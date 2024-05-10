package com.insebre.project;

import com.insebre.project.controller.AppController;

/**
 * The entry point of the application.
 */
public class Main {

    /**
     * The main method initializes the application controller and starts the application.
     *
     * @param args The command line arguments (not used in this application).
     */
    public static void main(String[] args) {
        AppController appController = new AppController();
        appController.run();
    }
}
