package com.example.storee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
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
        String firstName = ((TextView)findViewById(R.id.firstnameET)).getText().toString();
        String lastName = ((TextView)findViewById(R.id.lastnameET)).getText().toString();
        String email = ((TextView)findViewById(R.id.emailET)).getText().toString();
        String phoneNumber = ((TextView)findViewById(R.id.phoneET)).getText().toString();
        String password = ((TextView)findViewById(R.id.passwordET)).getText().toString();
        String confirmPassword = ((TextView)findViewById(R.id.confirmPasswordET)).getText().toString();
        TextView registerStatsTV = findViewById(R.id.registerStatsTV);

        RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
        String url = "https://storee-api.000webhostapp.com/public/user/register";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(RegisterActivity.this, "Register SUCCESS", Toast.LENGTH_SHORT).show();
                registerStatsTV.setTextColor(Color.parseColor("#48A868"));
                registerStatsTV.setText("Login SUCCESS");
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this, "Login FAILED", Toast.LENGTH_SHORT).show();
                registerStatsTV.setTextColor(Color.parseColor("#F19696"));
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

    public boolean confirmPassword(String password, String confirmPassword) {
        return true;
    }
}