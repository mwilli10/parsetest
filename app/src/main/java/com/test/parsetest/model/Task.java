package com.test.parsetest.model;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Morgan on 4/10/16.
 */
@ParseClassName("Task")
public class Task extends ParseObject {


    public Task() {
        put("mUser", ParseUser.getCurrentUser());
        saveInBackground();

    }

    public Task(int calID, String assnID, String description, Date date) {
        //Constructor makes Task associated with assn and cal event

        put("mUser", ParseUser.getCurrentUser());
        put("assnUUID", assnID);
        put("calID", calID);
        put("dateDue", date);
        put("taskDescription", description);
        saveInBackground();

    }


    public Date getDate() {
        return getDate("dateDue");
    }

    public void setDate(Date date) {
        put("dateDue", date);
        saveInBackground();
    }

    public int getCalId() {
        return getInt("calID");
    }

    public void setCalId(int calId) {
        put("calID", calId);
    }

    public String getDescription() {
        return getString("taskDescription");
    }

    public void setDescription(String description) {
        put("taskDescription", description);
    }


    public void delete(boolean delete, Context context) {
        if (delete) {
            deleteEventually();
        }
    }
}
