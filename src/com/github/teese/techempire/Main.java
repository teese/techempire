package com.github.teese.techempire;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;

class Main {

    public static void main (String[] args) {
        Main main = new Main();
        main.start();
    }

    void start(){
        long endTime;
        long timeElapsed;
        ArrayList<Researcher> rp;
        ArrayList<GrantApplication> choices;
        int MyChoice;

        final long startTime = System.currentTimeMillis();
        int goal = 200;
        Points points = new Points(goal);
        rp = ResearcherPool.fromCSV(20);
        System.out.println("rp length " + rp.size());
        int numStartups = 0;
        int numTurns = 1;
        boolean gameIsLive = true;
        GUI gui;
        Thread guiThread;

        //ResultQueue resultqueue = new ResultQueue();
        //Queue<Result> rq = resultqueue.createResultQueue();

        int numThreadsToWaitFor = 1;
        // necessary to avaid compiler error for the gui.closeFrame()

        // https://stackoverflow.com/questions/6595835/resettable-countdownlatch
        // licence status uncertain
        // see also https://www.youtube.com/watch?v=59oQfkdn5mA
        ResettableCountDownLatch latch = new ResettableCountDownLatch(numThreadsToWaitFor);

        /*
         * Starting GUI in a separate thread is not fully necessary at the moment.
         * However seems to run more consistently faster. (400-500 ms until choices, rather than 400-1000)
         * Later the multithreading might be more useful
         */
        gui = new GUI(latch);
        guiThread = new Thread(gui);
        guiThread.start();
        // current thread will wait until the guiThread is finished.
        // after this, GUI is no longer running in a separate thread
        try {guiThread.join();} catch (InterruptedException ex) {ex.printStackTrace();}

        while (gameIsLive) {

            choices = new ArrayList<GrantApplication>();
            for (int i = 0; i < 4; i++){
                GrantApplication grantapplication = new GrantApplication(rp);
                choices.add(grantapplication);
                //System.out.println(grantapplication.getProposalQuality());
                //grantapplication.printSummary();
            }
            //Toggle toggle = new Toggle();
            //toggle.setToggle(0);

            //https://www.youtube.com/watch?v=59oQfkdn5mA
            //CountDownLatch latch = new CountDownLatch(numThreadsToWaitFor);

            endTime = System.currentTimeMillis();
            timeElapsed = endTime - startTime;
            System.out.println("time elapsed before choices = " + timeElapsed);

            // ask user for choices, and wait until the gui has the user input
            gui.showNewChoices(choices);
            try {
                latch.await();
            } catch (InterruptedException ex){
                ex.printStackTrace();
            }
            // get user choice, which should now be available
            MyChoice = gui.getMyChoice();
            // print your result
            // SHOULD BE REPLACED WITH A GUI...
            GrantApplication chosenGA = choices.get(MyChoice);
            Result result = chosenGA.getResult();
            System.out.println(" result biol " + result.Biol + " result Phys " + result.Phys + " result Chem " + result.Chem);
            // Add result to the user final points
            points.add(result);
            System.out.println(" points " + Arrays.toString(points.getPointsArray()));

            if (points.hasAchievedGoal()){
                gameIsLive = false;
                // hide the gui frame
                // should be replaced with a "YOU'RE A WINNER" screen later.
                gui.hideFrame();

                String pointsSummary = Arrays.toString(points.getPointsArray());

                GameOverGUI gameOverGUI = new GameOverGUI(numTurns, pointsSummary);
                Thread gameOverGUIThread = new Thread(gameOverGUI);
                gameOverGUIThread.start();
            }
            //toggle.setToggle(0);
            latch.reset();
            endTime = System.currentTimeMillis();
            timeElapsed = endTime - startTime;
            //System.out.println("time elapsed = " + timeElapsed);
            numTurns++;
        }
        System.out.println("CONGRATULATIONS! YOU WON!\nFinal points : " + Arrays.toString(points.getPointsArray()));
        System.out.println("Number of turns : " + numTurns);
//        gui.showGameOver();

//        gui.closeFrame();
//        System.exit(0);


        //guiThread.interrupt();
        /*
        //set up empty queue with results from 4 turns, each containing no points
        while (gameIsLive == true) {
            Choice choice = new Choice();
            choice.getGamerChoice(rp);
            Result futureResult = choice.getResult();
            rq.add(futureResult);
            // add results from 4 turns ago to the score
            Result turnResult = rq.pop();
            points.add(turnResult);
            if turnResult.newStartup == true {
                numStartups++;
            }
            if numStartups >= 20{
                gameIsLive = false;
            }
            numTurns++

        }
        */
    }
}

class Points {
    private int Biol;
    private int Phys;
    private int Chem;
    private boolean achievedGoal;
    private Result r;
    private int goal;
    Points (int inputGoal) {
        int Biol = 0;
        int Phys = 0;
        int Chem = 0;
        achievedGoal = false;
        goal = inputGoal;
    }
    void add(Result r){
        Biol += r.Biol;
        Phys += r.Phys;
        Chem += r.Chem;
        if (Biol > goal || Phys > goal || Chem > goal){
            achievedGoal = true;
        }
    }
    int[] getPointsArray(){
        int[] ar = new int[3];
        ar[0] = Biol;
        ar[1] = Phys;
        ar[2] = Chem;
        return ar;
    }
    boolean hasAchievedGoal(){
        return achievedGoal;
    }
}
