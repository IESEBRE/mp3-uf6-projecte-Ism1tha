package com.insebre.project.view;

import javax.swing.*;

public class AddProgramForm {
    private JPanel panel;
    private JTextField descriptionInput;
    private JTextField nameInput;
    private JTextField languageInput;
    private JTextField versionInput;
    private JButton submitButton;
    private JTextField categoryInput;
    private JPasswordField passwordInput;
    private JLabel nameLabel;
    private JLabel descriptionLabel;
    private JLabel categoryLabel;
    private JLabel languageLabel;
    private JLabel versionLabel;
    private JLabel passwordlabel;

    public AddProgramForm() {
        JFrame frame = new JFrame("Add Program");
        frame.setContentPane(getPanel());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    public JPanel getPanel() {
        return panel;
    }

    public JTextField getNameInput() {
        return nameInput;
    }

    public JTextField getDescriptionInput() {
        return descriptionInput;
    }

    public JTextField getCategoryInput() {
        return categoryInput;
    }

    public JTextField getLanguageInput() {
        return languageInput;
    }

    public JTextField getVersionInput() {
        return versionInput;
    }

    public JPasswordField getPasswordInput() {
        return passwordInput;
    }

    public JButton getSubmitButton() {
        return submitButton;
    }

    public void show() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
        frame.setVisible(true);
    }

    public void hide() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
        frame.setVisible(false);
    }

    public void close() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
        frame.dispose();
    }
}
