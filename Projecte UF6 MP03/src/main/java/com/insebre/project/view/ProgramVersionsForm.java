package com.insebre.project.view;

import javax.swing.*;

public class ProgramVersionsForm {

    private JPanel panel;
    private JTable table1;
    private JTextField versionInput;
    private JButton addNewVersionButton;
    private JTextField commitInput;
    private JTextField dateInput;
    private JButton saveVersionButton;
    private JButton deleteVersionButton;
    private JButton unselectVersionButton;

    public ProgramVersionsForm() {
    }

    public JPanel getPanel() {
        return panel;
    }

    public JTable getTable1() {
        return table1;
    }

    public JTextField getVersionInput() {
        return versionInput;
    }

    public JTextField getCommitsInput() {
        return commitInput;
    }

    public JTextField getReleaseDateInput() {
        return dateInput;
    }

    public AbstractButton getUnselectVersionButton() { return unselectVersionButton; }

    public AbstractButton getAddNewVersionButton() {
        return addNewVersionButton;
    }

    public AbstractButton getSaveVersionButton() {
        return saveVersionButton;
    }

    public AbstractButton getDeleteVersionButton() {
        return deleteVersionButton;
    }
}
