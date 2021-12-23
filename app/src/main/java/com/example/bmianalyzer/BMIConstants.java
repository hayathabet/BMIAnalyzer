package com.example.bmianalyzer;

public interface BMIConstants {
          int MALE=0 ,FEMALE=1 ;
          int LC= 10 ,SG= 11 , GA = 12 , BC=13 ,SB=14 , DELTA = 15;
          int NORMAL =0 ,UNDER_WEIGHT =-1 , OVER_WEIGHT=1 , OBESITY = 2 ;
          String DATE_PICKER = "datePicker";
          String TIME_PICKER = "timePicker";
          int PICKER_REQUEST = 0 ;
          //SharedPreferences
          String USER_LOGIN_STATUS = "user_login_status" ;
          String USER_NAME = "name" ;
          String USER_GENDER = "gender" ;
          String USER_STATUS = "status" ;
          String USER_MESSAGE = "message" ;
          String USER_PASSWORD ="password" ;
          String USER_FILE= "user_file" ;
          String USER_BOD= "bod" ;
          //firebase
          String USERS ="users";
          String UID = "uid";
    String USER_RECORDS = "records";
    String USER_LENGTH = "length" ;
    String USER_WEIGHT = "weight" ;
    String USER_EMAIL = "email" ;
    //int LOG_IN = 1 , LOG_OUT = 0 ;
}
