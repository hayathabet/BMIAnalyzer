package com.example.bmianalyzer;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;


import com.example.bmianalyzer.databinding.ActivityNewRecordBinding;


public class NewRecordActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener , TimePickerDialog.OnTimeSetListener {
private ActivityNewRecordBinding binding ;
private double length = 160 , weight = 60 ;
private BMIRecord record ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewRecordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        record = new BMIRecord(length,weight,"");
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        //attach onClickListeners
        binding.minusLength.setOnClickListener(this);
        binding.plusLength.setOnClickListener(this);
        binding.minusWeight.setOnClickListener(this);
        binding.plusWeight.setOnClickListener(this);
        binding.editDate.setOnClickListener(this);
        binding.editTime.setOnClickListener(this);
        binding.saveDataBtn.setOnClickListener(this);

        User user = SharedPreferencesHelper.getUser(getApplicationContext());
        length = user.getLengthCM();
        weight = user.getWeightKG();
        binding.length.setText(String.format("%s", length));
        binding.weightTxt.setText(String.format("%s", weight));
    }


    //onClickListener
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.minus_length:
                length--;
                record.setLength(length);
                binding.length.setText(String.format("%s", length));
                return;
            case R.id.minus_weight:
                weight--;
                record.setWeight(weight);
                binding.weightTxt.setText(String.format("%s", weight));
                return;
            case R.id.plus_length:
                length++;
                record.setLength(length);
                binding.length.setText(String.format("%s", length));
                return;
            case R.id.plus_weight:
                weight++;
                record.setWeight(weight);
                binding.weightTxt.setText(String.format("%s", weight));
                return;
            case R.id.edit_date:
                showDatePickerDialog();
                return;
            case R.id.edit_time:
                showTimePickerDialog();
                return;
            case R.id.save_data_btn:
                storeRecord();
                return;
            default:

        }//switch
    }

    private void storeRecord() {
        FirebaseHelper.getInstance().addBMIRecord(record);
        startActivity(new Intent(this,HomeActivity.class));
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
    String date = ""+i2+"/"+i1+"/"+i ;
    binding.editDate.setText(date);
    record.setDate(date);
    }

    private void showDatePickerDialog() {
        DialogFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setShowsDialog(true);
        datePickerFragment.show(getSupportFragmentManager(), BMIConstants.DATE_PICKER);
    }

    private void showTimePickerDialog() {
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.setShowsDialog(true);
        timePicker.show(getSupportFragmentManager(), BMIConstants.TIME_PICKER);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        String time = ""+i+":"+i1 ;
        binding.editTime.setText(time);
        record.setTime(time);
    }
}