package com.insebre.project.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Form1 {
    private JTable table1;
    private JPanel panel1;
    private JButton deleteSelectedProgramButton;
    private JButton editSelectedProgramButton;
    private JButton addNewProgramButton;
    private JLabel Label1;

    public void show() {
        JFrame frame = new JFrame("Software Manager - Ismael SG");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        throw new IllegalArgumentException("Unknown test exception.");
    }

    public void setTableData(Object[][] data) {
        DefaultTableModel model = new DefaultTableModel(data, new String[]{"Name", "Description", "Category", "Language", "Version", "Release Date"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table1.setModel(model);
    }
}
