package com.test.parsetest;

/**
 * Created by Morgan on 4/3/16.
 */

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class TaskActivity extends AppCompatActivity {

    private NumberPicker mFriendCountPicker;
    private FriendNameView mFriendNameView;
    private Button mSaveButton;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFriendCountPicker = (NumberPicker) findViewById(R.id.friend_count);
        mFriendNameView = (FriendNameView) findViewById(R.id.friend_names);
        mSaveButton = (Button) findViewById(R.id.count_friends_button);

        mFriendCountPicker.setMaxValue(8);
        mFriendCountPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mFriendNameView.setFriendCount(newVal);
            }
        });

        FragmentManager manager = getSupportFragmentManager();
        mFriendNameView.setFragmentManager(manager);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFriendNameView.checkForValidInput()) {
                    List<String> tasks = mFriendNameView.getFriendNames();
                    List<Date> dates = convertStrToDate(mFriendNameView.getFriendDates());
//                    Intent i = new Intent(TaskActivity.this, FriendCountActivity.class);
//                    i.putStringArrayListExtra("tasks", new ArrayList<String>(tasks));
//                    i.putStringArrayListExtra("dates", new ArrayList<String>(dates));
//                    startActivity(i);
                    addAllToCal(tasks, dates);
                    Intent i = new Intent(getApplicationContext(), AssignmentListActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });

    }

    private List<Date> convertStrToDate(List<String> dateStrs) {
            //Toast.makeText(FriendCountActivity.this, "dates:" + dates.toString(), Toast.LENGTH_LONG).show();
            List<Date> dates = new ArrayList<Date>();
            for (String date : dateStrs) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
                Date convertedDate = new Date();
                try {
                    convertedDate = dateFormat.parse(date);
                    dates.add(convertedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                System.out.println(convertedDate);

            }
        return dates;
        }


    public void addAllToCal(List<String> tasks,List<Date> dates ){

        for (int i =0 ; i < tasks.size(); i++){
            addEventToCalendar(tasks.get(i), dates.get(i));
        }
    }




    public Integer addEventToCalendar( String task, Date date ) {

        long calID = 1;
        Calendar calDate = Calendar.getInstance();

        long startMillis = calDate.getTimeInMillis();


        long endMillis = date.getTime();
        //long endMillis = (testDate + 60 * 60 * 1000);

        ContentResolver cr = getApplicationContext().getContentResolver();
        ContentValues values = new ContentValues();

        values.put(CalendarContract.Events.EVENT_COLOR, 3);

        values.put(CalendarContract.Events.EVENT_COLOR, 2);
        values.put(CalendarContract.Events.DTSTART, startMillis);
        values.put(CalendarContract.Events.DTEND, endMillis);
        //TODO
        values.put(CalendarContract.Events.TITLE, "Task Checkpoint");
        values.put(CalendarContract.Events.DESCRIPTION, task );
        values.put(CalendarContract.Events.CALENDAR_ID, calID);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().toString());

        values.put("hasAlarm", 1); // 0 for false, 1 for true


        //DO CHECK PERMISSION
        Toast.makeText(getApplicationContext(), "Task Added to Calendar", Toast.LENGTH_SHORT).show();

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {

//            ActivityCompat.requestPermissions(getApplicationContext(), new String[]{android.Manifest.permission.WRITE_CALENDAR},
//                    PERMISSION_REQUEST_CALENDAR);
        }
        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
        int eventID = Integer.parseInt(uri.getLastPathSegment());

        return eventID;


    }


    // Access Assignment attributes and update event in calendar using Content Resolver and Values
    private int updateCalendarEntry(int eventID, Date date, String task) {
        int iNumRowsUpdated = 0;

        ContentResolver cr = getApplicationContext().getContentResolver();
        ContentValues values = new ContentValues();
        Uri updateUri = null;


        long endMillis = date.getTime();

        values.put(CalendarContract.Events.EVENT_COLOR, 3);
        Calendar calDate = Calendar.getInstance();

        //values.put(CalendarContract.Events.DTSTART, testDate);
        values.put(CalendarContract.Events.DTEND, endMillis);
        values.put(CalendarContract.Events.DESCRIPTION, task);

        updateUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventID);
        int rows = getApplicationContext().getContentResolver().update(updateUri, values, null, null);
        return iNumRowsUpdated;

    }
}