package com.insebre.project.controller.form;

import com.insebre.project.controller.DataController;
import com.insebre.project.controller.ExceptionController;
import com.insebre.project.exception.EmptyFieldFoundException;
import com.insebre.project.model.Program;
import com.insebre.project.view.AddProgramForm;

import javax.swing.*;
import javax.xml.crypto.Data;

public class AddProgramFormController {

    private final AddProgramForm addProgramForm;

    public AddProgramFormController(AddProgramForm addProgramForm) {

        this.addProgramForm = addProgramForm;

        addProgramForm.getSubmitButton().addActionListener(e -> {
            try {
                System.out.println("Submit button clicked");
                String name = addProgramForm.getNameInput().getText();
                String description = addProgramForm.getDescriptionInput().getText();
                String category = addProgramForm.getCategoryInput().getText();
                String language = addProgramForm.getLanguageInput().getText();
                String version = addProgramForm.getVersionInput().getText();
                String password = new String(addProgramForm.getPasswordInput().getPassword());

                if (name.isEmpty() || description.isEmpty() || category.isEmpty() || language.isEmpty() || version.isEmpty() || password.isEmpty()) {
                    throw new EmptyFieldFoundException("Please fill all fields!");
                }
                else {
                    Program program = new Program(name, description, category, language, version, "2021-09-01");
                    Program[] currentData = DataController.getData();
                    int currentIndex = DataController.appDataIndex;
                    currentData[currentIndex] = program;
                    DataController.setData(currentData);
                    DataController.appDataIndex++;
                    JOptionPane.showMessageDialog(null, "Program added successfully!");
                    addProgramForm.close();
                }
            } catch (Exception ex) {
                ExceptionController.handleException(ex);
            }
        });
    }

    public void show() {
        addProgramForm.getPanel().setVisible(true);
    }

}
