package com.insebre.project.view;

import javax.swing.*;
import java.awt.*;

public class Form1 {
    private JTable table1;
    private JPanel panel1;

    public void show() {
        JFrame frame = new JFrame("Form1");
        frame.setContentPane(new Form1().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
