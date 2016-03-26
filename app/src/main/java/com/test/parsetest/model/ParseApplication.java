package com.test.parsetest.model;

/**
 * Created by Morgan on 12/10/15.
 */

import com.parse.Parse;
import com.parse.ParseACL;

import com.parse.ParseObject;
import com.parse.ParseUser;

import android.app.Application;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(getApplicationContext());
        // Add your initialization code here
        Parse.initialize(this, "f9QqKGZE4METxNFz0hZ6oVB4MW8D5BEAJkww3BgI", "78jffD9sEVBiMA7iGWLjz1eyCgPoh9cKpyZNmMlN");

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        ParseObject.registerSubclass(Assignment.class);
        ParseObject.registerSubclass(Hints.class);
        ParseObject.registerSubclass(Grade.class);
//        ParseObject.registerSubclass(Grades.class);
        // If you would like all objects to be private by default, remove this
        // line.
        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);
    }

}