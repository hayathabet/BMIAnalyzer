package com.example.bmianalyzer;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import com.example.bmianalyzer.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityHomeBinding binding ;
    private static User user;
    private static BMIRecordAdapter adapter ;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //set the onClickListener
        binding.addFoodButton.setOnClickListener(this);
        binding.addRecordButton.setOnClickListener(this);
        binding.logout.setOnClickListener(this);
        user = SharedPreferencesHelper.getUser(getApplicationContext());
        adapter = new BMIRecordAdapter(user , getBaseContext());

        binding.name.setText(user.getName());
        binding.currentStatus.setText(BMIRecord.toStringStatus(user.calculateStatus()));
        binding.message.setText(user.message());
        adapter.setRecords(user.getRecords());


        notifyAdapter();
        binding.recyclerView.setAdapter(adapter);

     //   Toast.makeText(this, "AGE = "+user.getAge(), Toast.LENGTH_SHORT).show();
        }



    @Override
    public void onClick(View view) {
        Intent intent ;
        switch (view.getId()){
            case R.id.add_food_button:
                intent = new Intent(HomeActivity.this, HomeActivity.class);
             break;
            case R.id.add_record_button:
                intent = new Intent(HomeActivity.this, NewRecordActivity.class );
                break;
            case R.id.logout:
                intent = new Intent(HomeActivity.this, LoginActivity.class );
                FirebaseHelper.getInstance().logout();
                SharedPreferencesHelper.clearUser(getApplicationContext());
                break;
            default:
                intent = new Intent(HomeActivity.this, SplashActivity.class);
        }
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        notifyAdapter();
    }

    public static User getUser(){
        return user ;
    }

    public static void notifyAdapter(){
        FirebaseHelper.getInstance().getRecords();
        adapter.setRecords(user.getRecords());
        adapter.notifyDataSetChanged();
        Log.e("0000000000", "00000 storeData: " +user.getRecords());
    }
}