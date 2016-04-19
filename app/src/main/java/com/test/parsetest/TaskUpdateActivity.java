package com.test.parsetest;
    import android.Manifest;
    import android.content.ContentResolver;
    import android.content.ContentUris;
    import android.content.ContentValues;
    import android.content.pm.PackageManager;
    import android.net.Uri;
    import android.os.Bundle;
    import android.os.Handler;
    import android.provider.CalendarContract;
    import android.support.v4.app.FragmentManager;
    import android.support.v4.content.ContextCompat;
    import android.support.v7.app.AppCompatActivity;
    import android.view.View;
    import android.widget.Button;
    import android.widget.ImageButton;
    import android.widget.LinearLayout;
    import android.widget.NumberPicker;

    import java.text.ParseException;
    import java.text.SimpleDateFormat;
    import java.util.ArrayList;
    import java.util.Calendar;
    import java.util.List;

    import android.widget.Toast;

    import com.test.parsetest.model.Task;

    import java.util.Date;
    import java.util.TimeZone;
    import java.util.Timer;
    import java.util.TimerTask;



public class TaskUpdateActivity extends AppCompatActivity {

        private NumberPicker mFriendCountPicker;
        private TaskUpdateView mTaskUpdateView;
        private Button mSaveButton;
        private ImageButton mAddTaskButton;
        private ImageButton mRemoveTaskButton;
        private String mAssnID;
        private List<Task> mTasks;
        private static TaskLab sTaskLab;
        private Timer timer;
        private TimerTask timerTask;



        @Override protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_update_task);


            Bundle extra = getIntent().getExtras();
            mAssnID = extra.getString("ASSNID");
            sTaskLab = TaskLab.get(getApplicationContext());
            mTaskUpdateView = (TaskUpdateView) findViewById(R.id.tasks_update);
            sTaskLab.updateTasks(mAssnID);
            mTasks = sTaskLab.getTasks();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mTaskUpdateView.setUpOldTasks(mTasks);
                }
            }, 2000);



            mSaveButton = (Button) findViewById(R.id.save_task_update);
            mAddTaskButton = (ImageButton) findViewById(R.id.add_task_button);
            mRemoveTaskButton = (ImageButton) findViewById(R.id.remove_task_button);

           mAddTaskButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   mTaskUpdateView.addTask();
               }
           });

            mRemoveTaskButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int updateCal = mTaskUpdateView.removeTask();
//                    Toast.makeText(getApplicationContext(), Integer.toString(updateCal),Toast.LENGTH_SHORT).show();
                    deleteCalendarEntry(updateCal);
                }


            });

            FragmentManager manager = getSupportFragmentManager();
            mTaskUpdateView.setFragmentManager(manager);

            mSaveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTaskUpdateView.checkForValidInput()) {
                        if (mTaskUpdateView.updateOldTasks()){
                            List<String> tasks = mTaskUpdateView.getmOldTasks();

                            List<Date> dates = convertStrToDate(mTaskUpdateView.getmOldDates());

                            List<Integer> calIds = mTaskUpdateView.getmOldCalIds();
                            int size = tasks.size();
                            for (int i = 0; i <size ; i++){

                                //updates Calendar Entry, DB entry is updated from the view
                                updateCalendarEntry(calIds.get(i), tasks.get(i), dates.get(i));
                            }
                        }

//
                        List<Date> dates = convertStrToDate(mTaskUpdateView.getNewTaskDates());
                        List<String> tasks = mTaskUpdateView.getNewTaskNames();

//                        Toast.makeText(getApplicationContext(), tasks.toString(), Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getApplicationContext(), mTaskUpdateView.getNewTaskDates().toString(), Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getApplicationContext(), mTasks.toString(), Toast.LENGTH_SHORT).show();
                        if (tasks.size()>0) {
                            addAllToCalAndDb(tasks, dates);
                        }
                       // finish();
                    }
                }
            });

        }

        private List<Date> convertStrToDate(List<String> dateStrs) {
            //Toast.makeText(getApplicationContext(), "dates:" + dateStrs.toString(), Toast.LENGTH_LONG).show();
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


        public void addAllToCalAndDb( List<String> tasks, List<Date> dates ){

            for (int i =0 ; i < tasks.size(); i++){
                final String task = tasks.get(i);
                final Date date = dates.get(i);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int calID = addEventToCalendar(task, date);
                        new Task(calID,mAssnID, task, date);
                    }
                }, 500);

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
            values.put(CalendarContract.Events.TITLE, "Assignment Task Checkpoint");
            values.put(CalendarContract.Events.DESCRIPTION, task );
            values.put(CalendarContract.Events.CALENDAR_ID, calID);
            values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().toString());

            values.put("hasAlarm", 1); // 0 for false, 1 for true


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
        private int updateCalendarEntry(int eventID, String task, Date date) {
            int iNumRowsUpdated = 0;

            ContentResolver cr = getApplicationContext().getContentResolver();
            ContentValues values = new ContentValues();
            Uri updateUri = null;

            long endMillis = date.getTime();

            values.put(CalendarContract.Events.EVENT_COLOR, 3);
            Calendar calDate = Calendar.getInstance();

            values.put(CalendarContract.Events.DTEND, endMillis);
            values.put(CalendarContract.Events.DESCRIPTION, task);

            updateUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventID);
            int rows = getApplicationContext().getContentResolver().update(updateUri, values, null, null);
            if (rows>0){Toast.makeText(getApplicationContext(), "Task Updated in Calendar", Toast.LENGTH_SHORT).show();}
            return rows;

        }



    private int deleteCalendarEntry(int eventID) {
        int iNumRowsUpdated = 0;
        Uri deleteUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventID);
        int rows = getApplicationContext().getContentResolver().delete(deleteUri, null, null);
        if (rows>0){
            Toast.makeText(getApplicationContext(), "Task Deleted from Calendar",Toast.LENGTH_SHORT ).show();

        }
        return iNumRowsUpdated;
    }



}
