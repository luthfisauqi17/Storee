package com.example.storee;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class HttpsRequestHandler extends AsyncTask<HttpsConfig, String, String> {

    private static final String UTF_8 = "UTF-8";

    @Override
    protected String doInBackground(HttpsConfig... params) {
        HttpsURLConnection conn = null;
        HttpsConfig httpsConfig = params[0];
        StringBuilder response = new StringBuilder();
        try{
            String dataParams = getDataString(httpsConfig.getParams(), httpsConfig.getMethodtype());
            URL url = new URL(httpsConfig.getMethodtype() == HttpsConfig.GET ? httpsConfig.getUrl()
                    + dataParams : httpsConfig.getUrl());
            conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod(httpsConfig.getMethodtype() == HttpsConfig.GET ? "GET":"POST");
            if(httpsConfig.getParams() != null && httpsConfig.getMethodtype() == HttpsConfig.POST){
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, UTF_8));
                writer.append(dataParams);
                writer.flush();
                writer.close();
                os.close();
            }
            int responseCode = conn.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                String line ;
                BufferedReader br =
                        new BufferedReader( new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null){
                    response.append(line);
                }
            }
            else {
                response.append("");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return response.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        onResponse(s);
    }

    public void onResponse(String response){

    }



    private String getDataString(HashMap<String,String> params, int methodType)
            throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean isFirst = true;
        for(Map.Entry<String,String> entry : params.entrySet()){
            if (isFirst){
                isFirst = false;
                if(methodType == HttpsConfig.GET){
                    result.append("?");
                }
            }else{
                result.append("&");
            }
            result.append(URLEncoder.encode(entry.getKey(), UTF_8));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), UTF_8));
        }
        return result.toString();
    }
}
