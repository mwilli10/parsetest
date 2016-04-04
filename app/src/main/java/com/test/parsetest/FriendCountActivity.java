package com.test.parsetest;

/**
 * Created by Morgan on 2/25/16.
 */
import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by rharter on 5/14/14.
 */
public class FriendCountActivity extends AppCompatActivity {

    private TextView mTextView;
    private List<Date> mDates = new ArrayList<>();
    private List<String> mTasks = new ArrayList<>();

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_count);

        mTextView = (TextView) findViewById(R.id.friend_text);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //List<String> names = extras.getStringArrayList("tasks");
            mTasks= extras.getStringArrayList("tasks");
            setFriendText(mTasks);
            List<String> dates = extras.getStringArrayList("dates");
            setFriendDate(dates);
            addAllToCal();
        }
    }

    private void setFriendText(List<String> names) {
        StringBuilder builder = new StringBuilder();
        builder.append("You just created " + names.size() + " subtasks");
        builder.append('\n');
        for (String name : names) {
            builder.append(name).append('\n');
        }
        mTextView.setText(builder.toString());

    }


    private void setFriendDate(List<String> dates) {
        //Toast.makeText(FriendCountActivity.this, "dates:" + dates.toString(), Toast.LENGTH_LONG).show();
        for (String date : dates) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
            Date convertedDate = new Date();
            try {
                convertedDate = dateFormat.parse(date);
                mDates.add(convertedDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            System.out.println(convertedDate);

        }
        // mTextView.setText(mDates.toString());
       Toast.makeText(FriendCountActivity.this, mDates.toString(), Toast.LENGTH_LONG).show();
    }


    public void addAllToCal(){

        for (int i =0 ; i < mTasks.size(); i++){
            addEventToCalendar(mTasks.get(i), mDates.get(i));
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


    // Access Dibbit attributes and update event in calendar using Content Resolver and Values
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