package com.test.parsetest;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.app.Activity;
import android.content.Intent;
import android.widget.ImageButton;

import com.parse.ParseAnonymousUtils;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    private ImageButton mAssignmentButton;
    private ImageButton mGradesButton;
    private ImageButton mHintButton;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Determine whether the current user is an anonymous user
        if (ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
            // If user is anonymous, send the user to LoginActivity.class
            Intent intent = new Intent(MainActivity.this,
                    LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            // Get current user data from Parse.com
            ParseUser currentUser = ParseUser.getCurrentUser();
        }



        setContentView(R.layout.main_dashboard);
        mAssignmentButton = (ImageButton) findViewById(R.id.assignmentButton);
        mGradesButton = (ImageButton) findViewById(R.id.gradesButton);
        mHintButton = (ImageButton) findViewById(R.id.hintButton);


        mAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // start Assignment Activity
                Intent intent = new Intent(getApplicationContext(), AssignmentListActivity.class);
                startActivity(intent);
            }
        });

        mGradesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start grades activity
                Intent intent = new Intent(getApplicationContext(), GradeListActivity.class);
                startActivity(intent);
            }
        });

        mHintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start helpful hints activity.
                Intent intent = new Intent(getApplicationContext(), HintsActivity.class);
                startActivity(intent);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        super.onCreateOptionsMenu(menu);
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_item_logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void logout() {
        ParseUser.logOut();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }


}
