package com.example.bmianalyzer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bmianalyzer.databinding.ActivityRegesterationBinding;

public class RegesterationActivity extends AppCompatActivity {
private ActivityRegesterationBinding binding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegesterationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        clicks();
    }

    private void clicks() {
        binding.createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(register()){
                    finish();
                    startActivity(new Intent(RegesterationActivity.this , CompleteActivity.class));
                }else {
                    Toast.makeText(RegesterationActivity.this, "The.....", Toast.LENGTH_SHORT).show();
                }

            }
        });

        binding.loginTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegesterationActivity.this ,LoginActivity.class));
            }
        });
    }

    private boolean register(){

        String userName= binding.name.getText().toString();
        String userPassword= binding.password.getText().toString();
        String userRePassword= binding.repassword.getText().toString();



        if(userPassword.equals(userRePassword)){
            User user = User.getUser(userName,userPassword);
            user.setEmail(binding.email.getText().toString());
            FirebaseHelper.getInstance().createUser(user,this);
            SharedPreferencesHelper.setUser(user, getApplicationContext());
            return true ;
        }else{
            Toast.makeText(this, "Password and re-password are not matched", Toast.LENGTH_SHORT).show();
            return false;
        }

    }
}