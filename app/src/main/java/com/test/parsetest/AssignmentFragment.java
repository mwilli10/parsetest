package com.test.parsetest;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.test.parsetest.model.Assignment;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

/**
 * Created by Morgan on 2/2/16.
 */
public class AssignmentFragment  extends Fragment implements ActivityCompat.OnRequestPermissionsResultCallback {


    private static final String ARG_DIBBIT_ID = "dibbit_id";
    private static final String DIALOG_DATE = "DialogDate";

    private static final int REQUEST_DATE = 0;

    private static final int PERMISSION_REQUEST_CALENDAR = 1;
    private static final int NUM_STARS = 5;

    private Assignment mDibbit;
    private EditText mTitleField;
    private TextView mCategoryField;
    private Date mDate;
    private Spinner mCategory;

    private Button mDateButton;
    private Button mSaveButton;
    private Button mSubtaskButton;
    private CheckBox mDoneCheckBox;
    private RatingBar mRatingBar;
    private EditText mDescriptionBox;


    public static AssignmentFragment newInstance(UUID dibbitId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DIBBIT_ID, dibbitId);

        AssignmentFragment fragment = new AssignmentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID dibbitId = (UUID) getArguments().getSerializable(ARG_DIBBIT_ID);
        mDibbit = AssignmentLab.get(getActivity()).getDibbit(dibbitId);
        mDate = mDibbit.getDate();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_assignment, container, false);

        mTitleField = (EditText) v.findViewById(R.id.dibbit_title);
        mTitleField.setText(mDibbit.getName());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //This space intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //This space intentionally left blank
            }

            @Override
            public void afterTextChanged(Editable s) {
                //This space intentionally left blank
            }
        });

//        mCategoryField = (EditText) v.findViewById(R.id.dibbit_category);
//        mCategoryField.setText(mDibbit.getCategory());
//        mCategoryField.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                //This space intentionally left blank
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                //This space intentionally left blank
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                //This space intentionally left blank
//            }
//        });
        mCategoryField = (TextView) v.findViewById(R.id.dibbit_category);
        mCategory = (Spinner) v.findViewById(R.id.dibbit_category_dropdown);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.assignment_category_options, android.R.layout.simple_spinner_dropdown_item);
        adapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        mCategory.setAdapter(adapter);


        mDateButton = (Button) v.findViewById(R.id.dibbit_date);
        updateDate();
        // Open date dialogue
        // If dibbit is in calendar, update it
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mDibbit.getDate());
                dialog.setTargetFragment(AssignmentFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);

            }
        });

//
//        mDoneCheckBox = (CheckBox) v.findViewById(R.id.dibbit_done);
//        mDoneCheckBox.setChecked(mDibbit.isDone());
//        mDoneCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                mDoneCheckBox.setChecked(isChecked);
//
//            }
//        });

        // Allow rating bar to be set to half value
        mRatingBar = (RatingBar) v.findViewById(R.id.dibbit_difficulty_ratingBar);
        mRatingBar.setNumStars(NUM_STARS);
        mRatingBar.setStepSize(0.5f);
        mRatingBar.setRating((float) mDibbit.getDifficulty());
        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                mRatingBar.setRating(rating);
            }
        });


        mDescriptionBox = (EditText) v.findViewById(R.id.dibbit_description);
        mDescriptionBox.setText(mDibbit.getDescription());
        mDescriptionBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //This space intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //This space intentionally left blank
            }


            @Override
            public void afterTextChanged(Editable s) {
                //This space intentionally left blank

            }
        });
        mSaveButton = (Button) v.findViewById(R.id.btn_save);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDibbit.setName(mTitleField.getText().toString());
                mDibbit.setCategory(mCategory.getSelectedItem().toString());
