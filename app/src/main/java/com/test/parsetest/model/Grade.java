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
@ParseClassName("Grade")
public class Grade extends ParseObject {
    private UUID mId;


    public Grade() {
        //Constructor makes Dibbit with random ID and empty date
        mId = UUID.randomUUID();
        put("mUser", ParseUser.getCurrentUser());
        saveInBackground();
    }

    public UUID getId() {
        return mId;
    }

    public String getStrategiesUsed() {
        return getString("strategiesUsed");
    }

    public void setStrategiesUsed(String strategies) {
        put("strategiesUsed", strategies);
        saveInBackground();
    }

    public double getTimeSpentHrs() {
        return getDouble("timeSpent_hrs");
    }

    public void setTimeSpentHrs(double time) {
        put("timeSpent_hrs", time);
        saveInBackground();
    }


    public double getEffort() {
        return getDouble("effort");
    }

    public void setEffort(double effort) {
        put("effort", effort);
        saveInBackground();
    }


    public int getEventId(){
        return getInt("mEventId");
    }

    public void setEventId(int eventID){
        put("mEventId", eventID);
        saveInBackground();
    }

    public String getGrade() {
        return getString("grade");
    }

    public void setGrade(String grade) {
        put("grade", grade);
        saveInBackground();
    }

    public boolean getComplete() {

        return getBoolean("complete");
    }

    public void setComplete(boolean status) {
        put("complete", status);
        saveInBackground();
    }

    public double getPlanFollowed() {
        return getDouble("planFollowed");
    }

    public void setPlanFollowed(double planFollowed) {
        put("planFollowed", planFollowed);
        saveInBackground();
    }

    public String getFurtherImprovement() {
        return getString("furtherImprovement");
    }

    public void setFurtherImprovement(String planFollowed) {
        put("furtherImprovement", planFollowed);
        saveInBackground();
    }

    public String getAssnId() {
        return getString("assnId");
    }

    public void setAssnId(String id) {
        put("assnId", id);
        saveInBackground();
    }

    public String getName(){
        return getString("assnName");
    }

    public void setName(String name){
        put("assnName", name);
        saveInBackground();
    }


    public Date getDate(){
        return getDate("date");
    }

    public void setDate(Date date){
        put("date", date);
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