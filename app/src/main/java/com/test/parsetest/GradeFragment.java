package com.test.parsetest;

import android.content.DialogInterface;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

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

import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.test.parsetest.model.Grade;
import java.util.UUID;

/**
 * Created by Morgan on 2/2/16.
 */

/**
 * Created by Morgan on 3/27/16.
 */
public class GradeFragment extends Fragment implements ActivityCompat.OnRequestPermissionsResultCallback {




    private static final String ARG_DIBBIT_ID = "dibbit_id";
    private static final String DIALOG_DATE = "DialogDate";

    private static final int REQUEST_DATE = 0;

    private static final int PERMISSION_REQUEST_CALENDAR = 1;
    private static final int NUM_STARS = 5;

    private Grade mDibbit;
    private TextView mTitleField;
    private TextView mCompletedField;
    private TextView mPlanFollowedField;
    private TextView mStrategyField;
    private TextView mTimeField;
    private TextView mEffortField;
    private TextView mReflectinoField;



    private Spinner mCompletedSpinner;
    private Spinner mGradeSpinner;
    private TextView mGradeTextView;
    private Grade mGrade;
    private Button mSaveButton;
    private RatingBar mPlanFollowedRatingBar;
    private RatingBar mEffortRatingBar;
    private EditText mStrat6;
    private EditText mTimeBox;
    private EditText mReflectionBox;
    private String Strategies = "";

    private CheckBox mStrat1, mStrat2, mStrat3, mStrat4, mStrat5;


    public static GradeFragment newInstance(UUID dibbitId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DIBBIT_ID, dibbitId);

