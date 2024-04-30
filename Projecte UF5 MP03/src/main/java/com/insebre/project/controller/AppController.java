package com.insebre.project.controller;

import com.insebre.project.Main;
import com.insebre.project.controller.form.MainFormController;
import com.insebre.project.view.MainForm;

import java.util.Locale;

public class AppController {

    public void run() {
        try {
            Locale.setDefault(new Locale("ca","ES"));
            DataController.loadData();
            DataController.addDummyData();
            openMainForm();
        } catch (Exception e) {
            ExceptionController.handleException(e);
        }
    }

    public void openMainForm() {
        MainForm mainForm = new MainForm();
        MainFormController mainFormController = new MainFormController(mainForm);
        mainFormController.setTableData(DataController.getParsedPrograms());
        mainFormController.show();
    }

    public int getCurrentProgramIndex() {
        return Main.currentProgramIndex;
    }
}
