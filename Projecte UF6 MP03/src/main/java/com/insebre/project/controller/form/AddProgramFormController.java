package com.insebre.project.controller.form;

import com.insebre.project.controller.AppController;
import com.insebre.project.controller.ExceptionController;
import com.insebre.project.controller.PasswordController;
import com.insebre.project.exception.EmptyFieldFoundException;
import com.insebre.project.exception.InvalidInputFormatException;
import com.insebre.project.exception.dao.DAOException;
import com.insebre.project.model.Program;
import com.insebre.project.view.AddProgramForm;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

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
                String category = Objects.requireNonNull(addProgramForm.getCategoryComboBox().getSelectedItem()).toString();
                String language = Objects.requireNonNull(addProgramForm.getLanguageComboBox().getSelectedItem()).toString();
                String releaseDate = addProgramForm.getReleaseDateInput().getText();
                String password = new String(addProgramForm.getPasswordInput().getPassword());
                boolean paid = addProgramForm.getPaidCheckBox().isSelected();

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

                // Check password length and if is numeric
                if (password.length() != PasswordController.PASSWORD_LENGTH || !password.matches("[0-9]+")) {
                    throw new Exception("The password must be 4 characters long and contain only numbers");
                } else {
                    Program program = new Program(-1, name, description, category, language, releaseDate, password, paid);
                    AppController.programDAO.create(program);
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
        populateCategoryComboBox();
        populateLanguageComboBox();
        dialog.setVisible(true);

        // Window listener to handle form closing event
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                AppController.showingAddProgramForm = false;
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
        addProgramForm.getCategoryComboBox().removeAllItems();

        // Add categories to the combo box
        for (EditProgramFormController.Category category : EditProgramFormController.Category.values()) {
            addProgramForm.getCategoryComboBox().addItem(String.valueOf(category));
        }
    }

    /**
     * Populate the language combo box with some languages.
     */
    public void populateLanguageComboBox() {
        // Clear the combo box before adding new items (optional, depending on your requirements)
        addProgramForm.getLanguageComboBox().removeAllItems();

        // Add languages to the combo box
        for (EditProgramFormController.Language language : EditProgramFormController.Language.values()) {
            addProgramForm.getLanguageComboBox().addItem(String.valueOf(language));
        }
    }
}
