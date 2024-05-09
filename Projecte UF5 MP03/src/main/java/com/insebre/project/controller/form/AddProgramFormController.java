package com.insebre.project.controller.form;

import com.insebre.project.controller.AppController;
import com.insebre.project.controller.DataController;
import com.insebre.project.controller.ExceptionController;
import com.insebre.project.controller.PasswordController;
import com.insebre.project.exception.EmptyFieldFoundException;
import com.insebre.project.exception.InvalidInputFormatException;
import com.insebre.project.model.Program;
import com.insebre.project.view.AddProgramForm;

import javax.swing.*;
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
                String releaseDate = addProgramForm.getReleaseDateInput().getText();
                String password = new String(addProgramForm.getPasswordInput().getPassword());

                if(name.isEmpty()) throw new EmptyFieldFoundException("Name");
                if(description.isEmpty()) throw new EmptyFieldFoundException("Description");
                if(category.isEmpty()) throw new EmptyFieldFoundException("Category");
                if(language.isEmpty()) throw new EmptyFieldFoundException("Language");
                if(releaseDate.isEmpty()) throw new EmptyFieldFoundException("Release Date");
                if(password.isEmpty()) throw new EmptyFieldFoundException("Password");

                if (AppController.validateReleaseDate(releaseDate)) {
                    throw new InvalidInputFormatException("Invalid release date format (yyyy-mm-dd) or future date");
                }
                if (DataController.appDataIndex >= DataController.appData.length) {
                    throw new Exception("The maximum number of programs has been reached");
                }
                if (password.length() != PasswordController.PASSWORD_LENGTH) {
                    throw new Exception("The password must be 4 characters long");
                }
                else {
                    Program program = new Program(name, description, category, language, releaseDate);
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