//                mDibbit.setDone(mDoneCheckBox.isChecked());
                mDibbit.setDifficulty(mRatingBar.getRating());
                mDibbit.setDescription(mDescriptionBox.getText().toString());
                Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();

            }
        });

        mSubtaskButton = (Button) v.findViewById(R.id.btn_update_tasks);
        if (mDibbit.getTasks() == true){
            mSubtaskButton.setVisibility(View.VISIBLE);
            mSubtaskButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //TODO OPEN THE EDIT SUBTASK EVENT
                }
            });
        }

        PackageManager packageManager = getActivity().getPackageManager();

        return v;
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }


        //Updates the date/time buttons
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mDibbit.setDate(date);
            updateDate();
        }
    }

    private void updateDate() {
        mDateButton.setText(android.text.format.DateFormat.format("EEEE, MMM dd, yyyy", mDibbit.getDate()));
    }

    // Access Dibbit attributes and insert event to calendar using Content Resolver and Values
    public void addEventToCalendar(Activity curActivity) {

        long calID = 1;
        Calendar calDate = Calendar.getInstance();

        long startMillis = calDate.getTimeInMillis();


        long testDate = mDibbit.getDate().getTime();
        long endMillis = (testDate + 60 * 60 * 1000);

        ContentResolver cr = getActivity().getContentResolver();
        ContentValues values = new ContentValues();

        values.put(CalendarContract.Events.EVENT_COLOR, 3);

        values.put(CalendarContract.Events.EVENT_COLOR, 2);
        values.put(CalendarContract.Events.DTSTART, testDate);
        values.put(CalendarContract.Events.DTEND, endMillis);
        values.put(CalendarContract.Events.TITLE, mDibbit.getName());
        values.put(CalendarContract.Events.DESCRIPTION, mDibbit.getDescription());
        values.put(CalendarContract.Events.CALENDAR_ID, calID);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().toString());

        values.put("hasAlarm", 1); // 0 for false, 1 for true


        //DO CHECK PERMISSION
        Toast.makeText(getActivity(), "Added to Calendar", Toast.LENGTH_SHORT).show();

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.WRITE_CALENDAR},
                    PERMISSION_REQUEST_CALENDAR);
        }
        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
        int eventID = Integer.parseInt(uri.getLastPathSegment());
        mDibbit.setEventId(eventID);


    }


    // Access Dibbit attributes and update event in calendar using Content Resolver and Values
    private int updateCalendarEntry(int eventID) {
        int iNumRowsUpdated = 0;

        ContentResolver cr = getActivity().getContentResolver();
        ContentValues values = new ContentValues();
        Uri updateUri = null;


        long testDate = mDibbit.getDate().getTime();
        long endMillis = (testDate + 60 * 60 * 1000);

        values.put(CalendarContract.Events.EVENT_COLOR, 3);
        Calendar calDate = Calendar.getInstance();

        values.put(CalendarContract.Events.DTSTART, testDate);
        values.put(CalendarContract.Events.DTEND, endMillis);
        values.put(CalendarContract.Events.TITLE, mDibbit.getName());
        values.put(CalendarContract.Events.DESCRIPTION, mDibbit.getDescription());
        values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().toString());

        values.put("hasAlarm", 1); // 0 for false, 1 for true

        updateUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventID);
        int rows = getActivity().getContentResolver().update(updateUri, values, null, null);
        return iNumRowsUpdated;
    }

    private int deleteCalendarEntry(int eventID) {
        int iNumRowsUpdated = 0;
        Uri deleteUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventID);
        int rows = getActivity().getContentResolver().delete(deleteUri, null, null);
        if (rows > 0) {
            Toast.makeText(getActivity(), "Deleted from Calendar", Toast.LENGTH_SHORT).show();
        }
        return iNumRowsUpdated;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // BEGIN_INCLUDE(onRequestPermissionsResult)
        if (requestCode == PERMISSION_REQUEST_CALENDAR) {
            // Request for camera permission.
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted. Start calendar preview Activity.
                Toast.makeText(getActivity(), "Calendar permission was granted.",
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                // Permission request was denied.
                Toast.makeText(getActivity(), "Calendar permission was denied.",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
        // END_INCLUDE(onRequestPermissionsResult)
    }

//    public void askForGoals() {
//
//        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                switch (which) {
//                    case DialogInterface.BUTTON_POSITIVE:
////                        Intent intent = new Intent(getActivity(), subgoals.class);
////                        startActivity();
//                        break;
//
//                    case DialogInterface.BUTTON_NEGATIVE:
//                        //No button clicked
//                        break;
//                }
//            }
//        };
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setMessage("Would you like to create sub-goals for this Assignment? " +
//                "Please remember that this is an important part of the metacognition process!")
//                .setPositiveButton("Yes", dialogClickListener)
//                .setNegativeButton("No", dialogClickListener).show();
//    }


}

