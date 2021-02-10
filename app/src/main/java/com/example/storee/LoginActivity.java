package com.example.storee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

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
            // We need to make a RequestQueue object for passing the Request to the API
            RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
            // This is the URL of the API
            String url = "https://storee-api.000webhostapp.com/public/user/login";
            // A canned request for retrieving the response body at a given URL as a String
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                // This is the listener for the Success response without any errors
                @Override
                public void onResponse(String response) {
                    // Create a popup toast to inform the user
                    Toast.makeText(LoginActivity.this, "Login SUCCEED", Toast.LENGTH_SHORT).show();
                    // Set the color of the success message
                    loginStatsTV.setTextColor(Color.parseColor("#48A868"));
                    // Set the text to "Login SUCCEED"
                    loginStatsTV.setText("Login SUCCEED");
                    // Create an Intent to move to another activity
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    // Move to another activity
                    startActivity(i);
                }
            }, new Response.ErrorListener() {
                // This is the listener if there is any errors when receiving the response
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Create a popup toast to inform the user
                    Toast.makeText(LoginActivity.this, "Login FAILED", Toast.LENGTH_SHORT).show();
                    // Set the color of the fail message
                    loginStatsTV.setTextColor(Color.parseColor("#F19696"));
                    // Set the text to "Login failed"
                    loginStatsTV.setText("Login failed, please try again");
                }
            }) {
                @Override
                // Overriding the getParams method allows you to build the HashMap and
                // return the object to the Volley request for posting.
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("email", email);
                    params.put("password", password);
                    return params;
                }

                // Returns a list of extra HTTP headers to go along with this request
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    // application/x-www-form-urlencoded represents a URL encoded form.
                    // This is the default value if enctype attribute is not set to anything.
                    params.put("Content-Type", "application/x-www-form-urlencoded");
                    return params;
                }
            };
            // Add a request
            requestQueue.add(stringRequest);
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