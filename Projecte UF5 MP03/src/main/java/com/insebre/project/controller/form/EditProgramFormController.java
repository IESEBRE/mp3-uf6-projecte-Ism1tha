package com.insebre.project.controller.form;

import com.insebre.project.controller.AppController;
import com.insebre.project.controller.DataController;
import com.insebre.project.controller.PasswordController;
import com.insebre.project.view.EditProgramForm;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EditProgramFormController {

    private final EditProgramForm editProgramForm;
    private final int programIndex;

    public EditProgramFormController(EditProgramForm editProgramForm, int programIndex) {
        this.editProgramForm = editProgramForm;
        this.programIndex = programIndex;
    }

    public void show() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Edit program");
        dialog.setContentPane(editProgramForm.getPanel());
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.pack();
        dialog.setLocationRelativeTo(null);

        editProgramForm.getNameInput().setText(DataController.appData[programIndex].getName());
        editProgramForm.getDescriptionInput().setText(DataController.appData[programIndex].getDescription());
        editProgramForm.getCategoryInput().setText(DataController.appData[programIndex].getCategory());
        editProgramForm.getLanguageInput().setText(DataController.appData[programIndex].getLanguage());
        editProgramForm.getVersionInput().setText(DataController.appData[programIndex].getVersion());
        editProgramForm.getPasswordInput().setText(PasswordController.readProgramPassword(programIndex));

        dialog.setVisible(true);

        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                AppController.showingEditProgramForm = false;
                AppController.refreshMainForm();
            }
        });
    }

}
