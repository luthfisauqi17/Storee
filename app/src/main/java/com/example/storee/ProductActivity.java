package com.example.storee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Intent i = getIntent();
        String name = i.getExtras().getString("Name");
        String price = i.getExtras().getString("Price");
        String image = i.getExtras().getString("Image");

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
}