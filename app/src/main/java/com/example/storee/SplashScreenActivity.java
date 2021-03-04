package com.example.storee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new CheckConnection() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if (response.length() != 0) {
                    Intent i = new Intent(SplashScreenActivity.this,
                            WelcomeActivity.class);
                    startActivity(i);
                }
                else
                    Toast.makeText(SplashScreenActivity.this,
                            "Connection failed!, please try again later..",
                            Toast.LENGTH_SHORT).show();
                Log.d("Info", response);
            }
        }.execute();
    }


    public class CheckConnection extends AsyncTask<Void, Void, Void> {
        String res = ""; // This is the variable to hold the result/response of the operation
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL("https://storee-api.000webhostapp.com/public/check_connection");
                URLConnection conn = url.openConnection();
                InputStream is = conn.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                int data = isr.read();
                while(data != -1) {
                    char curr = (char) data;
                    res += curr;
                    data = isr.read();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            onResponse(res);
        }

        public void onResponse(String response) {  }
    }
}