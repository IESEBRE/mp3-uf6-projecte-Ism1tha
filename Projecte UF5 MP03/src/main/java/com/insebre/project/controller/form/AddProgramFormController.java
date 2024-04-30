package com.insebre.project.controller.form;

import com.insebre.project.view.AddProgramForm;

import javax.swing.*;

public class AddProgramFormController {

    private final AddProgramForm addProgramForm;

    public AddProgramFormController(AddProgramForm addProgramForm) {
        this.addProgramForm = addProgramForm;
    }

    public void show() {
        JFrame frame = new JFrame("Add Program");
        frame.setContentPane(addProgramForm.getPanel1());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
