package com.example.bmianalyzer;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;


import com.example.bmianalyzer.databinding.ActivityCompleteBinding;

import java.util.Date;

public class CompleteActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener  {
    private ActivityCompleteBinding binding;
    private User user;
    private int length = 160 , weight = 60 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCompleteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        //attach onClickListeners
        binding.minusLength.setOnClickListener(this);
        binding.plusLength.setOnClickListener(this);
        binding.minusWeight.setOnClickListener(this);
        binding.plusWeight.setOnClickListener(this);
        binding.dobEdit.setOnClickListener(this);
        binding.saveDataBtn.setOnClickListener(this);
        user =SharedPreferencesHelper.getUser(getApplicationContext());

    }


    //onClickListener
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.minus_length:
                length--;
                binding.length.setText(String.format("%d", length));
                return;
            case R.id.minus_weight:
                weight--;
                binding.weightTxt.setText(String.format("%d", weight));
                return;
            case R.id.plus_length:
                length++;
                binding.length.setText(String.format("%d", length));
                return;
            case R.id.plus_weight:
                weight++;
                binding.weightTxt.setText(String.format("%d", weight));
                return;
            case R.id.dob_edit:
                showDatePickerDialog();
                return;

            case R.id.save_data_btn:
                storeUserInfo();
                return;
            default:

        }//switch
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void storeUserInfo() {
        //todo: save the info in firebase
        if(binding.maleRd.isChecked())user.setGender(BMIConstants.MALE);
        else user.setGender(BMIConstants.FEMALE);
        user.setWeightKG(Double.parseDouble(binding.weightTxt.getText().toString()));
        user.setLengthCM(Double.parseDouble(binding.length.getText().toString()));
        FirebaseHelper.getInstance().completeRegistration(user);
        SharedPreferencesHelper.setUser(user,getApplicationContext());
        startActivity(new Intent(this, HomeActivity.class));
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        String date = "" + i2 + "/" + i1 + "/" + i;
        binding.dobEdit.setText(date);
        user.setBod(new Date(i,i1,i2));
    }

    private void showDatePickerDialog() {
        DialogFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setShowsDialog(true);
        datePickerFragment.show(getSupportFragmentManager(), BMIConstants.DATE_PICKER);
    }
}