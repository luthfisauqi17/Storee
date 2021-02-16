package com.example.storee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
                    Intent i = new Intent(SplashScreenActivity.this, WelcomeActivity.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(SplashScreenActivity.this, "Connection failed", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    // parameters void, process void, and result void
    public class CheckConnection extends AsyncTask<Void, Void, Void> {
        String res = ""; // This is the variable to hold the result/response of the operation
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL("https://storee-api.000webhostapp.com/public/check_connection");
                // URLConnection class represent a communications link between the application and a URL
                // Instances of this class can be used both to read from and to write to the resource referenced by the URL
                URLConnection conn = url.openConnection();
                // Representing an input stream of bytes
                InputStream is = conn.getInputStream();
                // An InputStreamReader is a bridge from byte streams to character streams: It reads bytes and decodes them into characters using a specified charset.
                // The charset that it uses may be specified by name or may be given explicitly, or the platform's default charset may be accepted.
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