package com.test.parsetest;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.util.Pair;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.util.Pair;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.ParseQuery;
import com.test.parsetest.model.Assignment;
import com.test.parsetest.model.Grade;


/**
 * Created by Morgan on 2/2/16.
 */
public class GradeLab {


    private static GradeLab sDibbitLab;
    private Context mContext;
    private boolean mDidDataSetChange = false;


    private List<Grade> mAssignments;

    private GradeLab(Context context) {
        mContext = context;
        mAssignments = new ArrayList<>();
    }


    public static GradeLab get(Context context) {
        if (sDibbitLab == null) {
            sDibbitLab = new GradeLab(context);
        }
        return sDibbitLab;
    }

    public void updateDibbits() {
        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Grade");
        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Grade");
        query1.whereEqualTo("mUser", ParseUser.getCurrentUser());
        query2.whereEqualTo("isDone", true);


        List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
        queries.add(query1);

        ParseQuery<ParseObject> query = ParseQuery.or(queries);

        // Run the query
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> dibbitList, ParseException e) {

                if (e == null) {
                    // If there are results, update the list of dibbits
                    // and notify the adapter
                    mAssignments.clear();


                    for (ParseObject dibbit : dibbitList) {
                        mAssignments.add((Grade) dibbit);

                    }

                } else {
                    Log.d("Post retrieval", "Error: " + e.getMessage());
                }
            }

        });


    }

    public List<Grade> getDibbits() {
        return mAssignments;
    }

    public Grade getDibbit(UUID id) {
        for (Grade dibbit : mAssignments) {
            if (dibbit.getId().equals(id)) {
                return dibbit;
            }
        }
        return null;
    }


}

