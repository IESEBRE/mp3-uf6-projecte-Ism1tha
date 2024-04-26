package com.insebre.project.controller;

import com.insebre.project.Main;
import com.insebre.project.view.MainForm;

public class AppController {

    public void run() {
        try {
            DataController.loadData();
            DataController.addDummyData();
            openForm1();
        } catch (Exception e) {
            ExceptionController.handleException(e);
        }
    }

    public int getCurrentProgramIndex() {
        return Main.currentProgramIndex;
    }

    public void openForm1() {
        MainForm mainForm = new MainForm();
        mainForm.setTableData(DataController.getParsedPrograms());
        mainForm.show();
    }




}
