package com.insebre.project.controller.form;

import com.insebre.project.controller.AppController;
import com.insebre.project.controller.DataController;
import com.insebre.project.controller.ExceptionController;
import com.insebre.project.controller.PasswordController;
import com.insebre.project.exception.InvalidPasswordException;
import com.insebre.project.model.SuperCollection;
import com.insebre.project.model.Version;
import com.insebre.project.view.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class MainFormController {

    private final MainForm mainForm;

    public MainFormController(MainForm mainForm) {
        this.mainForm = mainForm;

        this.mainForm.getAddButton().addActionListener(e -> {
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
        });

        this.mainForm.getEditButton().addActionListener(e -> {
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
                        if(passwordPromptForm.isPasswordSubmittedSuccessfully()) throw new InvalidPasswordException();
                        else {
                            if(PasswordController.checkProgramPassword(mainForm.getTable().getSelectedRow(), passwordPromptForm.getSubmittedPassword())) throw new InvalidPasswordException();
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
        });

        this.mainForm.getDeleteButton().addActionListener(e -> {
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
                        if(passwordPromptForm.isPasswordSubmittedSuccessfully()) throw new InvalidPasswordException();
                        else {
                            int selectedProgramIndex = mainForm.getTable().getSelectedRow();
                            if(PasswordController.checkProgramPassword(selectedProgramIndex, passwordPromptForm.getSubmittedPassword())) throw new InvalidPasswordException();
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
        });

        this.mainForm.getViewSelectedProgramVersionsButton().addActionListener(e -> {
            if (mainForm.getTable().getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "Please select a program to view its versions!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            PasswordPromptForm passwordPromptForm = new PasswordPromptForm();
            passwordPromptForm.setVisible(true);
            passwordPromptForm.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    try{
                        if(passwordPromptForm.isPasswordSubmittedSuccessfully()) throw new InvalidPasswordException();
                        else {
                            int selectedProgramIndex = mainForm.getTable().getSelectedRow();
                            if(PasswordController.checkProgramPassword(selectedProgramIndex, passwordPromptForm.getSubmittedPassword())) throw new InvalidPasswordException();
                            if(AppController.showingViewProgramVersionForm) throw new Exception("View Program Versions Form is already open!");
                            ProgramVersionsForm programVersionsForm = new ProgramVersionsForm();
                            ProgramVersionsFormController programVersionsFormController = new ProgramVersionsFormController(programVersionsForm, selectedProgramIndex);
                            programVersionsFormController.setTableData(DataController.getParsedProgramVersions(selectedProgramIndex));
                            programVersionsFormController.show();
                            AppController.showingViewProgramVersionForm = true;
                        }
                    } catch (Exception ex) {
                        ExceptionController.handleException(ex);
                    }
                }
            });
        });

        this.mainForm.getSwitchSelectedProgramSupTypeButton().addActionListener(e -> {
            if (mainForm.getTable().getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "Please select a program to switch its SuperCollection type!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            PasswordPromptForm passwordPromptForm = new PasswordPromptForm();
            passwordPromptForm.setVisible(true);
            passwordPromptForm.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    try{
                        if(passwordPromptForm.isPasswordSubmittedSuccessfully()) throw new InvalidPasswordException();
                        if(AppController.showingViewProgramVersionForm) throw new Exception("Close the View Program Versions Form before switching the SuperCollection type");
                        else {
                            int selectedProgramIndex = mainForm.getTable().getSelectedRow();
                            if(PasswordController.checkProgramPassword(selectedProgramIndex, passwordPromptForm.getSubmittedPassword())) throw new InvalidPasswordException();
                            DataController.switchProgramSuperCollectionType(selectedProgramIndex);
                            DataController.saveData();
                            AppController.refreshSelectedProgramInformation();
                        }
                    } catch (Exception ex) {
                        ExceptionController.handleException(ex);
                    }
                }
            });
        });

        this.mainForm.getTable().getSelectionModel().addListSelectionListener(e -> updateAppInformation());
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

    public void updateAppInformation() {
        if (mainForm.getTable().getSelectedRow() != -1) {
            int selectedIndex = mainForm.getTable().getSelectedRow();
            SuperCollection<Version> superCollection = DataController.appData[selectedIndex].getVersions();
            if (superCollection.getType() == SuperCollection.CollectionType.ARRAY_LIST) {
                mainForm.getSelectedSuperCollectionTypeLabel().setText("SuperCollection Type: ArrayList");
            } else {
                mainForm.getSelectedSuperCollectionTypeLabel().setText("SuperCollection Type: TreeSet");
            }
            mainForm.getSelectedSuperCollectionTypeLabel().setVisible(true);
            mainForm.getEditButton().setEnabled(true);
            mainForm.getDeleteButton().setEnabled(true);
            mainForm.getViewSelectedProgramVersionsButton().setEnabled(true);
            mainForm.getSelectedSuperCollectionTotalVersionsLabel().setText("Total Versions: " + superCollection.size());
            mainForm.getSelectedSuperCollectionTotalVersionsLabel().setVisible(true);
            mainForm.getSwitchSelectedProgramSupTypeButton().setEnabled(true);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate lastMonth = LocalDate.now().minusMonths(1);

            SuperCollection<Version> versionsList = DataController.appData[selectedIndex].getVersions();

            long count = versionsList.stream()
                    .filter(version -> {
                        try {
                            LocalDate releaseDate = LocalDate.parse(version.getDate(), formatter);
                            return !releaseDate.isAfter(LocalDate.now()) && releaseDate.isAfter(lastMonth);
                        } catch (DateTimeParseException e) {
                            System.err.println("Error parsing release date: " + e.getMessage());
                            return false;
                        }
                    })
                    .count();

            mainForm.getSelectedSuperCollectionLastTotalVersionsLabel().setText("Versions released last month: " + count);
            mainForm.getSelectedSuperCollectionLastTotalVersionsLabel().setVisible(true);
        } else {
            mainForm.getSelectedSuperCollectionLastTotalVersionsLabel().setVisible(false);
            mainForm.getSelectedSuperCollectionTotalVersionsLabel().setVisible(false);
            mainForm.getSelectedSuperCollectionTypeLabel().setVisible(false);
            mainForm.getEditButton().setEnabled(false);
            mainForm.getDeleteButton().setEnabled(false);
            mainForm.getViewSelectedProgramVersionsButton().setEnabled(false);
            mainForm.getSwitchSelectedProgramSupTypeButton().setEnabled(false);

        }
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

    public void setImages() {
        ImageIcon icon = new ImageIcon("resources/logo.png");
        JLabel imgLabel = mainForm.getImgLabel();
        Image scaledImage = icon.getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        imgLabel.setIcon(scaledIcon);
    }
}