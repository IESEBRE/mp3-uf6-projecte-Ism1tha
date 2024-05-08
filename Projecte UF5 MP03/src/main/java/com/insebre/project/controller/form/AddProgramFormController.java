package com.insebre.project.controller.form;

import com.insebre.project.controller.AppController;
import com.insebre.project.controller.DataController;
import com.insebre.project.controller.ExceptionController;
import com.insebre.project.controller.PasswordController;
import com.insebre.project.exception.EmptyFieldFoundException;
import com.insebre.project.exception.InvalidVersionNameException;
import com.insebre.project.model.Program;
import com.insebre.project.view.AddProgramForm;
import com.insebre.project.view.MainForm;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AddProgramFormController {

    private final AddProgramForm addProgramForm;
    private JDialog dialog;

    public AddProgramFormController(AddProgramForm addProgramForm) {
        this.addProgramForm = addProgramForm;

        addProgramForm.getSubmitButton().addActionListener(e -> {
            try {
                String name = addProgramForm.getNameInput().getText();
                String description = addProgramForm.getDescriptionInput().getText();
                String category = addProgramForm.getCategoryInput().getText();
                String language = addProgramForm.getLanguageInput().getText();
                String version = addProgramForm.getVersionInput().getText();
                String password = new String(addProgramForm.getPasswordInput().getPassword());

                if (name.isEmpty() || description.isEmpty() || category.isEmpty() || language.isEmpty() || version.isEmpty() || password.isEmpty()) {
                    throw new EmptyFieldFoundException();
                }
                if (!validateVersion(version)) {
                    throw new InvalidVersionNameException();
                }
                if (DataController.appDataIndex >= DataController.appData.length) {
                    throw new Exception("The maximum number of programs has been reached");
                }
                if (password.length() != PasswordController.PASSWORD_LENGTH) {
                    throw new Exception("The password must be 4 characters long");
                }
                else {
                    Program program = new Program(name, description, category, language, version, "2021-09-01");
                    Program[] currentData = DataController.getData();
                    int currentIndex = DataController.appDataIndex;
                    currentData[currentIndex] = program;
                    DataController.setData(currentData);
                    DataController.appDataIndex++;
                    DataController.saveData();
                    PasswordController.insertProgramPassword(currentIndex, password);
                    JOptionPane.showMessageDialog(null, "Program added successfully!");
                    dialog.dispose();
                }
            } catch (Exception ex) {
                ExceptionController.handleException(ex);
            }
        });
    }

    public boolean validateVersion(String version) {
        return version.matches("^[0-9]+\\.[0-9]+\\.[0-9]+$");
    }

    public void show() {
        dialog = new JDialog();
        dialog.setTitle("Add Program");
        dialog.setContentPane(addProgramForm.getPanel());
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                AppController.showingAddProgramForm = false;
                AppController.refreshMainForm();
            }
        });
    }
}
