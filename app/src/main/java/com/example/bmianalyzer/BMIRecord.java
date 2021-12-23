package com.example.bmianalyzer;

import static com.example.bmianalyzer.BMIConstants.*;
import static com.example.bmianalyzer.BMIConstants.NORMAL;
import static com.example.bmianalyzer.BMIConstants.OBESITY;
import static com.example.bmianalyzer.BMIConstants.OVER_WEIGHT;
import static com.example.bmianalyzer.BMIConstants.UNDER_WEIGHT;

public class BMIRecord {
   private double length , weight ;
   private String date ;
   private String time ;
   public static int KEY = 0;
   private int key ;

    public BMIRecord(double length, double weight, String date) {
        this.length = length;
        this.weight = weight;
        this.date = date;
        setKey();
        KEY++;
    }

    public BMIRecord(){

    }

    public String getDate() {
        return date;
    }

    public double getLength() {
        return length;
    }

    public double getWeight() {
        return weight;
    }

    public double getBMI(double percent) {
        return ((weight / Math.sqrt(length/100) ) * percent) ;

    }

    public int getStatus(double percent) {
        double bmi = getBMI(percent);
        if(bmi < 18.5 ) return UNDER_WEIGHT;
        if(bmi >= 18.5 && bmi < 25  ) return NORMAL;
        if(bmi >= 25 && bmi < 30 ) return OVER_WEIGHT;
        return OBESITY;
    }

    public static int toStringStatus(int status) {
        switch(status){
            case NORMAL : return  R.string.normal ;
            case OBESITY : return R.string.obesity ;
            case OVER_WEIGHT : return R.string.over_weight;
            case UNDER_WEIGHT : return R.string.under_weight;
        }
        return -1;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getKey() {
        return key;
    }

    public void setKey() {
        this.key = KEY;
    }
}
