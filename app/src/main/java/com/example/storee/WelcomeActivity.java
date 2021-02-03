package com.example.storee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void gotoLogin(View v)
    {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    public void gotoRegister(View v)
    {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }
}