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
    public String [] hints = new String [5];

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




    public List<Hints> updateHints(String category) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Hints");
        query.whereEqualTo("type", "Paper");
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
                        hints[0] = ((Hints) hint).getHintText();
                    }

//                    Toast.makeText(mContext, "PASS" + mHints.get(1).getHintText(),
//                            Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("Post retrieval", "Error: " + e.getMessage());
                }
            }

        });
//        Toast.makeText(mContext, "PASS" + mHints.get(1).getHintText(),
//                Toast.LENGTH_SHORT).show();
        return mHints;
        }


    public String[] getHints() {
        Toast.makeText(mContext, "PASS" + hints[0],
                            Toast.LENGTH_SHORT).show();
        return hints;
    }


}
