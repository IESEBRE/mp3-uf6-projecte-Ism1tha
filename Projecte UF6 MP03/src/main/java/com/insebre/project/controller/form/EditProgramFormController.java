package com.insebre.project.controller.form;

import com.insebre.project.controller.AppController;
import com.insebre.project.controller.PasswordController;
import com.insebre.project.exception.EmptyFieldFoundException;
import com.insebre.project.exception.InvalidInputFormatException;
import com.insebre.project.exception.dao.DAOException;
import com.insebre.project.model.Program;
import com.insebre.project.view.EditProgramForm;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Controller class for managing the logic and behavior of the 'EditProgramForm'.
 * This class handles form submission, data validation, program editing, and password insertion.
 *
 * @author Ismael Semmar Galvez
 * @version 1.0
 */
public class EditProgramFormController {

    private final EditProgramForm editProgramForm;
    private final int programID;

    public enum Category {
        ERP,
        CRM,
        SCM,
        HRM,
        BI,
        ECOMMERCE;
    }

    public enum Language {
        ENGLISH,
        SPANISH,
        CATALAN,
        MORROCAN,
    }

    /**
     * Constructs an 'EditProgramFormController' with the specified 'EditProgramForm' and program index.
     *
     * @param editProgramForm The 'EditProgramForm' instance to be controlled.
     * @param programID    The index of the program to be edited in the data array.
     */
    public EditProgramFormController(EditProgramForm editProgramForm, int programID) {
        this.editProgramForm = editProgramForm;
        this.programID = programID;
    }

    /**
     * Displays the 'EditProgramForm' dialog for editing a program.
     */
    public void show() throws DAOException {
        JDialog dialog = new JDialog();
        dialog.setTitle("Edit Program");
        dialog.setContentPane(editProgramForm.getPanel());
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.pack();
        dialog.setLocationRelativeTo(null);

        // Populate the category and language combo boxes
        populateCategoryComboBox();
        populateLanguageComboBox();

        // Populate form fields with existing program data
        Program program = AppController.programDAO.get(programID);
        editProgramForm.getNameInput().setText(program.getName());
        editProgramForm.getDescriptionInput().setText(program.getDescription());
        editProgramForm.getCategoryComboBox().setSelectedItem(program.getCategory());
        editProgramForm.getLanguageComboBox().setSelectedItem(program.getLanguage());
        editProgramForm.getReleaseDateInput().setText(program.getReleaseDate());
        editProgramForm.getPasswordInput().setText(program.getPassword());
        editProgramForm.getPaidCheckBox().setSelected(program.isPaid());

        dialog.setVisible(true);

        // Action listener for the submit button in the 'EditProgramForm'
        editProgramForm.getSubmitButton().addActionListener(e -> {
            try {
                // Retrieve input values from the form
                String name = editProgramForm.getNameInput().getText();
                String description = editProgramForm.getDescriptionInput().getText();
                String category = Objects.requireNonNull(editProgramForm.getCategoryComboBox().getSelectedItem()).toString();
                String language = Objects.requireNonNull(editProgramForm.getLanguageComboBox().getSelectedItem()).toString();
                String releaseDate = editProgramForm.getReleaseDateInput().getText();
                String password = editProgramForm.getPasswordInput().getText();
                boolean paid = editProgramForm.getPaidCheckBox().isSelected();

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
                    Program editedProgram = AppController.programDAO.get(programID);
                    editedProgram.setName(name);
                    editedProgram.setDescription(description);
                    editedProgram.setCategory(category);
                    editedProgram.setLanguage(language);
                    editedProgram.setReleaseDate(releaseDate);
                    editedProgram.setPassword(password);
                    editedProgram.setPaid(paid);
                    AppController.programDAO.update(editedProgram);
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
                try {
                    AppController.reloadMainFormTable();
                } catch (DAOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    /**
     * Populate the category combo box with some categories.
     */
    private void populateCategoryComboBox() {
        // Clear the combo box before adding new items (optional, depending on your requirements)
        editProgramForm.getCategoryComboBox().removeAllItems();

        // Add categories to the combo box
        for (Category category : Category.values()) {
            editProgramForm.getCategoryComboBox().addItem(String.valueOf(category));
        }
    }

    /**
     * Populate the language combo box with some languages.
     */
    public void populateLanguageComboBox() {
        // Clear the combo box before adding new items (optional, depending on your requirements)
        editProgramForm.getLanguageComboBox().removeAllItems();

        // Add languages to the combo box
        for (Language language : Language.values()) {
            editProgramForm.getLanguageComboBox().addItem(String.valueOf(language));
        }
    }
}
