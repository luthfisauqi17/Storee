package com.example.storee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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
        loadProduct("");
    }

    public void gotoHome(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void searchItem(View v) {
        loadProduct(((EditText)findViewById(R.id.all_item_search_et_id)).getText().toString());
    }

    public void loadProduct(String productName) {
        lstItem.clear();
        HttpsConfig httpsCallGet = new HttpsConfig();
        httpsCallGet.setMethodtype(HttpsConfig.GET);
        httpsCallGet.setUrl("https://storee-api.000webhostapp.com/public/product/get_product");
        HashMap<String, String> paramsGet = new HashMap<>();
        paramsGet.put("productName", productName);
        httpsCallGet.setParams(paramsGet);
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

                    RecyclerView myRv = (RecyclerView) findViewById(R.id.all_item_recycleview_id);
                    ProductRecyclerViewAdapter myAdapter =
                            new ProductRecyclerViewAdapter(AllItemsActivity.this, lstItem);
                    myRv.setLayoutManager(
                            new GridLayoutManager(AllItemsActivity.this, 2));
                    myRv.setAdapter(myAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute(httpsCallGet);
    }
}