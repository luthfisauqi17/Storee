package com.example.storee;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sessionManager = new SessionManager(getApplicationContext());

        HttpsConfig httpsCallGet = new HttpsConfig();
        httpsCallGet.setMethodtype(HttpsConfig.GET);
        httpsCallGet.setUrl(
                "https://storee-api.000webhostapp.com/public/user/detail/" + String.valueOf(sessionManager.getUserId()));
        HashMap<String, String> paramsPost = new HashMap<>();
        httpsCallGet.setParams(paramsPost);
        new HttpsRequestHandler() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                if(response.length() != 0) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONObject o = (JSONObject) jsonObject.get("data");
                        Log.d("profile", String.valueOf(o.getInt("user_id")));
                        ((TextView)findViewById(R.id.profile_name)).setText(o.getString("user_first_name") + " " + o.getString("user_last_name"));
                        ((TextView)findViewById(R.id.profile_email)).setText(o.getString("user_email"));
                        ((TextView)findViewById(R.id.profile_phone)).setText(o.getString("user_pn"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Log.d("Test Failed", response);
                }
            }
        }.execute(httpsCallGet);
    }

    public void gotoHome(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void gotoWelcome(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Log Out");
        builder.setMessage("Are you sure to Log Out?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sessionManager.setLogin(false);
                sessionManager.setUserId(-1);
                startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}