package com.insebre.project.view;

import javax.swing.*;

public class EditProgramForm {
    private JPanel panel;
    private JTextField descriptionInput;
    private JTextField nameInput;
    private JButton submitButton;
    private JPasswordField passwordInput;
    private JLabel nameLabel;
    private JLabel descriptionLabel;
    private JLabel categoryLabel;
    private JLabel languageLabel;
    private JLabel passwordlabel;
    private JTextField releaseDateInput;
    private JLabel releaseDateLabel;
    private JCheckBox paidSoftwareCheckbox;
    private JComboBox<String> languageComboBox;
    private JComboBox<String> categoryComboBox;

    public JPanel getPanel() {
        return panel;
    }

    public JTextField getNameInput() { return nameInput; }

    public JTextField getDescriptionInput() { return descriptionInput; }

    public JComboBox<String> getCategoryComboBox() { return categoryComboBox; }

    public JComboBox<String> getLanguageComboBox() { return languageComboBox; }

    public JTextField getReleaseDateInput() { return releaseDateInput; }

    public JPasswordField getPasswordInput() {
        return passwordInput;
    }

    public JButton getSubmitButton() {
        return submitButton;
    }

    public JCheckBox getPaidCheckBox() { return paidSoftwareCheckbox; }
}
