package com.github.teese.techempire;

import java.util.LinkedList;
import java.util.Queue;

class Result{
    private int Biol;
    private int Phys;
    private int Chem;
    private FieldPercentages fieldPercentages;
    private int proposalQuality;
    Result(){
        int Biol = 0;
        int Phys = 0;
        int Chem = 0;
    }
    void getResult(FieldPercentages fieldPercentages, int proposalQuality){
        Biol = (int) (fieldPercentages.Biol * proposalQuality);
        Phys = (int) (fieldPercentages.Phys * proposalQuality);
        Chem = (int) (fieldPercentages.Chem * proposalQuality);
    }

    public int getBiol() {
        return Biol;
    }

    public void setBiol(int biol) {
        Biol = biol;
    }

    public int getPhys() {
        return Phys;
    }

    public void setPhys(int phys) {
        Phys = phys;
    }

    public int getChem() {
        return Chem;
    }

    public void setChem(int chem) {
        Chem = chem;
    }
}


class ResultQueue{
    public Queue<Result> createResultQueue(){
        Queue<Result> rq = new LinkedList<>();
        for (int i = 0; i < 4; i++){
            Result r = new Result();
            rq.add(r);
        }
        return rq;
    }
}