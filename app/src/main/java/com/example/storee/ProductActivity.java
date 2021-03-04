package com.example.storee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ProductActivity extends AppCompatActivity {

    boolean inCart = false;
    ImageButton cartBtn;

    SessionManager sessionManager;
    int userId, productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        sessionManager = new SessionManager(getApplicationContext());
        userId = sessionManager.getUserId();

        cartBtn = findViewById(R.id.add_to_cart_btn);

        Intent i = getIntent();
        int id = i.getExtras().getInt("Id");
        productId = id;
        String name = i.getExtras().getString("Name");
        String price = i.getExtras().getString("Price");
        String image = i.getExtras().getString("Image");

        HttpsConfig httpsCallPost = new HttpsConfig();
        httpsCallPost.setMethodtype(HttpsConfig.POST);
        httpsCallPost.setUrl(
                "https://storee-api.000webhostapp.com/public/cart/check_cart");
        HashMap<String, String> paramsPost = new HashMap<>();
        paramsPost.put("user_id", String.valueOf(userId));
        paramsPost.put("product_id", String.valueOf(productId));
        httpsCallPost.setParams(paramsPost);
        new HttpsRequestHandler() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                Log.d("checkCart", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.getBoolean("error"))
                    {
                        ((ImageButton)findViewById(R.id.add_to_cart_btn)).setImageResource(R.drawable.ic_baseline_remove_shopping_cart_24);
                        inCart = true;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute(httpsCallPost);

        ((TextView)findViewById(R.id.product_detail_name_id)).setText(name);
        ((TextView)findViewById(R.id.product_detail_price_id)).setText(price);
        String imageURL = "https://storee-api.000webhostapp.com/public/assets/product_image/" + image;
        LoadImage loadImage = new LoadImage((ImageView)findViewById(R.id.product_detail_image_id));
        loadImage.execute(imageURL);
    }

    public void gotoHome(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void cartAction(View v) {
        if(inCart) {
            inCart = false;
            HttpsConfig httpsCallPost = new HttpsConfig();
            httpsCallPost.setMethodtype(HttpsConfig.POST);
            httpsCallPost.setUrl(
                    "https://storee-api.000webhostapp.com/public/cart/delete_cart");
            HashMap<String, String> paramsPost = new HashMap<>();
            paramsPost.put("user_id", String.valueOf(userId));
            paramsPost.put("product_id", String.valueOf(productId));
            httpsCallPost.setParams(paramsPost);
            new HttpsRequestHandler() {
                @Override
                public void onResponse(String response) {
                    super.onResponse(response);
                    Log.d("delete_item_cart", response.toString());
                }
            }.execute(httpsCallPost);
            ((ImageButton)findViewById(R.id.add_to_cart_btn)).setImageResource(R.drawable.ic_baseline_add_shopping_cart_24_white);
        }
        else {
            inCart = true;
            HttpsConfig httpsCallPost = new HttpsConfig();
            httpsCallPost.setMethodtype(HttpsConfig.POST);
            httpsCallPost.setUrl(
                    "https://storee-api.000webhostapp.com/public/cart/insert_cart");
            HashMap<String, String> paramsPost = new HashMap<>();
            paramsPost.put("user_id", String.valueOf(userId));
            paramsPost.put("product_id", String.valueOf(productId));
            httpsCallPost.setParams(paramsPost);
            new HttpsRequestHandler() {
                @Override
                public void onResponse(String response) {
                    super.onResponse(response);
                    Log.d("insert_item_cart", response.toString());
                }
            }.execute(httpsCallPost);
            ((ImageButton)findViewById(R.id.add_to_cart_btn)).setImageResource(R.drawable.ic_baseline_remove_shopping_cart_24);
        }
    }
}