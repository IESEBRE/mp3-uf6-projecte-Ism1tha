package com.insebre.project.controller;

import com.insebre.project.Main;
import com.insebre.project.controller.form.MainFormController;
import com.insebre.project.model.Program;
import com.insebre.project.view.MainForm;

import java.util.Locale;

public class AppController {

    MainForm mainFormInstance;
    MainFormController mainFormController;

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
        this.mainFormInstance = new MainForm();
        this.mainFormController = new MainFormController(this.mainFormInstance);
        mainFormController.setTableData(DataController.getParsedPrograms());
        mainFormController.show();
    }

    public void hideMainForm() {
        mainFormController.hide();
    }

    public int getCurrentProgramIndex() {
        return Main.currentProgramIndex;
    }
}
