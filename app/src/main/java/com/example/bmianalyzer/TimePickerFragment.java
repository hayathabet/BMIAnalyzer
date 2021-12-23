package com.example.bmianalyzer;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

/**
 * DialogFragment used to pick a ToDoItem deadline date
 */
public class TimePickerFragment extends DialogFragment {
    TimePickerDialog.OnTimeSetListener onTimeSetListener ;



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onTimeSetListener = ( TimePickerDialog.OnTimeSetListener) context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);


        // Create a new instance of DatePickerDialog and return it
        return   new TimePickerDialog(getActivity() , onTimeSetListener , hourOfDay , minute , false);


    }

}