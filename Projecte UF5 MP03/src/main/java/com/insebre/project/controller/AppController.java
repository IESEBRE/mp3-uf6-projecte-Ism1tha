package com.insebre.project.controller;

import com.insebre.project.view.Form1;

public class AppController {

    public void run() {
        DataController.loadData();
        DataController.addDummyData();
        Form1 form1 = new Form1();
        form1.show();
    }
}
