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

/**
 * Controller class for managing the logic and behavior of the 'AddProgramForm'.
 * This class handles form submission, data validation, program creation, and password insertion.
 *
 * @author Ismael Semmar Galvez
 * @version 1.0
 */
public class AddProgramFormController {

    private final AddProgramForm addProgramForm;
    private JDialog dialog;

    /**
     * Constructs an 'AddProgramFormController' with the specified 'AddProgramForm'.
     *
     * @param addProgramForm The 'AddProgramForm' instance to be controlled.
     */
    public AddProgramFormController(AddProgramForm addProgramForm) {
        this.addProgramForm = addProgramForm;

        // Action listener for the submit button in the 'AddProgramForm'
        addProgramForm.getSubmitButton().addActionListener(e -> {
            try {
                // Retrieve input values from the form
                String name = addProgramForm.getNameInput().getText();
                String description = addProgramForm.getDescriptionInput().getText();
                String category = addProgramForm.getCategoryInput().getText();
                String language = addProgramForm.getLanguageInput().getText();
                String releaseDate = addProgramForm.getReleaseDateInput().getText();
                String password = new String(addProgramForm.getPasswordInput().getPassword());

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

                // Check if maximum program limit is reached
                if (DataController.appDataIndex >= DataController.appData.length) {
                    throw new Exception("The maximum number of programs has been reached");
                }

                // Check password length
                if (password.length() != PasswordController.PASSWORD_LENGTH) {
                    throw new Exception("The password must be 4 characters long");
                } else {
                    // Create a new program instance
                    Program program = new Program(name, description, category, language, releaseDate);

                    // Add the program to the data array
                    Program[] currentData = DataController.getData();
                    int currentIndex = DataController.appDataIndex;
                    currentData[currentIndex] = program;
                    DataController.setData(currentData);
                    DataController.appDataIndex++;

                    // Save data and insert program password
                    DataController.saveData();
                    PasswordController.insertProgramPassword(currentIndex, password);

                    // Show success message and dispose the dialog
                    JOptionPane.showMessageDialog(null, "Program added successfully!");
                    dialog.dispose();
                }
            } catch (Exception ex) {
                ExceptionController.handleException(ex);
            }
        });
    }

    /**
     * Displays the 'AddProgramForm' dialog.
     */
    public void show() {
        dialog = new JDialog();
        dialog.setTitle("Add Program");
        dialog.setContentPane(addProgramForm.getPanel());
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

        // Window listener to handle form closing event
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                AppController.showingAddProgramForm = false;
                AppController.refreshMainForm();
            }
        });
    }
}
