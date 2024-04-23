package com.insebre.project;

import com.insebre.project.controller.AppController;

public class Main {

    public static int currentProgramIndex = 0;

    public static void main(String[] args) {
        AppController appController = new AppController();
        appController.run();
    }
}