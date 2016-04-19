package com.test.parsetest;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.test.parsetest.model.Grade;
import com.test.parsetest.model.Hints;
import com.test.parsetest.model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Morgan on 4/15/16.
 */
public class TaskLab {




    private static TaskLab sTaskLab;
    private Context mContext;
    private boolean mDidDataSetChange = false;


    public List<Task> mTasks;;

    private TaskLab(Context context) {
        mContext = context;
        mTasks = new ArrayList<>();
    }


    public static TaskLab get(Context context) {
        if (sTaskLab == null) {
            sTaskLab = new TaskLab(context);
        }
        return sTaskLab;
    }


    public void updateTasks(String assnID) {
        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Task");
        query1.whereEqualTo("mUser", ParseUser.getCurrentUser());
//        query1.setLimit(20);
        query1.whereEqualTo("assnUUID", assnID);


        List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
        queries.add(query1);

        ParseQuery<ParseObject> query = ParseQuery.or(queries);
        query.orderByAscending("createdAt");
        // Run the query
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> dibbitList, ParseException e) {

                if (e == null) {
                    // If there are results, update the list of dibbits
                    // and notify the adapter
                    mTasks.clear();


                    for (ParseObject dibbit : dibbitList) {
                        mTasks.add((Task) dibbit);
                    }

                } else {
                    Log.d("Post retrieval", "Error: " + e.getMessage());
                }
            }

        });

    }

    public List<Task> getTasks() {
        return mTasks;
    }




}
