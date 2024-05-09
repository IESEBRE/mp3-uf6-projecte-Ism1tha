package com.insebre.project.controller;

import com.insebre.project.controller.form.MainFormController;
import com.insebre.project.view.MainForm;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class AppController {

    static MainForm mainFormInstance;
    static MainFormController mainFormController;

    public static boolean showingAddProgramForm = false;
    public static boolean showingEditProgramForm = false;
    public static boolean showingViewProgramVersionForm = false;

    public void run() {
        try {
            Locale.setDefault(new Locale("ca","ES"));
            DataController.loadData();
            openMainForm();
        } catch (Exception e) {
            ExceptionController.handleException(e);
        }
    }

    public static void openMainForm() {
        mainFormInstance = new MainForm();
        mainFormController = new MainFormController(mainFormInstance);
        mainFormController.setTableData(DataController.getParsedPrograms());
        mainFormController.setImages();
        mainFormController.show();
    }

    public static void refreshMainForm() {
        mainFormController.setTableData(DataController.getParsedPrograms());
    }

    public static void refreshSelectedProgramInformation() {
        mainFormController.updateAppInformation();
    }

    public static boolean validateVersion(String version) {
        return !version.matches("^[0-9]+\\.[0-9]+\\.[0-9]+$");
    }

    public static boolean validateReleaseDate(String releaseDate) {
        if (!releaseDate.matches("^[0-9]{4}-[0-9]{2}-[0-9]{2}$")) {
            return true;
        }
        try {
            LocalDate parsedDate = LocalDate.parse(releaseDate, DateTimeFormatter.ISO_LOCAL_DATE);
            LocalDate currentDate = LocalDate.now();
            return !parsedDate.isBefore(currentDate);
        } catch (Exception e) {
            return true;
        }
    }
}
