package com.insebre.project.view;

import javax.swing.*;

public class MainForm {
    private JTable table1;
    private JPanel panel1;
    private JButton deleteSelectedProgramButton;
    private JButton editSelectedProgramButton;
    private JButton addNewProgramButton;
    private JButton viewSelectedProgramVersionsButton;
    private JLabel imgLabel;

    public MainForm() {
        editSelectedProgramButton.setEnabled(false);
        deleteSelectedProgramButton.setEnabled(false);
        viewSelectedProgramVersionsButton.setEnabled(false);
    }

    public JPanel getPanel() {
        return panel1;
    }

    public JTable getTable() {
        return table1;
    }

    public JButton getEditButton() {
        return editSelectedProgramButton;
    }

    public JButton getAddButton() {
        return addNewProgramButton;
    }

    public JButton getDeleteButton() {
        return deleteSelectedProgramButton;
    }

    public JButton getViewSelectedProgramVersionsButton() { return viewSelectedProgramVersionsButton; }

    public JLabel getImgLabel() { return imgLabel; }
}
