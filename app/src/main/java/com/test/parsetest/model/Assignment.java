package com.test.parsetest.model;

import android.content.Context;
import android.content.DialogInterface;

import android.support.v7.app.AlertDialog;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.ParseACL;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Morgan on 2/2/16.
 */
@ParseClassName("Assignment")
public class Assignment extends ParseObject {
    private UUID mId;


     public Assignment() {
        //Constructor makes Dibbit with random ID and empty date
        mId = UUID.randomUUID();
        put("mUser", ParseUser.getCurrentUser());
         put("mIsDone", false);


        saveInBackground();

    }

    public UUID getId() {
        return mId;
    }

    public String getDescription() {
        return getString("mDescription");
    }

    public void setDescription(String description) {
        put("mDescription", description);
        saveInBackground();
    }

    public String getCategory() {
        return getString("mCategory");
    }

    public void setCategory(String category) {
        put("mCategory", category);
        saveInBackground();
    }


    public double getDifficulty() {
        return getDouble("mDifficulty");
    }

    public void setDifficulty(double difficulty) {
        put("mDifficulty", difficulty);
        saveInBackground();
    }


    public int getEventId(){
        return getInt("mEventId");
    }

    public void setEventId(int eventID){
        put("mEventId", eventID);
        saveInBackground();
    }

    public String getName() {
        return getString("mName");
    }

    public void setName(String title) {
        put("mName", title);
        saveInBackground();
    }

    public Date getDate() {
        if (getDate("mDate") == null) {
            put("mDate", new Date());
        }
        return getDate("mDate");
    }

    public void setDate(Date date) {
        put("mDate", date);
        saveInBackground();
    }

    public boolean isDone() {
        return getBoolean("mIsDone");
    }

    public void setDone(boolean isDone) {
        put("mIsDone", isDone);

        saveInBackground();
    }

    public void delete(boolean delete, Context context){
        if (delete) {
            //
            new AlertDialog.Builder(context)
                    .setTitle("Delete entry")
                    .setMessage("You sure you want to click this?  This assignment will be moved to the grades section")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            deleteEventually();
                            //REFRESH HERE IS BETTER

                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        }

    }
}