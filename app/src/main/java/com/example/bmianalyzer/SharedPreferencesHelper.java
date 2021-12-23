package com.example.bmianalyzer;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

public class SharedPreferencesHelper {
    private static boolean isLoggedIn(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(BMIConstants.USER_FILE, Context.MODE_PRIVATE);
        return preferences.getBoolean(BMIConstants.USER_LOGIN_STATUS, false);
    }

    private static int getUserGender(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(BMIConstants.USER_FILE, Context.MODE_PRIVATE);
        return preferences.getInt(BMIConstants.USER_GENDER, -1);
    }

    private static double getUserLength(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(BMIConstants.USER_FILE, Context.MODE_PRIVATE);
        return preferences.getFloat(BMIConstants.USER_LENGTH, -1);
    }
    private static double getUserWeight(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(BMIConstants.USER_FILE, Context.MODE_PRIVATE);
        return preferences.getFloat(BMIConstants.USER_WEIGHT, -1);
    }


//    public static int getUserStatus(Context context) {
//        SharedPreferences preferences = context.getSharedPreferences(BMIConstants.USER_STATUS, Context.MODE_PRIVATE);
//        return preferences.getInt(BMIConstants.USER_STATUS, -1);
//    }
//
//    public static int getUserMessage(Context context) {
//        SharedPreferences preferences = context.getSharedPreferences(BMIConstants.USER_MESSAGE, Context.MODE_PRIVATE);
//        return preferences.getInt(BMIConstants.USER_MESSAGE, -1);
//    }

    private static String getUserName(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(BMIConstants.USER_FILE, Context.MODE_PRIVATE);
        return preferences.getString(BMIConstants.USER_NAME, "Empty");
    }


    private static String getUserPassword(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(BMIConstants.USER_FILE, Context.MODE_PRIVATE);
        return preferences.getString(BMIConstants.USER_PASSWORD, "Empty");
    }

    private static String getUid(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(BMIConstants.USER_FILE, Context.MODE_PRIVATE);
        return preferences.getString(BMIConstants.UID, "Empty");
    }

    private static long getUserBOD(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(BMIConstants.USER_FILE, Context.MODE_PRIVATE);
        return preferences.getLong(BMIConstants.USER_BOD, 0);
    }
    public static User getUser(Context context){
        String name = getUserName(context) ;
        String password = getUserPassword(context) ;
        if(!name.equals("Empty")||!password.equals("Empty")){
            User user= User.getUser( name,password );
            user.setGender(getUserGender(context));
            user.setBod(new Date(getUserBOD(context)));
            user.setUid(getUid(context));
            user.setLengthCM(getUserLength(context));
            user.setWeightKG(getUserWeight(context));
            user.setEmail(getEmail(context));
            return  user ;
        }

       return  null;
    }

    private static String getEmail(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(BMIConstants.USER_FILE, Context.MODE_PRIVATE);
        return preferences.getString(BMIConstants.USER_EMAIL, "Empty");
    }


    public static void setUser(User user , Context context){
        SharedPreferences.Editor sharedPref =context.getSharedPreferences(BMIConstants.USER_FILE,Context.MODE_PRIVATE).edit();
        sharedPref.putBoolean(BMIConstants.USER_LOGIN_STATUS, true);
        sharedPref.putInt(BMIConstants.USER_GENDER, user.getGender());
        sharedPref.putFloat(BMIConstants.USER_LENGTH, (float) user.getLengthCM());
        sharedPref.putFloat(BMIConstants.USER_WEIGHT, (float) user.getWeightKG());
        sharedPref.putInt(BMIConstants.USER_GENDER, user.getGender());
        sharedPref.putString(BMIConstants.USER_NAME, user.getName());
        sharedPref.putString(BMIConstants.UID, user.getUid());
        sharedPref.putString(BMIConstants.USER_PASSWORD, user.getPassword());
        sharedPref.putString(BMIConstants.USER_EMAIL, user.getEmail());
        if(user.getBod()!=null)
        sharedPref.putLong(BMIConstants.USER_BOD , user.getBod().getTime());
        sharedPref.apply();
    }

    public static void clearUser(Context context){
        SharedPreferences.Editor sharedPref =context.getSharedPreferences(BMIConstants.USER_FILE,Context.MODE_PRIVATE).edit();
        sharedPref.clear();
        sharedPref.apply();
    }
}
