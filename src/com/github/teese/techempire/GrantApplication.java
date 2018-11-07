package com.github.teese.techempire;

import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class GrantApplication {
    private Researcher r;
    private int randomQuality;
    private int proposalQuality;
    private int[] peerReviews;
    private String[] peerReviewsText;

    private Result result;
    GrantApplication(ArrayList<Researcher> rp){
        int randomIndex;
        FieldPercentages fieldPercentages;
        // currently allows 2 applications from same researcher
        randomIndex = (int) (Math.random() * rp.size());
        r = rp.get(randomIndex);
        randomQuality = (int) (Math.random() * 50);
        proposalQuality = Math.round(r.getQuality() + randomQuality);
        Random random = new Random();
        peerReviews = new int[3];
        peerReviewsText = new String[3];
        for (int ii = 0; ii<3; ii++){
            int reviewQuality = (int) Math.round(random.nextGaussian() * 40 + proposalQuality);
            peerReviews[ii] = reviewQuality;
            if (reviewQuality > 0.9) {
                peerReviewsText[ii] = "good";
            } else {
                peerReviewsText[ii] = "okay";
            }
        }

//        RandomEnum<ResearchField> resFieldRandomiser = new RandomEnum<ResearchField>(ResearchField.class);
        fieldPercentages = new FieldPercentages();
        fieldPercentages.getFieldPercentages();
        result = new Result();
        //result.getResult(
        //result.Biol = 8;
        //System.out.println("result biol " + result.Biol);
        result.getResult(fieldPercentages, proposalQuality);
        //System.out.println(" result biol " + result.Biol + " result Phys " + result.Phys + " result Chem " + result.Chem);
        //fieldPercentages, proposalQuality);
    }

//    public String getSummary(){
//        String summary = String.format("%s randomly selected. hIndex = %d, age = %d, researcherQuality = %.2f, randomQuality = %d, proposalQuality = %d, reviews = %s",
//                r.name, r.getHindex(), r.getAge(), r.generateRandomQuality(), randomQuality, proposalQuality, Arrays.toString(peerReviewsText));
//        return summary;
//    }
//    public void printSummary(){
//        String summary = String.format("%s randomly selected. hIndex = %d, age = %d, researcherQuality = %.2f, randomQuality = %d, proposalQuality = %d, reviews = %s",
//                r.name, r.getHindex(), r.getAge(), r.generateRandomQuality(), randomQuality, proposalQuality, Arrays.toString(peerReviews));
//        System.out.println(summary);
//
//        //System.out.println(r.name + " randomly selected. Quality = " + proposalQuality + "reviews = " + Arrays.toString(peerReviews));
//    }
    public int getProposalQuality(){
        return proposalQuality;
    }
    Result getResult(){
        return result;
    }
    Researcher getResearcher(){
        return r;
    }
    public int getRandomQuality() {
        return randomQuality;
    }
    public int[] getPeerReviews() {
        return peerReviews;
    }
    public String[] getPeerReviewsText() {
        return peerReviewsText;
    }
    String getButtonText(){
        String buttonText = String.format("<html><center> %s<br>h-index = %d<br>age = %d<br>reviews = %s",
                r.getName(), r.gethIndex(), r.getAge(), Arrays.toString(peerReviewsText));
        return buttonText;
    }
}

class FieldPercentages{
    float Biol;
    float Phys;
    float Chem;
    FieldPercentages(){
        Biol = 0.0f;
        Phys = 0.0f;
        Chem = 0.0f;
    }
    void setFieldPercentages(float[] fieldArray){
        Biol = fieldArray[0];
        Phys = fieldArray[1];
        Chem = fieldArray[2];
    }

    void getFieldPercentages(){
        float majorFieldPercentage;
        float minorFieldPercentage;
        float[] fieldArray;
        float[] shuffledFieldArray;

//        RandomEnum<ResearchField> resFieldRandomiser = new RandomEnum<ResearchField>(ResearchField.class);
//        ResearchField majorField = resFieldRandomiser.random();
        //System.out.println("randResField" + resFieldRandomiser.random());
        //ArrayList<ResearchField> minorfields = new ArrayList<ResearchField>();
        //minorfields.add(ResearchField.Biol);
        //minorfields.add(ResearchField.Phys);
        //minorfields.add(ResearchField.Chem);
        //minorfields.remove(majorField);
        Random random = new Random();
        majorFieldPercentage =  (float) random.nextGaussian() * 0.35f + 1.00f;
        if (majorFieldPercentage > 1.0f){
            majorFieldPercentage = 1.0f;
        } else if (majorFieldPercentage < 0.5f) {
            majorFieldPercentage = 0.5f;
        }
        // minorFieldPercentage = 1.0f - majorFieldPercentage;
        // fieldPercentages.minorfields[0] = minorFieldPercentage * Math.random();
        //fieldPercentages = new FieldPercentages();
        //System.out.println(fieldPercentages.Biol);
        //System.out.println("minorfields[0]" + minorfields.get(0));
        fieldArray = new float[3];
        //fieldArray[0] = 0.6f;
        //fieldArray[1] = 0.3f;
        //fieldArray[2] = 0.1f;

        // random number centred on 0.75
        fieldArray[0] = majorFieldPercentage;
        // random percentage of remainder
        float firstMinorFieldPercentageMultiplier =  (float) random.nextGaussian() * 0.35f + 1.00f;
        if (firstMinorFieldPercentageMultiplier > 1.0f){
            firstMinorFieldPercentageMultiplier = 1.0f;
        } else if (firstMinorFieldPercentageMultiplier < 0.6f) {
            firstMinorFieldPercentageMultiplier = 0.6f;
        }
        //System.out.println("firstMinorFieldPercentageMultiplier : " + firstMinorFieldPercentageMultiplier);
        float firstMinorFieldPercentage = (1.0f - fieldArray[0]) * firstMinorFieldPercentageMultiplier;
        //fieldArray[1] = (1.0f - fieldArray[0]) * (float) Math.random();
        fieldArray[1] = firstMinorFieldPercentage;
        // final remainder
        fieldArray[2] = 1.0f - majorFieldPercentage - firstMinorFieldPercentage;

        //System.out.println("fieldArray : " + Arrays.toString(fieldArray));
        //  shuffle
        //shuffleFloatArray(fieldArray);
        shuffledFieldArray = Shuffler.shuffleFloatArray(fieldArray);

        Biol = shuffledFieldArray[0];
        Phys = shuffledFieldArray[1];
        Chem = shuffledFieldArray[2];

    /*
    // set the field percentages using randomised array
    fieldPercentages.setFieldPercentages(fieldArray);
    //System.out.println(" majorfield " + majorField + " minorfields " + minorfields + " majorFieldPercentage " + majorFieldPercentage);
    System.out.println(Arrays.toString(fieldArray));
    //System.out.println(Arrays.toString(fieldArray * 5));
    System.out.println("fieldPercentages.Biol" + fieldPercentages.Biol + " " + fieldPercentages.Phys + " " + fieldPercentages.Chem);
    return fieldPercentages;
    */
    }
}

class Shuffler{
    static float[] shuffleFloatArray(float[] ar){
        // If running on Java 6 or older, use `new Random()` on RHS here
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--){
            int index = rnd.nextInt(i + 1);
            // Simple swap
            float a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
        return ar;
    }
}