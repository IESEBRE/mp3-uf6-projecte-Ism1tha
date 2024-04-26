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
    private JLabel Label1;

    public MainForm() {
        editSelectedProgramButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (table1.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a program to edit!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                PasswordPromptForm passwordPromptForm = new PasswordPromptForm();
                passwordPromptForm.setVisible(true);
                passwordPromptForm.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        System.out.println("Password Prompt Closed!");
                        if (passwordPromptForm.isPasswordSubmittedSuccessfully()) {
                            JOptionPane.showMessageDialog(null, "Password Submitted Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Password Submission Failed!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
            }
        });
    }

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
