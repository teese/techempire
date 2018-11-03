package com.github.teese.techempire;

import java.util.LinkedList;
import java.util.Queue;

class Result{
    int Biol;
    int Phys;
    int Chem;
    FieldPercentages fieldPercentages;
    int proposalQuality;
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