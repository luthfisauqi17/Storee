package com.example.storee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AllItemsActivity extends AppCompatActivity {

    List<Product> lstItem = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_items);

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
                        lstItem.add(new Product(
                                o.getInt("product_id"),
                                o.getString("product_name"),
                                o.getInt("product_stock"),
                                o.getDouble("product_price"),
                                o.getString("product_image"),
                                o.getString("created_at")));
                    }

                    RecyclerView myrv = (RecyclerView) findViewById(R.id.all_item_recycleview_id);
                    ProductRecyclerViewAdapter myAdapter = new ProductRecyclerViewAdapter(AllItemsActivity.this, lstItem);
                    myrv.setLayoutManager(new GridLayoutManager(AllItemsActivity.this, 2));
                    myrv.setAdapter(myAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute(httpsCallPost);
    }

    public void gotoHome(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}