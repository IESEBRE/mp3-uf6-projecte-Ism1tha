package com.insebre.project.controller.form;

import com.insebre.project.controller.AppController;
import com.insebre.project.controller.DataController;
import com.insebre.project.controller.PasswordController;
import com.insebre.project.exception.EmptyFieldFoundException;
import com.insebre.project.exception.InvalidInputFormatException;
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
        editProgramForm.getReleaseDateInput().setText(DataController.appData[programIndex].getReleaseDate());
        editProgramForm.getPasswordInput().setText(PasswordController.readProgramPassword(programIndex));

        dialog.setVisible(true);

        editProgramForm.getSubmitButton().addActionListener(e -> {
            try {
                String name = editProgramForm.getNameInput().getText();
                String description = editProgramForm.getDescriptionInput().getText();
                String category = editProgramForm.getCategoryInput().getText();
                String language = editProgramForm.getLanguageInput().getText();
                String releaseDate = editProgramForm.getReleaseDateInput().getText();
                String password = editProgramForm.getPasswordInput().getText();

                if(name.isEmpty()) throw new EmptyFieldFoundException("Name");
                if(description.isEmpty()) throw new EmptyFieldFoundException("Description");
                if(category.isEmpty()) throw new EmptyFieldFoundException("Category");
                if(language.isEmpty()) throw new EmptyFieldFoundException("Language");
                if(releaseDate.isEmpty()) throw new EmptyFieldFoundException("Release Date");
                if(password.isEmpty()) throw new EmptyFieldFoundException("Password");

                if (AppController.validateReleaseDate(releaseDate)) {
                    throw new InvalidInputFormatException("Invalid release date format (yyyy-mm-dd) or future date");
                }
                if (password.length() != PasswordController.PASSWORD_LENGTH) {
                    throw new Exception("The password must be 4 characters long");
                }
                else {
                    DataController.appData[programIndex].setName(name);
                    DataController.appData[programIndex].setDescription(description);
                    DataController.appData[programIndex].setCategory(category);
                    DataController.appData[programIndex].setLanguage(language);
                    DataController.appData[programIndex].setReleaseDate(releaseDate);
                    DataController.saveData();
                    PasswordController.insertProgramPassword(programIndex, password);
                    JOptionPane.showMessageDialog(null, "Program edited successfully!");
                    dialog.dispose();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });

        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                AppController.showingEditProgramForm = false;
                AppController.refreshMainForm();
            }
        });
    }

}
