package com.test.parsetest;

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

import com.test.parsetest.model.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Morgan on 4/10/16.
 */
public class TaskUpdateView extends LinearLayout{

    private int mFriendCount;
    private int mEditTextResId;
    private int mDatePickerResId;
    private FragmentManager fragmentManager;
    final String mTime = "11:59:00 AM";
    private Button mAddTask;
    private List<Task> mTasks;
    private List<Integer> mCalIds = new ArrayList<Integer>();
    List<String> mOldTasks = new ArrayList<String>();
    List<String> mOldDates = new ArrayList<String>();


    public TaskUpdateView(Context context) {
        this(context, null);
    }

    public void setFragmentManager(FragmentManager fm){
        fragmentManager = fm;
    }
    public FragmentManager getFragmentManager(){
        return fragmentManager;
    }

    public TaskUpdateView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TaskUpdateView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOrientation(VERTICAL);
    }

    public int getFriendCount() {
        return mFriendCount;
    }

    public void setUpOldTasks( List<Task> taskList) {

        //TODO test that these two list sizes are the same

            removeAllViews();
            mTasks = taskList;
            for (int i = 0; i < mTasks.size(); i++) {
                addView(createOldTask(mTasks.get(i).getDescription()));
                addView(createOldDate(mTasks.get(i).getDate()));
                if (mTasks.get(i).getCalId() > 0) {
                    mCalIds.add(mTasks.get(i).getCalId());
                }
            }
    }

    public View createOldTask(String task){
        View v;
        if (mEditTextResId > 0) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(mEditTextResId, this, true);
        } else {
            EditText et = new EditText(getContext());
            et.setTextColor(Color.parseColor("#FFFFFF"));
            et.setText(task);
            v = et;
        }
        return v;
    }

    public View createOldDate(Date date) {
        View v;
        if (mEditTextResId > 0) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(mEditTextResId, this, true);
        } else {

                final Button dp = new Button(getContext());
            if (date instanceof Date) {
                String dayOfTheWeek = (String) android.text.format.DateFormat.format("EEEE", date);
                String month = (String) android.text.format.DateFormat.format("MM", date);
                String year = (String) android.text.format.DateFormat.format("yyyy", date);
                String day = (String) android.text.format.DateFormat.format("dd", date);
                dp.setText(dayOfTheWeek + " " + month + "/" + day + "/" + year + " ");
            }
            dp.setTextColor(Color.parseColor("#FFFFFF"));
                    dp.setOnClickListener(new View.OnClickListener() {
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

    public void addTask(){

        addView(createEditText());
        addView(createDatePicker());
    }

    public int removeTask(){
        int i = getChildCount();
        int out = 0;
        if (i > 1) {
            View date = getChildAt(i-1);
            View task = getChildAt(i-2);
            if ((i/2)<=mTasks.size() && mCalIds.get((i/2)-1) >0 ){
                out = mCalIds.get((i/2)-1);
                mCalIds.remove(i/2-1);
                mTasks.get((i/2)-1).delete(true, getContext());
                mTasks.remove((i / 2) - 1);
            }
            LinearLayout linearParent = (LinearLayout) date.getParent();
            linearParent.removeView(date);
            linearParent.removeView(task);
        }

        return out;
    }

    private View createEditText() {
        View v;
        if (mEditTextResId > 0) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(mEditTextResId, this, true);
        } else {
            EditText et = new EditText(getContext());
            et.setHint(R.string.friend_name);
            et.setTextColor(Color.parseColor("#FFFFFF"));
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
            dp.setTextColor(Color.parseColor("#FFFFFF"));
            dp.setHint("Select Due Date");
            dp.setOnClickListener(new View.OnClickListener() {
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


    public boolean updateOldTasks(){
        boolean update = false;
        int i = getChildCount();
        int count;
        int out = 0;
        int oldTaskSize = mTasks.size();
        for  (count = 0; count <oldTaskSize; count++) {
            View task = getChildAt(count*2);
            View date = getChildAt(count*2+1);
            if ( mCalIds.get(count) >0 ) {

                if (task instanceof EditText) {
                    EditText et = (EditText) task;
                    mOldTasks.add(et.getText().toString());
                    mTasks.get(count).setDescription(et.getText().toString());
                    update = true;
                }
                if (date instanceof Button) {
                    Button et = (Button) date;
                    String source = et.getText().toString();
                    String substr=source.substring(source.indexOf(" ") + 1);
                    String datestr = substr + mTime;
                    mOldDates.add(datestr);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
                    Date convertedDate = new Date();
                    try {
                        convertedDate = dateFormat.parse(datestr);
                        mTasks.get(count).setDate(convertedDate);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    System.out.println(convertedDate);


                    update = true;
                }
            }
        }
       return update;
    }

    public List<String> getmOldTasks(){
        return mOldTasks;
    }
    public List<String> getmOldDates(){

        return mOldDates;
    }
    public List<Integer> getmOldCalIds(){

        return mCalIds;
    }


    public List<String> getNewTaskNames() {
        List<String> names = new ArrayList<>();
        for (int i = mTasks.size(); i < getChildCount()/2; i++) {
            View v = getChildAt(i*2);
            if (v instanceof EditText) {
                EditText et = (EditText) v;
                names.add(et.getText().toString());
            }
        }
        return names;
    }


    public List<String> getNewTaskDates(){
        List<String> dates = new ArrayList<>();
        for (int i = mTasks.size(); i < getChildCount()/2; i++) {
            View v = getChildAt(i*2+1);
            System.out.println(v.toString());
            if (v instanceof Button) {
                Button et = (Button) v;
                String source = et.getText().toString();
                String substr=source.substring(source.indexOf(" ") + 1);
                String datestr = substr + mTime;
                dates.add(datestr);
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




