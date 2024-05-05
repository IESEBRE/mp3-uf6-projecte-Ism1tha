package com.insebre.project.controller.form;

import com.insebre.project.controller.AppController;
import com.insebre.project.controller.DataController;
import com.insebre.project.controller.ExceptionController;
import com.insebre.project.controller.PasswordController;
import com.insebre.project.exception.InvalidPasswordException;
import com.insebre.project.view.AddProgramForm;
import com.insebre.project.view.EditProgramForm;
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

        this.mainForm.getAddButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if(AppController.showingAddProgramForm) throw new Exception("Add Program Form is already open!");
                    AddProgramForm addProgramForm = new AddProgramForm();
                    AddProgramFormController addProgramFormController = new AddProgramFormController(addProgramForm);
                    addProgramFormController.show();
                    AppController.showingAddProgramForm = true;
                }
                catch (Exception ex) {
                    ExceptionController.handleException(ex);
                }
            }
        });

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
                            if(!passwordPromptForm.isPasswordSubmittedSuccessfully()) throw new InvalidPasswordException();
                            else {
                                if(!PasswordController.checkProgramPassword(mainForm.getTable().getSelectedRow(), passwordPromptForm.getSubmittedPassword())) throw new InvalidPasswordException();
                                if(AppController.showingEditProgramForm) throw new Exception("Edit Program Form is already open!");
                                AppController.showingEditProgramForm = true;
                                EditProgramForm editProgramForm = new EditProgramForm();
                                EditProgramFormController editProgramFormController = new EditProgramFormController(editProgramForm, mainForm.getTable().getSelectedRow());
                                editProgramFormController.show();

                            }
                        } catch (Exception ex) {
                            ExceptionController.handleException(ex);
                        }
                    }
                });
            }
        });

        this.mainForm.getDeleteButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mainForm.getTable().getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a program to delete!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                PasswordPromptForm passwordPromptForm = new PasswordPromptForm();
                passwordPromptForm.setVisible(true);
                passwordPromptForm.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        try{
                            if(!passwordPromptForm.isPasswordSubmittedSuccessfully()) throw new InvalidPasswordException();
                            else {
                                int selectedProgramIndex = mainForm.getTable().getSelectedRow();
                                if(!PasswordController.checkProgramPassword(selectedProgramIndex, passwordPromptForm.getSubmittedPassword())) throw new InvalidPasswordException();
                                DataController.appData[selectedProgramIndex] = null;
                                DataController.realocateDataObjects();
                                DataController.saveData();
                                PasswordController.deleteProgramPassword(selectedProgramIndex);
                                DataController.appDataIndex--;
                                AppController.refreshMainForm();
                            }
                        } catch (Exception ex) {
                            ExceptionController.handleException(ex);
                        }
                    }
                });
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