        GradeFragment fragment = new GradeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID dibbitId = (UUID) getArguments().getSerializable(ARG_DIBBIT_ID);
        mDibbit = GradeLab.get(getActivity()).getDibbit(dibbitId);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.grade_feedback_sent, container, false);
        //Toast.makeText(getContext(), mDibbit.getGrade(),Toast.LENGTH_SHORT).show();

        if (mDibbit.getGrade() != null){
            v = inflater.inflate(R.layout.grade_feedback_sent, container, false);
            return v;

        }else {
            v = inflater.inflate(R.layout.fragment_grade, container, false);

            mTitleField = (TextView) v.findViewById(R.id.assn_title);
            mTitleField.setText(mDibbit.getName());

            mCompletedField = (TextView) v.findViewById(R.id.assn_completed);
            mCompletedSpinner = (Spinner) v.findViewById(R.id.assn_completed_dropdown);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.assignment_completed_options, android.R.layout.simple_spinner_dropdown_item);
            adapter
                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // Apply the adapter to the spinner
            mCompletedSpinner.setAdapter(adapter);


            // Allow rating bar to be set to half value
            mPlanFollowedField = (TextView) v.findViewById(R.id.assn_followed_plan_textview);
            mPlanFollowedRatingBar = (RatingBar) v.findViewById(R.id.assn_plan_followed_ratingBar);
            mPlanFollowedRatingBar.setNumStars(NUM_STARS);
            mPlanFollowedRatingBar.setStepSize(0.5f);
            mPlanFollowedRatingBar.setRating((float) mDibbit.getEffort());
            mPlanFollowedRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    mPlanFollowedRatingBar.setRating(rating);
                }
            });


            mStrategyField = (TextView) v.findViewById(R.id.assn_followed_plan_textview);

            mStrat1 = (CheckBox) v.findViewById(R.id.strat1);
            mStrat1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mStrat1.setChecked(isChecked);

                }
            });
            mStrat2 = (CheckBox) v.findViewById(R.id.strat2);
            mStrat2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mStrat2.setChecked(isChecked);

                }
            });
            mStrat3 = (CheckBox) v.findViewById(R.id.strat3);
            mStrat3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mStrat3.setChecked(isChecked);

                }
            });
            mStrat4 = (CheckBox) v.findViewById(R.id.strat4);
            mStrat4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mStrat4.setChecked(isChecked);

                }
            });
            mStrat5 = (CheckBox) v.findViewById(R.id.strat5);
            mStrat5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mStrat5.setChecked(isChecked);

                }
            });

            mStrat6 = (EditText) v.findViewById(R.id.strat6);

            mStrat6.addTextChangedListener(new TextWatcher() {
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


            mTimeField = (TextView) v.findViewById(R.id.assn_time_spent_txt_view);
            mTimeBox = (EditText) v.findViewById(R.id.assn_time_spent_box);
            mTimeBox.addTextChangedListener(new TextWatcher() {
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


            mEffortField = (TextView) v.findViewById(R.id.assn_effort_txt_view);

            mEffortRatingBar = (RatingBar) v.findViewById(R.id.assn_plan_followed_ratingBar);
            mEffortRatingBar.setNumStars(NUM_STARS);
            mEffortRatingBar.setStepSize(0.5f);
            mEffortRatingBar.setRating((float) mDibbit.getEffort());
            mEffortRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    mEffortRatingBar.setRating(rating);
                }
            });


            mReflectinoField = (TextView) v.findViewById(R.id.assn_reflection_txtView);
            mReflectionBox = (EditText) v.findViewById(R.id.assn_reflection_box);
            mReflectionBox.addTextChangedListener(new TextWatcher() {
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

            mGradeTextView = (TextView) v.findViewById(R.id.assn_grade_txt_view);
            mGradeSpinner = (Spinner) v.findViewById(R.id.assn_grade_dropdown);
            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(), R.array.grade_selection_options, android.R.layout.simple_spinner_dropdown_item);
            adapter
                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // Apply the adapter to the spinner
            mGradeSpinner.setAdapter(adapter2);


            mSaveButton = (Button) v.findViewById(R.id.btn_save);
            mSaveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (checkForValidFields()) {

                        new AlertDialog.Builder(getContext())
                                .setTitle("Completed Assignment Reflection")
                                .setMessage("You sure this is your final feedback?")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // continue with delete
                                    }
                                })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // do nothing
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();


                        if (mCompletedSpinner.getSelectedItem().toString().equals("Yes")) {
                            mDibbit.setComplete(true);
                        } else {
                            mDibbit.setComplete(false);
                        }
                        mDibbit.setPlanFollowed(mPlanFollowedRatingBar.getRating());
                        mDibbit.setEffort(mEffortRatingBar.getRating());
                        mDibbit.setTimeSpentHrs((double) Integer.parseInt(mTimeBox.getText().toString()));
                        mDibbit.setFurtherImprovement(mReflectionBox.getText().toString());
                        mDibbit.setGrade(mGradeSpinner.getSelectedItem().toString());


                        setStrategies();
                        mDibbit.setStrategiesUsed(Strategies);


                        Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Please complete all fields", Toast.LENGTH_SHORT).show();
                    }
                }


            });
            PackageManager packageManager = getActivity().getPackageManager();
            return v;

        }
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
    }

    public void  setStrategies(){
        if (mStrat1.isChecked()){
            Strategies = Strategies + mStrat1.getText().toString() + ",";
        }
        if (mStrat2.isChecked()){
            Strategies = Strategies + mStrat2.getText().toString() + ",";
        }
        if (mStrat3.isChecked()){
            Strategies = Strategies + mStrat3.getText().toString() + ",";
        }
        if (mStrat4.isChecked()){
            Strategies = Strategies + mStrat4.getText().toString() + ",";
        }
        if (mStrat5.isChecked()){
            Strategies = Strategies + mStrat5.getText().toString() + ",";
        }
        Strategies = Strategies+ mStrat6.getText().toString();

    }
    public boolean checkForValidFields(){

        // Check all Edit Texts
        if(mReflectionBox.getText().toString().length() <=0 ){
            return false;
        }
        if(mTimeBox.getText().toString().length() <=0 ){
            return false;
        }
        if(mTimeBox.getText().toString().length() <=0 ){
            return false;
        }
    return true;
    }

}
