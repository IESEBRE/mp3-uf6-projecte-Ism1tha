package com.insebre.project.controller;

import com.insebre.project.Main;
import com.insebre.project.controller.form.MainFormController;
import com.insebre.project.model.Program;
import com.insebre.project.view.MainForm;

import java.util.Locale;

public class AppController {

    static MainForm mainFormInstance;
    static MainFormController mainFormController;

    public static boolean showingAddProgramForm = false;
    public static boolean showingEditProgramForm = false;

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
        mainFormController.show();
    }

    public static void refreshMainForm() {
        mainFormController.setTableData(DataController.getParsedPrograms());
    }

    public int getCurrentProgramIndex() {
        return Main.currentProgramIndex;
    }
}
