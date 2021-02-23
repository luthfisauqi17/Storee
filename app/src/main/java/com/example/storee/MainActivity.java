package com.example.storee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sessionManager = new SessionManager(getApplicationContext());
        Log.d("userid", String.valueOf(sessionManager.getUserId()));
        ((View)findViewById(R.id.recomended_product_card_id)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Hello World", Toast.LENGTH_SHORT).show();
            }
        });
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