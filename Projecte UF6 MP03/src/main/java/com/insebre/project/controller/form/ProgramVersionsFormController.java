package com.insebre.project.controller.form;

import com.insebre.project.controller.AppController;
import com.insebre.project.controller.ExceptionController;
import com.insebre.project.exception.EmptyFieldFoundException;
import com.insebre.project.exception.InvalidVersionNameException;
import com.insebre.project.exception.dao.DAOException;
import com.insebre.project.model.Version;
import com.insebre.project.view.ProgramVersionsForm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Controller for managing program versions within the software manager application.
 *
 * @author Ismael Semmar Galvez
 * @version 1.0
 */
public class ProgramVersionsFormController {

    private final ProgramVersionsForm programVersionsForm;
    private final int programID;

    /**
     * Constructs a ProgramVersionsFormController with the specified ProgramVersionsForm and program index.
     *
     * @param programVersionsForm The ProgramVersionsForm instance to be controlled.
     * @param programID       The index of the program in the data.
     */
    public ProgramVersionsFormController(ProgramVersionsForm programVersionsForm, int programID) {
        this.programVersionsForm = programVersionsForm;
        this.programID = programID;
    }

    /**
     * Displays the program versions form dialog.
     */
    public void show() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Program Versions - " + programID);
        dialog.setContentPane(programVersionsForm.getPanel());
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        programVersionsForm.getTable1().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // ListSelectionListener for table selection
        programVersionsForm.getTable1().getSelectionModel().addListSelectionListener(e -> {
            if (programVersionsForm.getTable1().getSelectedRow() != -1) {
                int selectedIndex = programVersionsForm.getTable1().getSelectedRow();
                TableModel model = programVersionsForm.getTable1().getModel();
                String version = model.getValueAt(selectedIndex, 1).toString();
                String releaseDate = model.getValueAt(selectedIndex, 2).toString();
                String commits = model.getValueAt(selectedIndex, 3).toString();
                programVersionsForm.getVersionInput().setText(version);
                programVersionsForm.getReleaseDateInput().setText(releaseDate);
                programVersionsForm.getCommitsInput().setText(commits);
                programVersionsForm.getVersionInput().setEnabled(false);
                programVersionsForm.getReleaseDateInput().setEnabled(false);
            }
            else {
                programVersionsForm.getVersionInput().setText("");
                programVersionsForm.getReleaseDateInput().setText("");
                programVersionsForm.getCommitsInput().setText("");
                programVersionsForm.getVersionInput().setEnabled(true);
                programVersionsForm.getReleaseDateInput().setEnabled(true);
            }
        });

        programVersionsForm.getUnselectVersionButton().addActionListener(e -> {
            programVersionsForm.getTable1().clearSelection();
        });

        // Add New Version Button ActionListener
        programVersionsForm.getAddNewVersionButton().addActionListener(e -> {
            try {
                String version = programVersionsForm.getVersionInput().getText();
                String releaseDate = programVersionsForm.getReleaseDateInput().getText();
                String commits = programVersionsForm.getCommitsInput().getText();
                if (version.isEmpty()) throw new EmptyFieldFoundException("Version");
                if (releaseDate.isEmpty()) throw new EmptyFieldFoundException("Release Date");
                if (commits.isEmpty()) throw new EmptyFieldFoundException("Commits");
                if (AppController.validateVersion(version)) throw new InvalidVersionNameException("Invalid version name (x.x.x)");
                if (AppController.validateReleaseDate(releaseDate))
                    throw new Exception("Invalid release date format (yyyy-mm-dd)");
                List<Version> programVersions = AppController.versionDAO.getByProgramID(programID);
                Version lastVersion;
                if(programVersions.isEmpty()) lastVersion = new Version(-1, programID, "0.0.0", "2021-01-01", "Initial version");
                else lastVersion = programVersions.get(programVersions.size() - 1);

                if (compareVersions(version, lastVersion.getVersion()) <= 0) {
                    throw new IllegalArgumentException("Version must be greater than the last version");
                }
                LocalDate newReleaseDate = LocalDate.parse(releaseDate, DateTimeFormatter.ISO_LOCAL_DATE);
                LocalDate lastReleaseDate = LocalDate.parse(lastVersion.getDate(), DateTimeFormatter.ISO_LOCAL_DATE);
                if (lastReleaseDate.isAfter(newReleaseDate)) {
                    throw new IllegalArgumentException("Release date must be after the release date of the last version");
                }
                Version newVersion = new Version(-1, programID, version, releaseDate, commits);
                AppController.versionDAO.create(newVersion);
                refreshTableData();
            } catch (Exception ex) {
                ExceptionController.handleException(ex);
            }
        });

