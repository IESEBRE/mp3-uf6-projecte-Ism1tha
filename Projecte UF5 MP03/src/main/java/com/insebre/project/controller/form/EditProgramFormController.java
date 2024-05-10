package com.insebre.project.controller.form;

import com.insebre.project.controller.AppController;
import com.insebre.project.controller.DataController;
import com.insebre.project.controller.PasswordController;
import com.insebre.project.exception.EmptyFieldFoundException;
import com.insebre.project.exception.InvalidInputFormatException;
import com.insebre.project.model.Program;
import com.insebre.project.view.EditProgramForm;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Controller class for managing the logic and behavior of the 'EditProgramForm'.
 * This class handles form submission, data validation, program editing, and password insertion.
 *
 * @author Ismael Semmar Galvez
 * @version 1.0
 */
public class EditProgramFormController {

    private final EditProgramForm editProgramForm;
    private final int programIndex;

    /**
     * Constructs an 'EditProgramFormController' with the specified 'EditProgramForm' and program index.
     *
     * @param editProgramForm The 'EditProgramForm' instance to be controlled.
     * @param programIndex    The index of the program to be edited in the data array.
     */
    public EditProgramFormController(EditProgramForm editProgramForm, int programIndex) {
        this.editProgramForm = editProgramForm;
        this.programIndex = programIndex;
    }

    /**
     * Displays the 'EditProgramForm' dialog for editing a program.
     */
    public void show() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Edit Program");
        dialog.setContentPane(editProgramForm.getPanel());
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.pack();
        dialog.setLocationRelativeTo(null);

        // Populate form fields with existing program data
        Program program = DataController.appData[programIndex];
        editProgramForm.getNameInput().setText(program.getName());
        editProgramForm.getDescriptionInput().setText(program.getDescription());
        editProgramForm.getCategoryInput().setText(program.getCategory());
        editProgramForm.getLanguageInput().setText(program.getLanguage());
        editProgramForm.getReleaseDateInput().setText(program.getReleaseDate());
        editProgramForm.getPasswordInput().setText(PasswordController.readProgramPassword(programIndex));

        dialog.setVisible(true);

        // Action listener for the submit button in the 'EditProgramForm'
        editProgramForm.getSubmitButton().addActionListener(e -> {
            try {
                // Retrieve input values from the form
                String name = editProgramForm.getNameInput().getText();
                String description = editProgramForm.getDescriptionInput().getText();
                String category = editProgramForm.getCategoryInput().getText();
                String language = editProgramForm.getLanguageInput().getText();
                String releaseDate = editProgramForm.getReleaseDateInput().getText();
                String password = editProgramForm.getPasswordInput().getText();

                // Validate input fields
                if (name.isEmpty()) throw new EmptyFieldFoundException("Name");
                if (description.isEmpty()) throw new EmptyFieldFoundException("Description");
                if (category.isEmpty()) throw new EmptyFieldFoundException("Category");
                if (language.isEmpty()) throw new EmptyFieldFoundException("Language");
                if (releaseDate.isEmpty()) throw new EmptyFieldFoundException("Release Date");
                if (password.isEmpty()) throw new EmptyFieldFoundException("Password");

                // Validate release date format and future date
                if (AppController.validateReleaseDate(releaseDate)) {
                    throw new InvalidInputFormatException("Invalid release date format (yyyy-mm-dd) or future date");
                }

                // Check password length
                if (password.length() != PasswordController.PASSWORD_LENGTH) {
                    throw new Exception("The password must be 4 characters long");
                } else {
                    // Update program data and save changes
                    Program editedProgram = DataController.appData[programIndex];
                    editedProgram.setName(name);
                    editedProgram.setDescription(description);
                    editedProgram.setCategory(category);
                    editedProgram.setLanguage(language);
                    editedProgram.setReleaseDate(releaseDate);
                    DataController.saveData();
                    PasswordController.insertProgramPassword(programIndex, password);
                    JOptionPane.showMessageDialog(null, "Program edited successfully!");
                    dialog.dispose();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });

        // Window listener to handle dialog closing event
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                AppController.showingEditProgramForm = false;
                AppController.refreshMainForm();
            }
        });
    }

}
