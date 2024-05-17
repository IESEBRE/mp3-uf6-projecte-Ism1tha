package com.insebre.project.controller.form;

import com.insebre.project.controller.AppController;
import com.insebre.project.controller.ExceptionController;
import com.insebre.project.controller.PasswordController;
import com.insebre.project.exception.InvalidPasswordException;
import com.insebre.project.exception.dao.DAOException;
import com.insebre.project.model.Program;
import com.insebre.project.modeldao.VersionDAO;
import com.insebre.project.view.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.List;

/**
 * Controller for the main form of the software manager application.
 *
 * @author Ismael Semmar Galvez
 * @version 1.0
 */
public class MainFormController {

    private final MainForm mainForm;

    /**
     * Constructor for MainFormController.
     *
     * @param mainForm The main form instance to be controlled.
     */
    public MainFormController(MainForm mainForm) {
        this.mainForm = mainForm;

        // Add Button ActionListener
        this.mainForm.getAddButton().addActionListener(e -> {
            try {
                if (AppController.showingAddProgramForm) throw new Exception("Add Program Form is already open!");
                AddProgramForm addProgramForm = new AddProgramForm();
                AddProgramFormController addProgramFormController = new AddProgramFormController(addProgramForm);
                addProgramFormController.show();
                AppController.showingAddProgramForm = true;
            } catch (Exception ex) {
                ExceptionController.handleException(ex);
            }
        });

        // Edit Button ActionListener
        this.mainForm.getEditButton().addActionListener(e -> {
            if (mainForm.getTable().getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "Please select a program to edit!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int rowIndex = mainForm.getTable().getSelectedRow();
            Object idObject = mainForm.getTable().getValueAt(rowIndex, mainForm.getTable().getColumnModel().getColumnIndex("ID"));
            int programID = Integer.parseInt(idObject.toString());
            PasswordPromptForm passwordPromptForm = new PasswordPromptForm();
            passwordPromptForm.setVisible(true);
            passwordPromptForm.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    try {
                        if (passwordPromptForm.isPasswordSubmittedSuccessfully()) throw new InvalidPasswordException();
                        else {
                            if (PasswordController.checkProgramPassword(programID, passwordPromptForm.getSubmittedPassword()))
                                throw new InvalidPasswordException();
                            if (AppController.showingEditProgramForm) throw new Exception("Edit Program Form is already open!");
                            AppController.showingEditProgramForm = true;
                            EditProgramForm editProgramForm = new EditProgramForm();
                            EditProgramFormController editProgramFormController = new EditProgramFormController(editProgramForm, programID);
                            editProgramFormController.show();
                        }
                    } catch (Exception ex) {
                        ExceptionController.handleException(ex);
                    }
                }
            });
        });

        // Delete Button ActionListener
        this.mainForm.getDeleteButton().addActionListener(e -> {
            if (mainForm.getTable().getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "Please select a program to delete!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int rowIndex = mainForm.getTable().getSelectedRow();
            Object idObject = mainForm.getTable().getValueAt(rowIndex, mainForm.getTable().getColumnModel().getColumnIndex("ID"));
            int programID = Integer.parseInt(idObject.toString());
            PasswordPromptForm passwordPromptForm = new PasswordPromptForm();
            passwordPromptForm.setVisible(true);
            passwordPromptForm.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    try {
                        if (passwordPromptForm.isPasswordSubmittedSuccessfully()) throw new InvalidPasswordException();
                        else {
                            int selectedProgramIndex = mainForm.getTable().getSelectedRow();
                            if (PasswordController.checkProgramPassword(programID, passwordPromptForm.getSubmittedPassword()))
                                throw new InvalidPasswordException();

                            AppController.programDAO.delete(programID);
                            AppController.reloadMainFormTable();
                        }
                    } catch (Exception ex) {
                        ExceptionController.handleException(ex);
                    }
                }
            });
        });

        // View Selected Program Versions Button ActionListener
        this.mainForm.getViewSelectedProgramVersionsButton().addActionListener(e -> {
            if (mainForm.getTable().getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "Please select a program to view its versions!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int rowIndex = mainForm.getTable().getSelectedRow();
            Object idObject = mainForm.getTable().getValueAt(rowIndex, mainForm.getTable().getColumnModel().getColumnIndex("ID"));
            int programID = Integer.parseInt(idObject.toString());
            PasswordPromptForm passwordPromptForm = new PasswordPromptForm();
            passwordPromptForm.setVisible(true);
            passwordPromptForm.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    try {
                        if (passwordPromptForm.isPasswordSubmittedSuccessfully()) throw new InvalidPasswordException();
                        else {
                            int selectedProgramIndex = mainForm.getTable().getSelectedRow();
                            if (PasswordController.checkProgramPassword(programID, passwordPromptForm.getSubmittedPassword()))
                                throw new InvalidPasswordException();
                            if (AppController.showingViewProgramVersionForm) throw new Exception("View Program Versions Form is already open!");
                            ProgramVersionsForm programVersionsForm = new ProgramVersionsForm();
                            ProgramVersionsFormController programVersionsFormController = new ProgramVersionsFormController(programVersionsForm, programID);
                            programVersionsFormController.setTableData(AppController.versionDAO.getByProgramID(programID));
                            programVersionsFormController.show();
                            AppController.showingViewProgramVersionForm = true;
                        }
                    } catch (Exception ex) {
                        ExceptionController.handleException(ex);
                    }
                }
            });
        });

        this.mainForm.getTable().getSelectionModel().addListSelectionListener(e -> updateAppInformation());
    }

    /**
     * Displays the main form.
     */
    public void show() {
        JFrame frame = new JFrame("Software Manager - Ismael SG");
        frame.setContentPane(mainForm.getPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        mainForm.getTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    /**
     * Updates information on the main form based on the selected row in the table.
     */
    public void updateAppInformation() {
        if (mainForm.getTable().getSelectedRow() != -1) {
            int selectedIndex = mainForm.getTable().getSelectedRow();
            mainForm.getEditButton().setEnabled(true);
            mainForm.getDeleteButton().setEnabled(true);
            mainForm.getViewSelectedProgramVersionsButton().setEnabled(true);
        } else {
            mainForm.getEditButton().setEnabled(false);
            mainForm.getDeleteButton().setEnabled(false);
            mainForm.getViewSelectedProgramVersionsButton().setEnabled(false);
        }
    }

    /**
     * Sets the table data for the main form.
     *
     * @param programs The list of programs to display in the table
     * @throws DAOException if there is an error retrieving the data
     */
    public void setTableData(List<Program> programs) throws DAOException {
        Object[][] data = new Object[programs.size()][8];
        for (int i = 0; i < programs.size(); i++) {
            Program program = programs.get(i);
            data[i][0] = program.getId();
            data[i][1] = program.getName();
            data[i][2] = program.getDescription();
            data[i][3] = program.getCategory();
            data[i][4] = program.getLanguage();
            data[i][5] = program.getVersion();
            data[i][6] = program.getReleaseDate();
            data[i][7] = program.isPaid();
        }

        String[] columnNames = {"ID", "Name", "Description", "Category", "Language", "Version", "Release Date", "Paid"};
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Assuming mainForm.getTable() returns a JTable
        mainForm.getTable().setModel(model);
    }


    /**
     * Sets images for the main form.
     */
    public void setImages() {
        ImageIcon icon = new ImageIcon("resources/images/logo.png");
        JLabel imgLabel = mainForm.getImgLabel();
        Image scaledImage = icon.getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        imgLabel.setIcon(scaledIcon);
    }
}
