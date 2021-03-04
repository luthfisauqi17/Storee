package com.example.storee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    SessionManager sessionManager;

    int prod_id;
    String prod_name, prod_price, prod_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sessionManager = new SessionManager(getApplicationContext());
        Log.d("userid", String.valueOf(sessionManager.getUserId()));

        HttpsConfig httpsCallGet = new HttpsConfig();
        httpsCallGet.setMethodtype(HttpsConfig.GET);
        httpsCallGet.setUrl(
                "https://storee-api.000webhostapp.com/public/recommendation/get_recommendation");
        HashMap<String, String> paramsPost = new HashMap<>();
        paramsPost.put("userId", String.valueOf(sessionManager.getUserId()));
        httpsCallGet.setParams(paramsPost);
        new HttpsRequestHandler() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                Log.d("Length:", String.valueOf(response.length()));
                if(response.length() != 0) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray array = jsonObject.getJSONArray("data");
                        JSONObject o = array.getJSONObject(0);
                        prod_id = o.getInt("product_id");
                        prod_name = o.getString("product_name");
                        prod_price = o.getString("product_price");
                        prod_image = o.getString("product_image");

                        ((TextView)findViewById(R.id.homepage_name_id)).setText(
                                o.getString("product_name"));
                        ((TextView)findViewById(R.id.homepage_price_id)).setText("Rp. " +
                                o.getString("product_price"));
                        ((ImageView)findViewById(R.id.homepage_image_id)).setImageBitmap(null);
                        String imageURL =
                                "https://storee-api.000webhostapp.com/public/assets/product_image/"
                                + o.getString("product_image");
                        LoadImage loadImage = new LoadImage(((ImageView)findViewById(
                                R.id.homepage_image_id)));
                        loadImage.execute(imageURL);
                        Log.d("homepage", array.getJSONObject(0).toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Log.d("Test Failed", response);
                }
            }
        }.execute(httpsCallGet);



        ((View)findViewById(R.id.recomended_product_card_id)).setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ProductActivity.class);
                i.putExtra("Id", prod_id);
                i.putExtra("Name", prod_name);
                i.putExtra("Price", "Rp. " + prod_price);
                i.putExtra("Image", prod_image);
                startActivity(i);
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