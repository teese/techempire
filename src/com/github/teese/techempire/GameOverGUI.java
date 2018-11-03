package com.github.teese.techempire;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOverGUI implements ActionListener, Runnable{
    private JFrame frame;
    private JPanel panel;
    private JLabel label;
    private String labelText;

    GameOverGUI(int numTurns, String pointsSummary) {
        String a = "You succeeded! Awesome Job!";
        labelText = String.format("<html><center><br><br><br><br> %s<br>Number of turns :  %d<br>Final Points :  %s", a, numTurns, pointsSummary);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.setVisible(false);
        System.exit(0);
    }

    @Override
    public void run() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        panel = new JPanel();
        label = new JLabel(labelText);
        panel.add(label);
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setSize(600,600);
        frame.setVisible(true);
    }
}
