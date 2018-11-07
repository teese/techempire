package com.github.teese.techempire;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.time.LocalDate;

public class ResearcherPool{

    private ResearcherPool(){}

    static ArrayList<Researcher> fromRandomisation(int numResearchers){
        ArrayList<Researcher> resPool;
        resPool = new ArrayList<>();
        for (int x = 0; x < numResearchers; x++) {
            Researcher r = new Researcher();
            r.setGoogleID("A" + (int) (Math.random() * 10000));
            r.setName(NameGenerator.generateName());
            r.setAffiliation("B" + (int) (Math.random() * 10000));
            r.sethIndex((int) (Math.random() * 50 + 3));
            r.setPurity((float) Math.random());
            r.setAge(Researcher.generateRandomAge());
            r.setQuality(Researcher.generateRandomQuality(r.gethIndex(), r.getAge()));
            resPool.add(r);
        }
        return resPool;
    }
    static ArrayList<Researcher> fromCSV(int numResearchers, String researchersCSV){
        ArrayList<Researcher> resPool;
        resPool = new ArrayList<>();

        //String researchersCSV = "S:\\m_cloud\\Dropbox\\javalearn\\techempire\\research\\MT_endnote_gscholar.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        ArrayList<Integer> randomRowList = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(researchersCSV), "UTF-8"));
            //BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFile));

            // get a random selection of rows from the entire length of the CSV
            int csvLength = 0;
            while ((line = bufferedReader.readLine()) != null) {csvLength++;}
            System.out.println("Your input csv file contains " + csvLength + " researchers.\n" +
                    "Researchers in your researcher pool : \n");

            while (randomRowList.size() < numResearchers){
                int randomRow = (int) (Math.random() * csvLength + 1);
                randomRowList.add(randomRow);
            }
        } catch (IOException e) {
            printError();
            System.exit(1);
        }

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(researchersCSV), "UTF-8"));
            int n = 0;
            while ((line = bufferedReader.readLine()) != null) {

                String[] L = line.split(cvsSplitBy);

                if (randomRowList.contains(n)) {
                    Researcher r = new Researcher();
                    r.setGoogleID(L[0]);
                    r.setName(L[1]);
                    r.setAffiliation(L[2]);
                    r.sethIndex(Integer.parseInt(L[3]));

                    r.setAgeFromFirstCitation(Integer.parseInt(L[4]));
                    r.setCitedBy(Integer.parseInt(L[5]));
                    r.setInterests(L[6]);
                    r.setPurity((float) Math.random());
                    r.setQuality(Researcher.generateRandomQuality(r.gethIndex(), r.getAge()));
                    resPool.add(r);
                    System.out.println(String.format("%s (%s)", r.getName(), r.getAffiliation()));
                }
                n++;
            }
        } catch (IOException e) {
            printError();
            System.exit(1);
        }
        return resPool;
    }
    public static boolean contains(final int[] arr, final int key) {
        return Arrays.stream(arr).anyMatch(i -> i == key);
    }
    static void printError(){
        System.out.println("Oops, there seems to be a problem with your input researchers-CSV file.");
        System.out.println("In Windows, use a double-backslash in filenames.");
        System.out.println(Main.exampleCommand);
    }
}
/*
    ArrayList<Researcher> rArr = new ArrayList<Researcher>();
    for (int x = 0; x < numResearchers; x++) {
        Researcher r = new Researcher();
        rArr.add(r);
    }
}
*/
class Researcher{
    private String name;
    private int age;
    private int hIndex;
    private float purity;
    private String googleID;
    private String affiliation;
    private String interests;
    private int citedBy;
    private float quality;
    static int generateRandomAge(){
        int age = 0;
        // replace age with a number above 20, centered at 47, standard deviation of 20
        while (age < 25) {
            Random r = new Random();
            age = (int) (r.nextGaussian()*20+47);
        }
        return age;
    }
    static float generateRandomQuality(int hIndex, int age){
        float quality = (float) (Math.pow(hIndex, 2) / (age - 27));
        return quality;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setAgeFromFirstCitation(int firstCitation){
        int currentYear = LocalDate.now().getYear();
        this.age = (int) currentYear - firstCitation + 27;
    }


    public int gethIndex() {
        return hIndex;
    }

    public void sethIndex(int hIndex) {
        this.hIndex = hIndex;
    }

    public float getPurity() {
        return purity;
    }

    public void setPurity(float purity) {
        this.purity = purity;
    }

    public String getGoogleID() {
        return googleID;
    }

    public void setGoogleID(String googleID) {
        this.googleID = googleID;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public int getCitedBy() {
        return citedBy;
    }

    public void setCitedBy(int citedBy) {
        this.citedBy = citedBy;
    }

    public float getQuality() {
        return quality;
    }

    public void setQuality(float quality) {
        this.quality = quality;
    }
}

enum ResearchField {
    Biol,
    Phys,
    Chem
}
