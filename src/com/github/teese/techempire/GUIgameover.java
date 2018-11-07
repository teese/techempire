package com.github.teese.techempire;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIgameover implements ActionListener, Runnable{
    private JFrame frame;
    private JPanel panel;
    private JLabel label;
    private String labelText;

    GUIgameover(int numTurns, int[] pointsSummary) {
        String a = String.format("You succeeded in %d turns!<br>Awesome Job!", numTurns);
        String finalPoints = String.format("&emsp Biology : %d <br>&emsp Physics : %d<br>&emsp Chemistry : %d", pointsSummary[0], pointsSummary[1], pointsSummary[2]);
        labelText = String.format("<html><center><br><br> %s</center><br><b>Final Points :</b><br>%s", a, finalPoints);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.setVisible(false);
        System.exit(0);
    }

    @Override
    public void run() {
        Color c = new Color(1,150,173);
        GUIchoices.setNimbusLookAndFeel();
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        panel = new JPanel();
        label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 40));
        panel.add(label);
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setSize(600,600);
        frame.setVisible(true);
    }
//    public void setNimbusLookAndFeel(Color c) {
//        try {
//            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    UIManager.setLookAndFeel(info.getClassName());
//                    LookAndFeel lnf = UIManager.getLookAndFeel().getClass().newInstance();
//                    UIDefaults uiDefaults = lnf.getDefaults();
//                    uiDefaults.put("nimbusBase", c);
//                    UIManager.getLookAndFeel().uninitialize();
//                    UIManager.setLookAndFeel(lnf);
//                    break;
//                }
//            }
//        } catch (Exception e) {
//            // If Nimbus is not available, you can set the GUIchoices to another look and feel.
//        }
//    }
}
