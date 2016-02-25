package com.test.parsetest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by Morgan on 2/8/16.
 */
public class SignupActivity extends Activity {
    Button btnRegister;
    String fullnametxt;
    String usernametxt;
    String passwordtxt;
    EditText password;
    EditText username;
    EditText fullname;
    Spinner classSection;
    Spinner classYear;
    TextView class_section_dropdown, class_year_dropdown;

    /**
     * Called when the activity is first created.
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from main.xml
        setContentView(R.layout.activity_register);


        // Locate EditTexts in main.xml
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        fullname = (EditText) findViewById(R.id.name);
        class_section_dropdown = (TextView) findViewById(R.id.class_section_dropdown_label);
        class_year_dropdown = (TextView) findViewById(R.id.class_year_dropdown_label);
        classSection = (Spinner) findViewById(R.id.class_section_dropdown);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.class_section_options, android.R.layout.simple_spinner_dropdown_item);
        adapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        classSection.setAdapter(adapter);

        classYear = (Spinner) findViewById(R.id.class_year_dropdown);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.class_year_options, android.R.layout.simple_spinner_dropdown_item);
        adapter2
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        classYear.setAdapter(adapter2);

        // Locate Buttons in main.xml
        btnRegister = (Button) findViewById(R.id.btnRegister);

                btnRegister.setOnClickListener(new View.OnClickListener() {

                    // STORE MORE THAN USERNAME AND PASSWORD IN PARSE DB

                    public void onClick(View arg0) {
                        // Retrieve the text entered from the EditText
                        usernametxt = username.getText().toString();
                        passwordtxt = password.getText().toString();
//                        fullnametxt = fullname.getText().toString();

                        // Force user to fill up the form
                        if (usernametxt.equals("") || passwordtxt.equals("")) {
                            Toast.makeText(getApplicationContext(),
                                    "Please complete the sign up form",
                                    Toast.LENGTH_LONG).show();

                        } else {
                            // Save new user data into Parse.com Data Storage
                            ParseUser user = new ParseUser();
                            user.setUsername(usernametxt);
                            user.setPassword(passwordtxt);
                            user.signUpInBackground(new SignUpCallback() {
                                                        public void done(ParseException e) {
                                                            if (e == null) {
                                                                // Show a simple Toast message upon successful registration
                                                                Toast.makeText(getApplicationContext(),
                                                                        "Successfully Signed up,no logging you in.",
                                                                        Toast.LENGTH_LONG).show();

                                                                // Send data to Parse.com for verification
                                                                ParseUser.logInInBackground(usernametxt, passwordtxt, new LogInCallback() {
                                                                    public void done(ParseUser user, ParseException e) {
                                                                        System.out.println("Inside done()");
                                                                        if ((e == null) & (user != null)) {
                                                                            // Valid user... Let them in the app
                                                                            startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                                                            Toast.makeText(getApplicationContext(),
                                                                                    ("Welcome back " + usernametxt),
                                                                                    Toast.LENGTH_SHORT).show();
                                                                            finish();
                                                                        } else {
                                                                            Toast.makeText(getApplicationContext(), "No such user exists, please signup or try again", Toast.LENGTH_LONG).show();
                                                                        }
                                                                    }
                                                                });


                                                            } else {
                                                                Toast.makeText(getApplicationContext(),
                                                                        "Sign up Error", Toast.LENGTH_LONG).show();
                                                            }
                                                        }
                                                    }

                            );
                        }


                    }

                });


            }


    }
