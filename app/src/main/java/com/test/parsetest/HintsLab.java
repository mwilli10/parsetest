package com.test.parsetest;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.test.parsetest.model.Assignment;
import com.test.parsetest.model.Hints;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Morgan on 2/16/16.
 */
public class HintsLab {

    private static HintsLab sHintsLab;
    private Context mContext;
    private boolean mDidDataSetChange = false;


    public List<Hints> mHints;
    public String[] hints = new String[5];

    private HintsLab(Context context) {
        mContext = context;
        mHints = new ArrayList<>();
    }


    public static HintsLab get(Context context) {
        if (sHintsLab == null) {
            sHintsLab = new HintsLab(context);
        }
        return sHintsLab;
    }


    public void updateHints() {

        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Hints");
        query1.whereEqualTo("type", "Paper");

        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Hints");
        query1.whereEqualTo("type", "Project");

        ParseQuery<ParseObject> query3 = ParseQuery.getQuery("Hints");
        query1.whereEqualTo("type", "Exam");

        List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
        queries.add(query1);
        queries.add(query2);
        queries.add(query3);

        ParseQuery<ParseObject> query = ParseQuery.or(queries);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    mHints.clear();
                    Toast.makeText(mContext, "PASS",
                            Toast.LENGTH_SHORT).show();
                    for (ParseObject hint : objects) {
                        mHints.add((Hints) hint);
//                        Toast.makeText(mContext, "PASS" + ((Hints) hint).getHintText(),
//                                Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Log.d("Post retrieval", "Error: " + e.getMessage());
                }
            }

        });

    }


    public List<Hints> getHintsAll() {

        return mHints;
    }

    public List<Hints> getHints(String category) {
        List<Hints> hints = new ArrayList();
        int i;
        switch (category) {
            case "Paper":
                for (i = 0; i < mHints.size(); i++) {
                    if (mHints.get(i).getType().equals("Paper")) {
                        hints.add(mHints.get(i));
                    }
                }
                Toast.makeText(mContext , hints.toString(),
                        Toast.LENGTH_SHORT).show();
                return hints;

            case "Project":
                for (i = 0; i < mHints.size(); i++) {
                    if (mHints.get(i).getType().equals("Project")) {
                        hints.add(mHints.get(i));
                    }
                }
                return hints;
            case "Exam":
                for (i = 0; i < mHints.size(); i++) {
                    if (mHints.get(i).getType().equals("Exam")) {
                        hints.add(mHints.get(i));
                    }
                }
                return hints;

            default:
                return hints;
        }
    }

}
