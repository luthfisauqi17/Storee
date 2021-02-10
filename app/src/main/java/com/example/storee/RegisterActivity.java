package com.example.storee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
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

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }


    /* This function used for Check connection, take the register inputs from user and
    pass it into the API to register the user to the database */
    public void register(View v) {

        // This variable is for storing all required inputs from user
        String firstName = ((EditText)findViewById(R.id.firstnameET)).getText().toString();
        String lastName = ((EditText)findViewById(R.id.lastnameET)).getText().toString();
        String email = ((EditText)findViewById(R.id.emailET)).getText().toString();
        String phoneNumber = ((EditText)findViewById(R.id.phoneET)).getText().toString();
        String password = ((EditText)findViewById(R.id.passwordET)).getText().toString();
        String confirmPassword = ((EditText)findViewById(R.id.confirmPasswordET)).getText().toString();

        // These variables needed for storing the components data from the UI
        TextView registerStatsTV = findViewById(R.id.registerStatsTV);
        EditText firstNameET = ((EditText)findViewById(R.id.firstnameET));
        EditText lastNameET = ((EditText)findViewById(R.id.lastnameET));
        EditText emailET = ((EditText)findViewById(R.id.emailET));
        EditText phoneET = ((EditText)findViewById(R.id.phoneET));
        EditText passwordET = ((EditText)findViewById(R.id.passwordET));
        EditText confirmPasswordET = ((EditText)findViewById(R.id.confirmPasswordET));
        CheckBox termAgreementCB = ((CheckBox)findViewById(R.id.agreementCB));

        // This variable is for storing the error messages
        String errorString = "";
        // This variable indicates if the user inputs have any error or not
        boolean errorExist = false;

        // First of all, we set the all inputs background to netral type of background
        firstNameET.setBackgroundResource(R.drawable.input_box_netral);
        lastNameET.setBackgroundResource(R.drawable.input_box_netral);
        emailET.setBackgroundResource(R.drawable.input_box_netral);
        phoneET.setBackgroundResource(R.drawable.input_box_netral);
        passwordET.setBackgroundResource(R.drawable.input_box_netral);
        confirmPasswordET.setBackgroundResource(R.drawable.input_box_netral);

        // Check if the agreement checkbox is checked or not
        if(!termAgreementCB.isChecked()) {
            // Append the error message to the errorString variable
            errorString += "*Please check the agreement!\n";
            // Set the errorExist boolean to true
            errorExist = true;
        }

        // Check if the firstName input is empty or not
        if(firstName.matches("")) {
            // Set the firstName input field background to warning type of background
            firstNameET.setBackgroundResource(R.drawable.input_box_warning);
            // Append the error message to the errorString variable
            errorString += "*First name input must not empty!\n";
            // Set the errorExist boolean to true
            errorExist = true;
        }

        // Check if the lastName input is empty or not
        if(lastName.matches("")) {
            // Set the lastName input field background to warning type of background
            lastNameET.setBackgroundResource(R.drawable.input_box_warning);
            // Append the error message to the errorString variable
            errorString += "*Last name input must not empty!\n";
            // Set the errorExist boolean to true
            errorExist = true;
        }

        // Check if the email input is empty or not
        if(email.matches("")) {
            // Set the email input field background to warning type of background
            emailET.setBackgroundResource(R.drawable.input_box_warning);
            // Append the error message to the errorString variable
            errorString += "*Email input must not empty!\n";
            // Set the errorExist boolean to true
            errorExist = true;
        }

        // Check if the phoneNumber input is empty or not
        if(phoneNumber.matches("")) {
            // Set the phoneNumber input field background to warning type of background
            phoneET.setBackgroundResource(R.drawable.input_box_warning);
            // Append the error message to the errorString variable
            errorString += "*Phone number input must not empty!\n";
            // Set the errorExist boolean to true
            errorExist = true;
        }

        // Check if the password input is empty or not
        if(password.matches("")) {
            // Set the password input field background to warning type of background
            passwordET.setBackgroundResource(R.drawable.input_box_warning);
            // Append the error message to the errorString variable
            errorString += "*Password input must not empty!\n";
            // Set the errorExist boolean to true
            errorExist = true;
        }
        else {
            // Check if the password input is matched or not
            if(!password.matches(confirmPassword)) {
                // Set the password and confirmPassword input field background to
                // warning type of background
                passwordET.setBackgroundResource(R.drawable.input_box_warning);
                confirmPasswordET.setBackgroundResource(R.drawable.input_box_warning);
                // Append the error message to the errorString variable
                errorString += "*Password is not match with the confirmation password!\n";
                // Set the errorExist boolean to true
                errorExist = true;
            }
        }

        // We need to check if we have error or not, if yes, we cannot process to request API
        if(!errorExist) {
            // We need to make a RequestQueue object for passing the Request to the API
            RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
            // This is the URL of the API
            String url = "https://storee-api.000webhostapp.com/public/user/register";
            // A canned request for retrieving the response body at a given URL as a String
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                // This is the listener for the Success response without any errors
                @Override
                public void onResponse(String response) {
                    // Create a popup toast to inform the user
                    Toast.makeText(RegisterActivity.this, "Register SUCCEED", Toast.LENGTH_SHORT).show();
                    // Set the color of the success message
                    registerStatsTV.setTextColor(Color.parseColor("#48A868"));
                    // Set the text to "Login SUCCEED"
                    registerStatsTV.setText("Register SUCCEED");
                    // Create an Intent to move to another activity
                    Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                    // Move to another activity
                    startActivity(i);
                }
            }, new Response.ErrorListener() {
                // This is the listener if there is any errors when receiving the response
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Create a popup toast to inform the user
                    Toast.makeText(RegisterActivity.this, "Register FAILED", Toast.LENGTH_SHORT).show();
                    // Set the color of the fail message
                    registerStatsTV.setTextColor(Color.parseColor("#F19696"));
                    // Set the text to "Login failed"
                    registerStatsTV.setText("Register failed, please try again");
                }
            }) {
                @Override
                // Overriding the getParams method allows you to build the HashMap and
                // return the object to the Volley request for posting.
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user_first_name", firstName);
                    params.put("user_last_name", lastName);
                    params.put("user_email", email);
                    params.put("user_pn", phoneNumber);
                    params.put("user_password", password);
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
            registerStatsTV.setTextColor(Color.parseColor("#F19696"));
            // Display all errors messages
            registerStatsTV.setText(errorString);
        }
    }
}