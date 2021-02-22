package com.example.storee;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.MyViewHolder>{
    private Context mContext;
    private List<Product> mData;

    public ProductRecyclerViewAdapter(Context mContext, List<Product> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_product, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_product_name.setText(mData.get(position).getName());
        holder.tv_product_price.setText(String.valueOf("Rp." + mData.get(position).getPrice()));
        String imageURL = "https://storee-api.000webhostapp.com/public/assets/product_image/" + mData.get(position).getImage();
        LoadImage loadImage = new LoadImage(holder.iv_product_image);
        loadImage.execute(imageURL);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_product_name;
        TextView tv_product_price;
        ImageView iv_product_image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_product_name = (TextView)itemView.findViewById(R.id.product_name_id);
            tv_product_price = (TextView)itemView.findViewById(R.id.product_price_id);
            iv_product_image = (ImageView)itemView.findViewById(R.id.product_image_id);
        }
    }

    private class LoadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;
        public LoadImage(ImageView iv_product_image) {
            this.imageView = iv_product_image;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String urlLink = strings[0];
            Bitmap bitmap = null;
            try {
                InputStream is = new URL(urlLink).openStream();
                bitmap = BitmapFactory.decodeStream(is);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
            Log.d("Success", "Success, yeay!");
        }
    }


}
