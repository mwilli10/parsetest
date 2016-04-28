package com.test.parsetest;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Morgan on 3/22/16.
 */
@SuppressLint("ValidFragment")
public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private Button edit;
    private String weekday;

    public SelectDateFragment(Button edit) {
        this.edit = edit;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
        weekday = dayFormat.format(calendar.getTime());
        return new DatePickerDialog(getActivity(), this, yy, mm, dd);
    }

    public void onDateSet(DatePicker view, int yy, int mm, int dd ) {
        populateSetDate(edit, yy, mm + 1, dd, weekday);
    }

    public void populateSetDate(Button edit, int year, int month, int day, String weekday) {
        edit.setText(month + "/" + day + "/" + year + " ");
    }

}