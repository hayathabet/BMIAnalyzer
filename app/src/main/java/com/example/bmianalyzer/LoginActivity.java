package com.example.bmianalyzer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bmianalyzer.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
private ActivityLoginBinding binding ;
private User user ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        clicks();

    }

    private boolean check(String username, String password){
        //todo: check at the firebase
       user = FirebaseHelper.getInstance().login(username,password, this);
       if(user!=null){

           return true ;
       }else{
           return false;
       }
//        if(username.equals("haya")&&password.equals("123")){
//            user = User.getUser(username,password);
//            user.setGender(BMIConstants.FEMALE);
//            user.setBod(new Date(1999,4,17));
//            SharedPreferencesHelper.setUser(user,getApplicationContext());
//            return true ;
//        }
//       return false ;
    }


    private void clicks(){
        binding.signUpTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             startActivity(new Intent(LoginActivity.this, RegesterationActivity.class));
            }
        });

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check(binding.userName.getText().toString(),binding.password.getText().toString())){
                    finish();
                    startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                }else{
                    Toast.makeText(LoginActivity.this, "User Name or Password are wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}