package com.example.storee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Home");
    }

    public void gotoRecommendation(View v) {
        Intent i = new Intent(this, RecommendationActivity.class);
        startActivity(i);
    }

    public void gotoAllItems(View v) {
        Intent i = new Intent(this, AllItemsActivity.class);
        startActivity(i);
    }

    public void gotoCart(View v) {
        Intent i = new Intent(this, CartActivity.class);
        startActivity(i);
    }

    public void gotoProfile(View v) {
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }
}