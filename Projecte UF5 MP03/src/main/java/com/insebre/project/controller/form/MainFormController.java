package com.insebre.project.controller.form;

import com.insebre.project.controller.ExceptionController;
import com.insebre.project.exception.InvalidPasswordException;
import com.insebre.project.view.AddProgramForm;
import com.insebre.project.view.MainForm;
import com.insebre.project.view.PasswordPromptForm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFormController {

    private final MainForm mainForm;

    public MainFormController(MainForm mainForm) {
        this.mainForm = mainForm;

        this.mainForm.getEditButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mainForm.getTable().getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a program to edit!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                PasswordPromptForm passwordPromptForm = new PasswordPromptForm();
                passwordPromptForm.setVisible(true);
                passwordPromptForm.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        try{
                            if(!passwordPromptForm.isPasswordSubmittedSuccessfully()) throw new InvalidPasswordException("Invalid Password!");
                        } catch (InvalidPasswordException ex) {
                            ExceptionController.handleException(ex);
                        }
                    }
                });
            }
        });

        this.mainForm.getAddButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddProgramForm addProgramForm = new AddProgramForm();
                AddProgramFormController addProgramFormController = new AddProgramFormController(addProgramForm);
                addProgramFormController.show();
            }
        });
    }

    public void show() {
        JFrame frame = new JFrame("Software Manager - Ismael SG");
        frame.setContentPane(mainForm.getPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        mainForm.getTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        throw new IllegalArgumentException("Unknown test exception.");
    }

    public void hide() {
        mainForm.getPanel().setVisible(false);
    }

    public void setTableData(Object[][] data) {
        DefaultTableModel model = new DefaultTableModel(data, new String[]{"Name", "Description", "Category", "Language", "Version", "Release Date"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        mainForm.getTable().setModel(model);
    }
}