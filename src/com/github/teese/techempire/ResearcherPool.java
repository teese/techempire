package com.github.teese.techempire;

import java.util.ArrayList;
import java.util.Random;

public class ResearcherPool{
    private int startNum = 20;
    private ArrayList<Researcher> rArr = new ArrayList<Researcher>();
    ResearcherPool() {
        for (int x = 0; x < startNum; x++) {
            Researcher r = new Researcher();
            rArr.add(r);
        }
    }
    int getSize(){
        return rArr.size();
    }
    Researcher getResearcher(int index){
        return rArr.get(index);
    }
}
/*
    ArrayList<Researcher> rArr = new ArrayList<Researcher>();
    for (int x = 0; x < startNum; x++) {
        Researcher r = new Researcher();
        rArr.add(r);
    }
}
*/
class Researcher{
    private String name;
    private int age;
    private int hIndex;
    private float histPurity;
    Researcher(){
        //NameGenerator n = new NameGenerator();
        name = NameGenerator.generateName();
        hIndex = (int) (Math.random() * 50) + 3;
        histPurity = (float) Math.random();
        age = generateAge();
    }
    private int generateAge(){
        int age = 0;
        // replace age with a number above 20, centered at 47, standard deviation of 20
        while (age < 25) {
            Random r = new Random();
            age = (int) (r.nextGaussian()*20+47);
        }
        return age;
    }
    float getQuality(){
        float quality = (float) (Math.pow(hIndex, 2) / (age - 24));
        return quality;
    }
    int getHindex(){
        return hIndex;
    }
    String getName(){
        return name;
    }
    float getHistPurity(){
        return histPurity;
    }
    int getAge(){
        return age;
    }
}

enum ResearchField {
    Biol,
    Phys,
    Chem
}
