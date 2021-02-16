package com.example.storee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    /* This function used for Check connection, take the login inputs from user and
    pass it into the API to check whether the user is listed in database or not */
    public void login(View v) {

        // This variable is for storing the email input from user
        String email = ((EditText) findViewById(R.id.emailEditText)).getText().toString();
        // This variable is for storing the password input from user
        String password = ((EditText) findViewById(R.id.passwordEditText)).getText().toString();

        // These variables needed for storing the components data from the UI
        TextView loginStatsTV = ((TextView) findViewById(R.id.loginStatusTV));
        EditText emailET = ((EditText) findViewById(R.id.emailEditText));
        EditText passwordET = ((EditText) findViewById(R.id.passwordEditText));

        // This variable is for storing the error messages
        String errorString = "";
        // This variable indicates if the user inputs have any error or not
        boolean errorExist = false;

        // First of all, we set the all inputs background to netral type of background
        emailET.setBackgroundResource(R.drawable.input_box_netral);
        passwordET.setBackgroundResource(R.drawable.input_box_netral);

        // Check if the email input is empty or not
        if (email.matches("")) {
            // Set the email input field background to warning type of background
            emailET.setBackgroundResource(R.drawable.input_box_warning);
            // Append the error message to the errorString variable
            errorString += "*Email input must not empty!\n";
            // Set the errorExist boolean to true
            errorExist = true;
        }

        // Check if the password input is empty or not
        if (password.matches("")) {
            // Set the password input field background to warning type of background
            passwordET.setBackgroundResource(R.drawable.input_box_warning);
            // Append the error message to the errorString variable
            errorString += "*Password input must not empty!\n";
            // Set the errorExist boolean to true
            errorExist = true;
        }

        // We need to check if we have error or not, if yes, we cannot process to request API
        if (!errorExist) {
            HttpConfig httpCallPost = new HttpConfig();
            httpCallPost.setMethodtype(HttpConfig.POST);
            httpCallPost.setUrl("https://storee-api.000webhostapp.com/public/user/login");
            HashMap<String, String> paramsPost = new HashMap<>();
            paramsPost.put("email", email);
            paramsPost.put("password", password);
            httpCallPost.setParams(paramsPost);
            new HttpsRequestHandler() {
                @Override
                public void onResponse(String response) {
                    super.onResponse(response);
                    Log.d("Length:", String.valueOf(response.length()));
                    if(response.length() != 0) {
                        loginStatsTV.setTextColor(Color.parseColor("#48A868"));
                        loginStatsTV.setText("Login SUCCEED");
                        Log.d("Test Success", response);
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                    else {
                        loginStatsTV.setTextColor(Color.parseColor("#F19696"));
                        loginStatsTV.setText("Login FAILED");
                        Log.d("Test Failed", response);
                    }
                }
            }.execute(httpCallPost);
        }
        // This means we have some errors, and we need to let the user know what the error are
        else {
            // Set the text color to reddish color
            loginStatsTV.setTextColor(Color.parseColor("#F19696"));
            // Display all errors messages
            loginStatsTV.setText(errorString);
        }
    }
}