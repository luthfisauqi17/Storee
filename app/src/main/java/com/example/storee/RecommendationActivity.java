package com.example.storee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class RecommendationActivity extends AppCompatActivity {

    SessionManager sessionManager;
    int userId;

    List<Product> productList = new ArrayList<>();
    int productId, likedProductId;

    int randNum;
    int secondsPassed = 0;

    Timer timer = new Timer();
    TimerTask task = new TimerTask() {

        @Override
        public void run() {
            secondsPassed++;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recomendation);
        sessionManager = new SessionManager(getApplicationContext());
        userId = sessionManager.getUserId();

        timer.scheduleAtFixedRate(task, 0, 1000);
        disableAllButton();
        productList.clear();
        HttpsConfig httpsCallPost = new HttpsConfig();
        httpsCallPost.setMethodtype(HttpsConfig.GET);
        httpsCallPost.setUrl("https://storee-api.000webhostapp.com/public/product/get_product");
        HashMap<String, String> paramsPost = new HashMap<>();
        httpsCallPost.setParams(paramsPost);
        new HttpsRequestHandler() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                Log.d("Response", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("data");

                    for(int i=0; i<array.length(); i++)
                    {
                        JSONObject o = array.getJSONObject(i);
                        productList.add(new Product(
                                o.getInt("product_id"),
                                o.getString("product_name"),
                                o.getInt("product_stock"),
                                o.getDouble("product_price"),
                                o.getString("product_image"),
                                o.getString("created_at")));
                    }
                    Log.d("List", productList.toString());
                    generateProductDisplay();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute(httpsCallPost);
    }

    public void likeProduct(View v)
    {
        disableAllButton();
        likedProductId = productId;
        String info =
                "Liked product id: " + String.valueOf(likedProductId) + "\n" +
                "Duration: " + String.valueOf(secondsPassed) + "\n" +
                "User id: " + String.valueOf(userId);
        Log.d("Swipee info", info);
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
        productList.remove(randNum);
        if(productList.size() == 0) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            Toast.makeText(this, "That's it for now, no more recommendation item left!", Toast.LENGTH_SHORT).show();
        }
        else generateProductDisplay();
    }

    public void gotoHome(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void generateProductDisplay() {
        secondsPassed = 0;
        Random rand = new Random();
        randNum = rand.nextInt(productList.size());
        productId = productList.get(randNum).getId();
        ((TextView)findViewById(R.id.recomendation_name_id)).setText(productList.get(randNum).getName());
        ((TextView)findViewById(R.id.recomendation_price_id)).setText(String.valueOf("Rp." + productList.get(randNum).getPrice()));
        String imageURL = "https://storee-api.000webhostapp.com/public/assets/product_image/" + productList.get(randNum).getImage();
        ((ImageView)findViewById(R.id.recomendation_image_id)).setImageBitmap(null);
        LoadImage loadImage = new LoadImage(((ImageView)findViewById(R.id.recomendation_image_id)));
        loadImage.execute(imageURL);
        enableAllButton();
    }

    public void disableAllButton()
    {
        ((ImageButton)findViewById(R.id.dislike_prod_id)).setEnabled(false);
        ((ImageButton)findViewById(R.id.cart_prod_id)).setEnabled(false);
        ((ImageButton)findViewById(R.id.like_prod_id)).setEnabled(false);
    }

    public void enableAllButton() {
        ((ImageButton)findViewById(R.id.dislike_prod_id)).setEnabled(true);
        ((ImageButton)findViewById(R.id.cart_prod_id)).setEnabled(true);
        ((ImageButton)findViewById(R.id.like_prod_id)).setEnabled(true);
    }
}