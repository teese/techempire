package com.github.teese.techempire;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.JPanel;
import javax.swing.UIDefaults;
import javax.swing.LookAndFeel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.UIManager.*;


public class GUIchoices implements ActionListener, Runnable{
    private JFrame frame;
    private HashMap<String,String> map;
    private int myChoice;
    private ArrayList<GrantApplication> choices;
    private ArrayList<GrantApplication> grantApplicationList;
    private ArrayList<String> buttonTextList;
    private ArrayList<JButton> jButtonList;
    private MyCentrePanel centrePanel;
    private MyNorthPanel northPanel;
    private JLabel northLabel;
    private Timer timer;
    private ResettableCountDownLatch latch;

    GUIchoices(ResettableCountDownLatch latch){
        // get the latch from the main thread or process
        // this can be reset, saving the effort of creating new latches for each choice
        this.latch = latch;
    }

    @Override
    public void run(){
        // create new lists
        // CONVERT TO ARRAYS??
        grantApplicationList = new ArrayList<>();
        buttonTextList = new ArrayList<>();
        jButtonList = new ArrayList<>();
        timer = new Timer(1, this);
        setNimbusLookAndFeel();

        frame = new JFrame();
        // DISPOSE_ON_CLOSE: set frame to close but Java to continue, if X is pressed.This is actually the default
        // EXIT_ON_CLOSE : java program will be terminated. Currently a bug makes window disappear when this is used..
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        map = new HashMap<>();

        northPanel = new MyNorthPanel();
        //JButton northButton = new JButton("Pick a researcher to fund");
        northLabel = new JLabel("Pick a researcher to fund");
        northLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        northPanel.add(northLabel);

        centrePanel = new MyCentrePanel();
        GridLayout gridLayout = new GridLayout(2,2);
        centrePanel.setLayout(gridLayout);

        // add the panels to the frame, set size, etc
        frame.getContentPane().add(BorderLayout.NORTH, northPanel);
        frame.getContentPane().add(BorderLayout.CENTER, centrePanel);
        frame.setSize(600,600);

        for (int i = 0; i < 4; i++) {
            String buttonText = "initial button text";
            buttonTextList.add(buttonText);
            map.put(buttonText, ""+i);
            JButton projButton = new JButton(buttonText);
            projButton.setFont(new Font("Arial", Font.PLAIN, 20));
            //projButton.setBackground(Color.CYAN);
            projButton.addActionListener(this);
            jButtonList.add(projButton);
            centrePanel.add(projButton);
        }
    }

    public static void setNimbusLookAndFeel() {
        Color baseColor = new Color(45, 171, 191);
        Color BlueGreyButtonColor = new Color(171, 215, 215);
        Color controlBackgroundColor = new Color(45, 171, 191);
        try {
            UIManager.put("nimbusBase", baseColor);
            UIManager.put("nimbusBlueGrey", BlueGreyButtonColor);
            UIManager.put("control", controlBackgroundColor);

            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("OOPS, NIMBUS GUI LOOKANDFEEL IS NOT WORKING!");
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
    }

    void showNewChoices(ArrayList<GrantApplication> choices){
        this.choices = choices;
        resetButtonsAndHashmap();
        frame.repaint();
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent event){
        // get the button text (e.g. <html><center> Lukclod<br>hIndex = 38<br>age = 28<br>reviews = [386, 343, 389]
        String s = event.getActionCommand();
        // get the choice number
        String myChoiceStr = map.get(s);
        myChoice = Integer.parseInt(myChoiceStr);
        System.out.println("\nGrant Application by " + choices.get(myChoice).getResearcher().getName() + ".");
        // display a new selection of choices
        resetButtonsAndHashmap();
        frame.repaint();
        // let main program know that user input is now available
        // CountDownLatch minus one, which at the moment will set it to 0
        latch.countDown();
    }

    /*
     * Resets the buttons to show new researcher choices
     * Resets the hashmap (python dictionary), which helps match the button text to the choice number
     */
    private void resetButtonsAndHashmap(){
        map = new HashMap<>();
        for (int i = 0; i < 4; i++) {
            grantApplicationList = new ArrayList<GrantApplication>();
            GrantApplication ga = choices.get(i);
            grantApplicationList.add(ga);
            String buttonText = ga.getButtonText();
            // add buttontext as key, and i as value
            // python equivalent ; {"buttontextblabla01" : "1", "button_text_whatever02" : "2"}
            map.put(buttonText, ""+i);
            JButton jb = jButtonList.get(i);
            jb.setText(buttonText);
        }
    }
    void resetChoices(ArrayList<GrantApplication> choices){
        this.choices = choices;
    }

    void closeFrame(){
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    void hideFrame(){
        frame.setVisible(false);
    }

    synchronized public int getMyChoice(){
        return myChoice;
    }

    class MyCentrePanel extends JPanel{
    }

    class MyNorthPanel extends JPanel{
    }
}



