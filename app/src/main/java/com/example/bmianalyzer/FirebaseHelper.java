package com.example.bmianalyzer;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class FirebaseHelper {
    private static FirebaseHelper firebaseHelper ;
    private static String UID="";


    public static FirebaseHelper getInstance(){
        if(firebaseHelper==null) {
            firebaseHelper=new FirebaseHelper();

        }
        return firebaseHelper ;
    }

    private FirebaseHelper(){

    }

    public void addBMIRecord(BMIRecord record){
        getFirebaseUser().child(BMIConstants.USER_RECORDS).push();
        record.setKey();
        getFirebaseUser().child(BMIConstants.USER_RECORDS).child(record.getKey()+"").setValue(record);
    }


    public ArrayList<BMIRecord> getRecords(){
        final ArrayList<BMIRecord> records = new  ArrayList<>();
         getFirebaseUser().child(BMIConstants.USER_RECORDS).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    ArrayList result =(ArrayList)task.getResult().getValue() ;
                    if(result!=null){
                        for(int i=0 ; i<result.size(); i++){
                          if(result.get(i)!=null){
                              HashMap rec= (HashMap) result.get(i) ;
                              BMIRecord add = new BMIRecord();
                              add.setTime((String)rec.get("time"));
                              add.setDate((String)rec.get("date"));
                              add.setWeight((long)rec.get("weight"));
                              add.setLength((long)rec.get("length"));

                              HomeActivity.getUser().addRecord (add);

                              Log.e("FIREBASE", "0000000000000000 GET RECORDS onComplete: " + result.size() );


                          }

                        }

                        HomeActivity.notifyAdapter();
//                           records.addAll(result);
//                           addListener(context);
//                           HomeActivity.getUser().setRecords(result);
                    }

                    //records[0] =new ArrayList<BMIRecord>(result.values());
                }
            }
        });

        return records;
    }

    public void createUser(User user , Context context){

      getFirebaseAuth().createUserWithEmailAndPassword(user.getEmail(),user.getPassword())
              .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                  boolean complete = true ;
                  @Override
                  public void onComplete(@NonNull Task<AuthResult> task) {

                      if(task.isSuccessful()){
                         // UID= getFirebaseUsers().push().getKey();
                          getFirebaseUsers().push();
                          UID= task.getResult().getUser().getUid();
                          getFirebaseUser().child(BMIConstants.USER_EMAIL).setValue(user.getEmail());
                          addListener(context);
                          getFirebaseUser().child(BMIConstants.USER_NAME).setValue(user.getName());
                          getFirebaseUser().child(BMIConstants.USER_PASSWORD).setValue(user.getPassword());
//
                      }else{
                          Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                          complete = false ;
                      }
                      if(complete)context.startActivity(new Intent(context , CompleteActivity.class));
                  }


              });
    }




    public void completeRegistration(User user){
        getFirebaseUsers().child(UID);
        getFirebaseUser().child(BMIConstants.USER_EMAIL).setValue(user.getEmail());
        getFirebaseUser().child(BMIConstants.USER_NAME).setValue(user.getName());
        getFirebaseUser().child(BMIConstants.USER_PASSWORD).setValue(user.getPassword());
        getFirebaseUser().child(BMIConstants.USER_GENDER).setValue(user.getGender());
        getFirebaseUser().child(BMIConstants.USER_BOD).setValue(user.getBod());
        getFirebaseUser().child(BMIConstants.USER_LENGTH).setValue(user.getLengthCM());
        getFirebaseUser().child(BMIConstants.USER_WEIGHT).setValue(user.getWeightKG());
        //getFirebaseUser().child(BMIConstants.).setValue(user.getGender());
    }

    public User login(String name, String password , Context context){
        final boolean[] success = {false};

        getFirebaseAuth().signInWithEmailAndPassword(name , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
              if(task.isSuccessful()){
               SharedPreferencesHelper.setUser(getUser(User.getUser(name,password)),context);
               addListener(context);
               success[0] =true ;
              }
            }
        });
        //getFirebase().child(BMIConstants.USERS).child(BMIConstants.UID);
        if(success[0]) return SharedPreferencesHelper.getUser(context);
        else{
            SharedPreferencesHelper.clearUser(context);
            return  null ;
        }
    }//login
    public  User getUser(User user){
     //   user.setName(  getFirebaseUser().child(BMIConstants.USER_NAME).getKey() ) ;
        getFirebaseUser().get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                  HashMap u = (HashMap) task.getResult().getValue();
                  if(u!=null){
                 user.setBod((Date) u.get(BMIConstants.USER_BOD));
                 // user.setGender((int) u.get(BMIConstants.USER_GENDER));
                // user.setRecords(u.getRecords());
                 user.setName((String) u.get(BMIConstants.USER_NAME));
                 user.setEmail((String) u.get(BMIConstants.USER_EMAIL));
                 user.setUid(UID);
                      Log.e("FIREBASE", "0000000000000000 GET USER onComplete: " + u );
                 //user.setRecords(getRecords());
              }


                }
            }
        });
        return user ;
    }
   private static DatabaseReference getFirebaseUsers(){
        return FirebaseDatabase.getInstance().getReference().child(BMIConstants.USERS);
   }

    private static DatabaseReference getFirebaseUser(){
        return getFirebaseUsers().child(UID);
    }

    private static FirebaseAuth getFirebaseAuth(){

       // FirebaseApp.initializeApp(context);
        return FirebaseAuth.getInstance();
   }

   public void  logout(){
        getFirebaseAuth().signOut();
   }

   public void addListener(Context context){
        getFirebaseAuth().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
               SharedPreferencesHelper.setUser(
                        getUser(SharedPreferencesHelper.getUser(context)) , context
                );
                HomeActivity.getUser().setRecords(getRecords());
            }
        });
   }
}

