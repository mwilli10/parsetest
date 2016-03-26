package com.test.parsetest.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.UUID;

/**
 * Created by Morgan on 2/15/16.
 */
@ParseClassName("Hints")
public class Hints extends ParseObject {


    private UUID mId;

    public int getHelpfulCnt(){
        return getInt("helpfulCnt");
    }

    public void incrementHelpful() {
        int cnt = getHelpfulCnt()+1;

        put("helpfulCnt", cnt);
    }


    public void decrementHelpful() {
        int cnt = getHelpfulCnt()-1;

        put("helpfulCnt",cnt);
    }

    public String getHintText(){
        return getString("hint");
    }


    public String getType(){
        return getString("type");
    }
}
