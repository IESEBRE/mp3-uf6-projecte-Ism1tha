package com.insebre.project.view;

import javax.swing.*;

public class PasswordPromptForm extends JFrame{

    private String password;
    private boolean isPasswordSubmittedSuccessfully = false;

    private JPanel panel1;
    private JPasswordField passwordField1;
    private JButton accessButton;

    public PasswordPromptForm() {

        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        accessButton.addActionListener(e -> {
            String password = String.valueOf(passwordField1.getPassword());
            if(password.length() != 4) {
                closeForm();
            }
            else {
                setPasswordSubmittedSuccessfully(true);
                setSubmittedPassword(password);
                closeForm();
            }
        });
    }

    public void closeForm() {
        dispose();
    }

    public void setPasswordSubmittedSuccessfully(boolean submittedSuccessfully) {
        this.isPasswordSubmittedSuccessfully = submittedSuccessfully;

    }

    public boolean isPasswordSubmittedSuccessfully() {
        return !isPasswordSubmittedSuccessfully;
    }

    public void setSubmittedPassword(String password) {
        this.password = password;
    }

    public String getSubmittedPassword() {
        return password;
    }

}
