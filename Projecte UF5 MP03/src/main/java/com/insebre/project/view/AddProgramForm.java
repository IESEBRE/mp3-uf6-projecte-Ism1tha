package com.insebre.project.view;

import javax.swing.*;

public class AddProgramForm extends JFrame{
    private JPanel panel;
    private JTextField descriptionInput;
    private JTextField nameInput;
    private JTextField languageInput;
    private JButton submitButton;
    private JTextField categoryInput;
    private JPasswordField passwordInput;
    private JLabel nameLabel;
    private JLabel descriptionLabel;
    private JLabel categoryLabel;
    private JLabel languageLabel;
    private JLabel passwordlabel;
    private JTextField releaseDateInput;
    private JLabel releaseDateLabel;

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

    public JTextField getReleaseDateInput() { return releaseDateInput; }

    public JPasswordField getPasswordInput() {
        return passwordInput;
    }

    public JButton getSubmitButton() {
        return submitButton;
    }

}
