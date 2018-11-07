package com.github.teese.techempire;

import java.util.ArrayList;
import java.util.Arrays;

class Main {
    public static String exampleCommand = "As arguments, please include the number of points required to win, and the path to the researcher CSV file.\ne.g.\n java -jar techempire.jar -p 1000 -r D:\\\\github_projects\\\\techempire\\\\docs\\\\researchers.csv";

    public static void main (String[] args) {
        Main main = new Main();
        main.start(args);
    }

    void start(String[] args){
        long endTime;
        long timeElapsed;
        ArrayList<Researcher> rp;
        ArrayList<GrantApplication> choices;
        int MyChoice;
        String researchersCSV = "";
        int pointsToWin = 0;

        if (args.length != 0) {
            if (args[0].equals("-p") && args[2].equals("-r")){
                pointsToWin = Integer.parseInt(args[1]);
                researchersCSV = args[3];
            }
        } else {
            System.out.println("Error. " + exampleCommand);
            System.exit(1);
        }
        final long startTime = System.currentTimeMillis();
        Points points = new Points(pointsToWin);
        rp = ResearcherPool.fromCSV(40, researchersCSV);
        System.out.println("rp length " + rp.size());
        int numStartups = 0;
        int numTurns = 1;
        boolean gameIsLive = true;
        GUIchoices gui;
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
         * Starting GUIchoices in a separate thread is not fully necessary at the moment.
         * However seems to run more consistently faster. (400-500 ms until choices, rather than 400-1000)
         * Later the multithreading might be more useful
         */
        gui = new GUIchoices(latch);
        guiThread = new Thread(gui);
        guiThread.start();
        // current thread will wait until the guiThread is finished.
        // after this, GUIchoices is no longer running in a separate thread
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

            //endTime = System.currentTimeMillis();
            //timeElapsed = endTime - startTime;
            //System.out.println("time elapsed before choices = " + timeElapsed);

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
            // SHOULD BE REPLACED WITH A GUIchoices...
            GrantApplication chosenGA = choices.get(MyChoice);
            Result result = chosenGA.getResult();
            System.out.println("Points added : Biol " + result.getBiol() + " Phys " + result.getPhys() + " Chem " + result.getChem());
            // Add result to the user final points
            points.add(result);

            System.out.println("Total points : Biol " + points.getBiol() + " Phys " + points.getPhys() + " Chem " + points.getChem());

            if (points.hasAchievedGoal()){
                gameIsLive = false;
                // hide the gui frame
                // should be replaced with a "YOU'RE A WINNER" screen later.
                gui.hideFrame();

                String pointsSummary = Arrays.toString(points.getPointsArray());

                GUIgameover gameOverGUI = new GUIgameover(numTurns, points.getPointsArray());
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
        System.out.println("\nCONGRATULATIONS! YOU WON!");
        System.out.println("Final points : Biol " + points.getBiol() + " Phys " + points.getPhys() + " Chem " + points.getChem());
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
    private int biol;
    private int phys;
    private int chem;
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
        biol += r.getBiol();
        phys += r.getPhys();
        chem += r.getChem();
        if (biol > goal || phys > goal || chem > goal){
            achievedGoal = true;
        }
    }
    int[] getPointsArray(){
        int[] ar = new int[3];
        ar[0] = biol;
        ar[1] = phys;
        ar[2] = chem;
        return ar;
    }
    boolean hasAchievedGoal(){
        return achievedGoal;
    }

    public int getBiol() {
        return biol;
    }

    public void setBiol(int biol) {
        this.biol = biol;
    }

    public int getPhys() {
        return phys;
    }

    public void setPhys(int phys) {
        this.phys = phys;
    }

    public int getChem() {
        return chem;
    }

    public void setChem(int chem) {
        this.chem = chem;
    }

    public boolean isAchievedGoal() {
        return achievedGoal;
    }

    public void setAchievedGoal(boolean achievedGoal) {
        this.achievedGoal = achievedGoal;
    }

    public Result getR() {
        return r;
    }

    public void setR(Result r) {
        this.r = r;
    }
}
