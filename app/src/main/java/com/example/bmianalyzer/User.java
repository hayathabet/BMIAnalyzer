package com.example.bmianalyzer;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;

import static com.example.bmianalyzer.BMIConstants.*;
import static com.example.bmianalyzer.BMIConstants.FEMALE;
import static com.example.bmianalyzer.BMIConstants.MALE;
import static com.example.bmianalyzer.BMIConstants.NORMAL;
import static com.example.bmianalyzer.BMIConstants.OBESITY;
import static com.example.bmianalyzer.BMIConstants.OVER_WEIGHT;
import static com.example.bmianalyzer.BMIConstants.UNDER_WEIGHT;

public class User {

   private String name , password , email , uid;
   private Date bod ;
   private ArrayList<BMIRecord> records  ;
   private static com.example.bmianalyzer.User user ;
   private int gender ;
   private double lengthCM=160, weightKG=60 ;

  public static final User getUser(String name, String password ){
      if (user == null) user= new User(name , password  );
      return user ;
  }

    private User(String name, String password) {
        this.name = name;
        this.password = password;
        records = new ArrayList<>();
    }

    private User(){

    }

    public double getLengthCM() {
        return lengthCM;
    }

    public void setLengthCM(double lengthCM) {
        this.lengthCM = lengthCM;
    }

    public double getWeightKG() {
        return weightKG;
    }

    public void setWeightKG(double weightKG) {
        this.weightKG = weightKG;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    //calculate the age from the bod and return it
    @RequiresApi(api = Build.VERSION_CODES.O)
    public int calculateAge() {
      LocalDate b =  LocalDate.of(bod.getYear() , bod.getMonth() , bod.getDay());
        Period period = Period.between(b, LocalDate.now());
      return period.getYears();
    }//getAge

    //get the Birth of Date
    public Date getBod() {
        return bod;
    }//getBod

    //get the gender
    public int getGender() {
        return gender;
    }//getGender

    //set the gender
    public void setGender(int gender) {
        this.gender = gender;
    }//setGender

    // set Birth of Date
    public void setBod(Date bod) {
        this.bod = bod;
    }//setBod

    //return ALL records
    public ArrayList<BMIRecord> getRecords() {
        return records;
    }//getRecords

    //Add new record
    public void addRecord(BMIRecord record) {
        if(!records.contains(record)){
            records.add(record);
            setWeightKG(record.getWeight());
            setLengthCM(record.getLength());
        }

    }//addRecord


    //Initialize the records ArrayList
    public void reset(){
      records = new ArrayList<>();
    }//reset

    //return specific the records
    public BMIRecord getRecord(int record) {
        return records.get(record);
    }//getRecord

    //setRecords to replace the current records with new ones
    public void setRecords(ArrayList<BMIRecord> records) {
        this.records = records;
        if(records==null) return;
        if(records.isEmpty()) return;
        setLengthCM(records.get(records.size()-1).getLength());
        setWeightKG(records.get(records.size()-1).getWeight());

    }//setRecords

    //BMI is different according to User age
    @RequiresApi(api = Build.VERSION_CODES.O)
    public double agePercentage(){
      int age = calculateAge() ;
      if ( age >=2 && age<= 10) return .7 ;
        if ( age >10 && age<= 20)
         switch (gender){
          case MALE :
              return 0.9;
          case FEMALE :
              return  0.8 ;
         }
      return 1 ;
    }//getAgePercent

    //return the status of last BMIRecord
    @RequiresApi(api = Build.VERSION_CODES.O)
    public int calculateStatus(){
        if(records==null) return NORMAL;
        if(records.isEmpty()) return NORMAL;
        if(records.size()==1) return records.get(1).getStatus(agePercentage());
      return BMIRecord.toStringStatus(records.get(records.size()-1).getStatus(agePercentage()));
    }//getStatus

    //return the message to the user
    @RequiresApi(api = Build.VERSION_CODES.O)
    public int message(){
        if(records==null) return  R.string.SG;

        if(records.isEmpty()) return  R.string.SG;
      int i = records.size()-1 ;
      return message(records.get(i) , records.get(i-1));
    }//getMessage
    @RequiresApi(api = Build.VERSION_CODES.O)
    public int message(BMIRecord rc1 , BMIRecord rc2){
       double diff = rc1.getBMI(agePercentage()) - rc2.getBMI(agePercentage()) ;
       int status= rc1.getStatus(agePercentage());
        switch (status){
            case UNDER_WEIGHT :
                if(diff < -1 || (diff >= -1 && diff < -0.3)) return R.string.SB ;
                else if (diff>= -0.3 && diff< 0.3) return R.string.LC ;
                else if (diff>= 0.3 && diff< 0.6) return R.string.SG ;
                else return R.string.GA ;
            case NORMAL :
                if(diff < -1 ) return R.string.SB ;
                else if (diff>= -1 && diff< -0.3) return R.string.BC ;
                else if (diff>= -0.3 && diff< 0.3) return R.string.LC ;
                else return R.string.BC ;
            case OVER_WEIGHT :
                if(diff < -1 ) return R.string.BC ;
                else if (diff >= -1 && diff < -0.6) return R.string.GA ;
                else if (diff>= -0.6 && diff< -0.3) return R.string.SG ;
                else if (diff>= -0.3 && diff< 0.3) return R.string.LC ;
                else if (diff>= 0.3 && diff< 0.6) return R.string.BC ;
                else return R.string.SB ;
            case OBESITY :
                if(diff < -1 || (diff >= -1 && diff < -0.3)) return R.string.GA ;
                else if (diff>= -0.6 && diff< 0) return R.string.LC ;
                else if (diff>= 0 && diff< 0.3) return R.string.BC ;
                else return R.string.SB ;
        }

        return R.string.SG ;
    }//getMessage
}
