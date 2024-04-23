package com.insebre.project.controller;

import com.insebre.project.Main;
import com.insebre.project.view.Form1;

public class AppController {

    public void run() {
        DataController.loadData();
        DataController.addDummyData();
        openForm1();
    }

    public int getCurrentProgramIndex() {
        return Main.currentProgramIndex;
    }

    public void openForm1() {
        Form1 form1 = new Form1();
        form1.setTableData(DataController.getParsedPrograms());
        form1.show();
    }


}