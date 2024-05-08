package com.insebre.project.controller.form;

import com.insebre.project.controller.AppController;
import com.insebre.project.controller.DataController;
import com.insebre.project.view.ProgramVersionsForm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ProgramVersionsFormController {

    private final ProgramVersionsForm programVersionsForm;
    private final int programIndex;

    public ProgramVersionsFormController(ProgramVersionsForm programVersionsForm, int programIndex) {
        this.programVersionsForm = programVersionsForm;
        this.programIndex = programIndex;
    }

    public void show() {
        String programName = DataController.appData[programIndex].getName();
        JDialog dialog = new JDialog();
        dialog.setTitle("Program versions - " + programName);
        dialog.setContentPane(programVersionsForm.getPanel());
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        programVersionsForm.getTable1().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        programVersionsForm.getTable1().getSelectionModel().addListSelectionListener(e -> {
            if (programVersionsForm.getTable1().getSelectedRow() != -1) {
                int selectedIndex = programVersionsForm.getTable1().getSelectedRow();
                TableModel model = programVersionsForm.getTable1().getModel();
                String version = model.getValueAt(selectedIndex, 0).toString();
                String releaseDate = model.getValueAt(selectedIndex, 1).toString();
                String commits = model.getValueAt(selectedIndex, 2).toString();
                programVersionsForm.getVersionInput().setText(version);
                programVersionsForm.getReleaseDateInput().setText(releaseDate);
                programVersionsForm.getCommitsInput().setText(commits);
            }
        });

        programVersionsForm.getAddNewVersionButton().addActionListener(e -> {
            String version = programVersionsForm.getVersionInput().getText();
            String releaseDate = programVersionsForm.getReleaseDateInput().getText();
            String commits = programVersionsForm.getCommitsInput().getText();
            if (version.isEmpty() || releaseDate.isEmpty() || commits.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // add new version
                DataController.addProgramVersion(programIndex, version, releaseDate, commits);
                setTableData(DataController.getParsedProgramVersions(programIndex));
                AppController.refreshSelectedProgramInformation();
            }
        });

        programVersionsForm.getSaveVersionButton().addActionListener(e -> {
            if (programVersionsForm.getTable1().getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "Please select a version to edit.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int selectedIndex = programVersionsForm.getTable1().getSelectedRow();
            String version = programVersionsForm.getVersionInput().getText();
            String releaseDate = programVersionsForm.getReleaseDateInput().getText();
            String commits = programVersionsForm.getCommitsInput().getText();
            if (version.isEmpty() || releaseDate.isEmpty() || commits.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                DataController.editProgramVersion(programIndex, selectedIndex, version, releaseDate, commits);
                setTableData(DataController.getParsedProgramVersions(programIndex));
            }
        });

        programVersionsForm.getDeleteVersionButton().addActionListener(e -> {
            if (programVersionsForm.getTable1().getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "Please select a version to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int selectedIndex = programVersionsForm.getTable1().getSelectedRow();
            DataController.deleteProgramVersion(programIndex, selectedIndex);
            setTableData(DataController.getParsedProgramVersions(programIndex));
            AppController.refreshSelectedProgramInformation();
        });

        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                AppController.showingViewProgramVersionForm = false;
                AppController.refreshMainForm();
            }
        });

    }

    public void setTableData(Object[][] data) {
        DefaultTableModel model = new DefaultTableModel(data, new String[]{"Version", "Release Date", "Commits"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        programVersionsForm.getTable1().setModel(model);
    };

}
