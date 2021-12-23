package com.example.bmianalyzer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bmianalyzer.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {
   private  ActivitySplashBinding binding ;
   private Intent intent ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }//onCreate


    public void next(View view) {
        User user = SharedPreferencesHelper.getUser(getApplicationContext());
        if(user!=null)
            intent = new Intent(this , HomeActivity.class);
        else
            intent = new Intent(this , LoginActivity.class);
        finish();
        startActivity(intent);

    }//next

}//SplashActivity