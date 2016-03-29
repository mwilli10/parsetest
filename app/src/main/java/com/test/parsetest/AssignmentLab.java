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


/**
 * Created by Morgan on 2/2/16.
 */
public class AssignmentLab {


        private static AssignmentLab sDibbitLab;
        private Context mContext;
        private boolean mDidDataSetChange = false;


        private List<Assignment> mAssignments;

        private AssignmentLab(Context context) {
            mContext = context;
            mAssignments = new ArrayList<>();
        }


        public static AssignmentLab get(Context context) {
            if (sDibbitLab == null) {
                sDibbitLab = new AssignmentLab(context);
            }
            return sDibbitLab;
        }

        public void updateDibbits() {
            ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Assignment");
            ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Assignment");
            query1.whereEqualTo("mUser", ParseUser.getCurrentUser());
            query1.whereEqualTo("mIsDone", false);


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
                            mAssignments.add((Assignment) dibbit);

                        }

                    } else {
                        Log.d("Post retrieval", "Error: " + e.getMessage());
                    }
                }

            });


        }



        public List<Assignment> getDibbits() {
            return mAssignments;
        }

        public Assignment getDibbit(UUID id) {
            for (Assignment dibbit : mAssignments) {
                if (dibbit.getId().equals(id)) {
                    return dibbit;
                }
            }
            return null;
        }

        public void addDibbit(Assignment c) {
            mAssignments.add(c);
        }




        }

