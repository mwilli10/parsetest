package com.test.parsetest;

/**
 * Created by Morgan on 2/25/16.
 */

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

//import android.app.FragmentManager;

/**
 * A custom compound view that displays an arbitrary
 * number of text views to enter your friends names.
 *
 * Created by rharter on 5/14/14.
 */
public class FriendNameView extends LinearLayout {

    private int mFriendCount;
    private int mEditTextResId;
    private int mDatePickerResId;
    private FragmentManager fragmentManager;
    final String mTime = "11:59:00 PM";

    public FriendNameView(Context context) {
        this(context, null);
    }

    public void setFragmentManager(FragmentManager fm){
        fragmentManager = fm;
    }
    public FragmentManager getFragmentManager(){
        return fragmentManager;
    }

    public FriendNameView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FriendNameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOrientation(VERTICAL);
    }

    public int getFriendCount() {
        return mFriendCount;
    }

    public void setFriendCount(int friendCount) {
        if (friendCount != mFriendCount) {
            mFriendCount = friendCount;

            removeAllViews();
            for (int i = 0; i < mFriendCount; i++) {
                addView(createEditText());
                addView(createDatePicker());
            }
        }
    }

    private View createEditText() {
        View v;
        if (mEditTextResId > 0) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(mEditTextResId, this, true);
        } else {
            EditText et = new EditText(getContext());
            et.setTextColor(Color.parseColor("#FFFFFF"));

            et.setHint(R.string.friend_name);
            v = et;
        }
        return v;
    }


    private View createDatePicker() {
        View v;
        if (mEditTextResId > 0) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(mEditTextResId, this, true);
        } else {
            final Button dp = new Button(getContext());
            dp.setHint("Select Due Date");
            dp.setTextColor(Color.parseColor("#FFFFFF"));
            dp.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment newFragment = new SelectDateFragment(dp);
                    newFragment.show(getFragmentManager(), "DatePicker");
                }
            });
            v = dp;
        }
        return v;
    }


    public int getEditTextResId() {
        return mEditTextResId;
    }

    public void setEditTextResId(int editTextResId) {
        mEditTextResId = editTextResId;
    }

    /**
     * Returns a list of entered friend names.
     */
    public List<String> getFriendNames() {
        List<String> names = new ArrayList<>();
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            if (v instanceof EditText) {
                EditText et = (EditText) v;
                names.add(et.getText().toString());
            }
        }
        return names;
    }


    public List<String> getFriendDates() {
        String dateString;
        String source;
        List<String> dates = new ArrayList<>();
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            if (v instanceof Button) {
                Button et = (Button) v;
                source = et.getText().toString();
                dateString = source.substring(source.indexOf(" ") + 1) + mTime;

                dates.add(dateString);
            }

        }

        return dates;
    }

    public boolean checkForValidInput(){
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            if (v instanceof Button ) {
                Button date = (Button) v;
                if (date.getText().toString().length() <=0 ){
                    Toast.makeText(getContext(), "Must fill out all fields", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            else{
                EditText task = (EditText) v;
                if(task.getText().toString().length() <=0 ){
                    Toast.makeText(getContext(), "Must fill out all fields", Toast.LENGTH_SHORT).show();
                    return false;
                }

            }
            }
        return true;
    }


}