        // Save Version Button ActionListener
        programVersionsForm.getSaveVersionButton().addActionListener(e -> {
            try {
                int rowIndex = programVersionsForm.getTable1().getSelectedRow();
                if (rowIndex == -1) throw new IllegalArgumentException("Please select a version to save changes");
                int versionID = Integer.parseInt(programVersionsForm.getTable1().getValueAt(rowIndex, 0).toString());
                String version = programVersionsForm.getVersionInput().getText();
                String releaseDate = programVersionsForm.getReleaseDateInput().getText();
                String commits = programVersionsForm.getCommitsInput().getText();
                if (version.isEmpty()) throw new EmptyFieldFoundException("Version");
                if (releaseDate.isEmpty()) throw new EmptyFieldFoundException("Release Date");
                if (commits.isEmpty()) throw new EmptyFieldFoundException("Commits");
                if (AppController.validateVersion(version)) throw new InvalidVersionNameException("Invalid version name (x.x.x)");
                if (AppController.validateReleaseDate(releaseDate))
                    throw new Exception("Invalid release date format (yyyy-mm-dd)");
                Version editedVersion = new Version(versionID, programID, version, releaseDate, commits);
                AppController.versionDAO.update(editedVersion);
                refreshTableData();
            } catch (Exception ex) {
                ExceptionController.handleException(ex);
            }
        });

        // Delete Version Button ActionListener
        programVersionsForm.getDeleteVersionButton().addActionListener(e -> {
            try {
                int rowIndex = programVersionsForm.getTable1().getSelectedRow();
                if (rowIndex == -1) throw new IllegalArgumentException("Please select a version to save changes");
                int versionID = Integer.parseInt(programVersionsForm.getTable1().getValueAt(rowIndex, 0).toString());
                AppController.versionDAO.delete(versionID);
                refreshTableData();
            } catch (DAOException ex) {
                throw new RuntimeException(ex);
            }
        });

        // WindowListener to handle dialog closing
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                AppController.showingViewProgramVersionForm = false;
                try {
                    AppController.reloadMainFormTable();
                } catch (DAOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    /**
     * Sets the table data in the program versions form.
     *
     * @param versions The data to set in the table.
     */
    public void setTableData(List<Version> versions) {
        Object[][] data = new Object[versions.size()][4];
        for (int i = 0; i < versions.size(); i++) {
            Version version = versions.get(i);
            data[i][0] = version.getId();
            data[i][1] = version.getVersion();
            data[i][2] = version.getDate();
            data[i][3] = version.getCommits();
        }
        DefaultTableModel model = new DefaultTableModel(data, new String[]{"ID", "Version", "Release Date", "Commits"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        programVersionsForm.getTable1().setModel(model);
    }

    /**
     * Refreshes the table data in the program versions form.
     */
    public void refreshTableData() {
        try {
            List<Version> versions = AppController.versionDAO.getByProgramID(programID);
            setTableData(versions);
        } catch (DAOException ex) {
            ExceptionController.handleException(ex);
        }
    }

    /**
     * Compares two version strings in the format x.x.x.
     *
     * @param version1 The first version string.
     * @param version2 The second version string.
     * @return An integer value representing the comparison result:
     *         -1 if version1 < version2,
     *         0 if version1 == version2,
     *         1 if version1 > version2.
     */
    private int compareVersions(String version1, String version2) {
        String[] parts1 = version1.split("\\.");
        String[] parts2 = version2.split("\\.");

        // Compare major version
        int major1 = Integer.parseInt(parts1[0]);
        int major2 = Integer.parseInt(parts2[0]);
        if (major1 != major2) {
            return Integer.compare(major1, major2);
        }

        // Compare minor version
        int minor1 = Integer.parseInt(parts1[1]);
        int minor2 = Integer.parseInt(parts2[1]);
        if (minor1 != minor2) {
            return Integer.compare(minor1, minor2);
        }

        // Compare patch version
        int patch1 = Integer.parseInt(parts1[2]);
        int patch2 = Integer.parseInt(parts2[2]);
        return Integer.compare(patch1, patch2);
    }
}
