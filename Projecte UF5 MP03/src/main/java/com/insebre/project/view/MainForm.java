package com.insebre.project.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainForm {
    private JTable table1;
    private JPanel panel1;
    private JButton deleteSelectedProgramButton;
    private JButton editSelectedProgramButton;
    private JButton addNewProgramButton;

    public MainForm() {
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
}
