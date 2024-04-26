package com.insebre.project.view;

import javax.swing.*;

public class PasswordPromptForm extends JFrame{

    public boolean isPasswordSubmittedSuccessfully = false;

    private JPanel panel1;
    private JPasswordField passwordField1;
    private JButton accessButton;

    public PasswordPromptForm() {

        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        accessButton.addActionListener(e -> {
            if (String.valueOf(passwordField1.getPassword()).equals("1234")) {
                setPasswordSubmittedSuccessfully(true);
                closeForm();
            } else {
                setPasswordSubmittedSuccessfully(false);
                closeForm();
            }
        });
    }

    private void closeForm() {
        // Perform any cleanup actions before closing the form
        dispose(); // Close the form

    }

    private void setPasswordSubmittedSuccessfully(boolean submittedSuccessfully) {
        this.isPasswordSubmittedSuccessfully = submittedSuccessfully;
    }

    public boolean isPasswordSubmittedSuccessfully() {
        return isPasswordSubmittedSuccessfully;
    }

}
