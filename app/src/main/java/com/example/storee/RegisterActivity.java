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

    public void register(View v) {
//        Variables
        String firstName = ((EditText)findViewById(R.id.firstnameET)).getText().toString();
        String lastName = ((EditText)findViewById(R.id.lastnameET)).getText().toString();
        String email = ((EditText)findViewById(R.id.emailET)).getText().toString();
        String phoneNumber = ((EditText)findViewById(R.id.phoneET)).getText().toString();
        String password = ((EditText)findViewById(R.id.passwordET)).getText().toString();
        String confirmPassword = ((EditText)findViewById(R.id.confirmPasswordET)).getText().toString();

        TextView registerStatsTV = findViewById(R.id.registerStatsTV);
        EditText firstNameET = ((EditText)findViewById(R.id.firstnameET));
        EditText lastNameET = ((EditText)findViewById(R.id.lastnameET));
        EditText emailET = ((EditText)findViewById(R.id.emailET));
        EditText phoneET = ((EditText)findViewById(R.id.phoneET));
        EditText passwordET = ((EditText)findViewById(R.id.passwordET));
        EditText confirmPasswordET = ((EditText)findViewById(R.id.confirmPasswordET));
        CheckBox termAgreementCB = ((CheckBox)findViewById(R.id.agreementCB));

        String errorString = "";
        boolean errorExist = false;

        firstNameET.setBackgroundResource(R.drawable.input_box_netral);
        lastNameET.setBackgroundResource(R.drawable.input_box_netral);
        emailET.setBackgroundResource(R.drawable.input_box_netral);
        phoneET.setBackgroundResource(R.drawable.input_box_netral);
        passwordET.setBackgroundResource(R.drawable.input_box_netral);
        confirmPasswordET.setBackgroundResource(R.drawable.input_box_netral);


        if(!termAgreementCB.isChecked()) {
            errorString += "*Please check the agreement!\n";
            errorExist = true;
        }

        if(firstName.matches("")) {
            firstNameET.setBackgroundResource(R.drawable.input_box_warning);
            errorString += "*First name input must not empty!\n";
            errorExist = true;
        }

        if(lastName.matches("")) {
            lastNameET.setBackgroundResource(R.drawable.input_box_warning);
            errorString += "*Last name input must not empty!\n";
            errorExist = true;
        }

        if(email.matches("")) {
            emailET.setBackgroundResource(R.drawable.input_box_warning);
            errorString += "*Email input must not empty!\n";
            errorExist = true;
        }

        if(phoneNumber.matches("")) {
            phoneET.setBackgroundResource(R.drawable.input_box_warning);
            errorString += "*Phone number input must not empty!\n";
            errorExist = true;
        }

        if(password.matches("")) {
            passwordET.setBackgroundResource(R.drawable.input_box_warning);
            errorString += "*Password input must not empty!\n";
            errorExist = true;
        }
        else {
            if(!password.matches(confirmPassword)) {
                passwordET.setBackgroundResource(R.drawable.input_box_warning);
                confirmPasswordET.setBackgroundResource(R.drawable.input_box_warning);
                errorString += "*Password is not match with the confirmation password!\n";
                errorExist = true;
            }
        }

        if(!errorExist) {
//        Request API
            RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
            String url = "https://storee-api.000webhostapp.com/public/user/register";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(RegisterActivity.this, "Register SUCCEED", Toast.LENGTH_SHORT).show();
                    registerStatsTV.setTextColor(Color.parseColor("#48A868"));
                    registerStatsTV.setText("Register SUCCEED");
                    Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(i);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(RegisterActivity.this, "Register FAILED", Toast.LENGTH_SHORT).show();
                    registerStatsTV.setTextColor(Color.parseColor("#F19696"));
                    registerStatsTV.setText("Register failed, please try again");
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user_first_name", firstName);
                    params.put("user_last_name", lastName);
                    params.put("user_email", email);
                    params.put("user_pn", phoneNumber);
                    params.put("user_password", password);
                    return params;
                }

                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/x-www-form-urlencoded");
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
        else
        {
            registerStatsTV.setTextColor(Color.parseColor("#F19696"));
            registerStatsTV.setText(errorString);
        }
    }
}