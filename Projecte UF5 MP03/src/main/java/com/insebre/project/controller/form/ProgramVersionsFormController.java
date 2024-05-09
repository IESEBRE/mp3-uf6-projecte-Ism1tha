package com.insebre.project.controller.form;

import com.insebre.project.controller.AppController;
import com.insebre.project.controller.DataController;
import com.insebre.project.controller.ExceptionController;
import com.insebre.project.exception.EmptyFieldFoundException;
import com.insebre.project.exception.InvalidVersionNameException;
import com.insebre.project.model.Version;
import com.insebre.project.view.ProgramVersionsForm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
            try {
                String version = programVersionsForm.getVersionInput().getText();
                String releaseDate = programVersionsForm.getReleaseDateInput().getText();
                String commits = programVersionsForm.getCommitsInput().getText();
                if (version.isEmpty()) throw new EmptyFieldFoundException("Version");
                if (releaseDate.isEmpty()) throw new EmptyFieldFoundException("Release Date");
                if (commits.isEmpty()) throw new EmptyFieldFoundException("Commits");
                if (AppController.validateVersion(version)) throw new InvalidVersionNameException("Invalid version name (x.x.x)");
                if (AppController.validateReleaseDate(releaseDate)) throw new Exception("Invalid release date format (yyyy-mm-dd)");

                Version lastVersion = DataController.appData[programIndex].getVersions().get(DataController.appData[programIndex].getVersions().size() - 1);
                if(compareVersions(version, lastVersion.getVersion()) < 1) {
                    throw new IllegalArgumentException("Version must be greater than the last version");
                }

                LocalDate newReleaseDate = LocalDate.parse(releaseDate, DateTimeFormatter.ISO_LOCAL_DATE);
                LocalDate lastReleaseDate = LocalDate.parse(lastVersion.getDate(), DateTimeFormatter.ISO_LOCAL_DATE);

                if (lastReleaseDate.isAfter(newReleaseDate)) {
                    throw new IllegalArgumentException("Release date must be after the release date of the last version");
                }

                DataController.addProgramVersion(programIndex, version, releaseDate, commits);
                setTableData(DataController.getParsedProgramVersions(programIndex));
                AppController.refreshSelectedProgramInformation();

            } catch (Exception ex) {
                ExceptionController.handleException(ex);
            }
        });

        programVersionsForm.getSaveVersionButton().addActionListener(e -> {
            try {
                if (programVersionsForm.getTable1().getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a version to edit.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int selectedIndex = programVersionsForm.getTable1().getSelectedRow();
                String version = programVersionsForm.getVersionInput().getText();
                String releaseDate = programVersionsForm.getReleaseDateInput().getText();
                String commits = programVersionsForm.getCommitsInput().getText();
                if (version.isEmpty()) throw new EmptyFieldFoundException("Version");
                if (releaseDate.isEmpty()) throw new EmptyFieldFoundException("Release Date");
                if (commits.isEmpty()) throw new EmptyFieldFoundException("Commits");
                if (AppController.validateVersion(version)) throw new InvalidVersionNameException("Invalid version name (x.x.x)");
                if (AppController.validateReleaseDate(releaseDate)) throw new Exception("Invalid release date format (yyyy-mm-dd) or future date");

                Version lastVersion = DataController.appData[programIndex].getVersions().get(DataController.appData[programIndex].getVersions().size() - 1);
                System.out.println(compareVersions(version, lastVersion.getVersion()));

                LocalDate newReleaseDate = LocalDate.parse(releaseDate, DateTimeFormatter.ISO_LOCAL_DATE);
                LocalDate lastReleaseDate = LocalDate.parse(lastVersion.getDate(), DateTimeFormatter.ISO_LOCAL_DATE);

                if (!newReleaseDate.isAfter(lastReleaseDate)) {
                    throw new IllegalArgumentException("Release date must be after the release date of the last version");
                }

                DataController.editProgramVersion(programIndex, selectedIndex, version, releaseDate, commits);
                setTableData(DataController.getParsedProgramVersions(programIndex));
            } catch (Exception ex) {
                ExceptionController.handleException(ex);
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
    }

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
