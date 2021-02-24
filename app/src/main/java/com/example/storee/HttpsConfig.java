package com.example.storee;

import java.util.HashMap;

// This class is for set and get the HTTP configurations
public class HttpsConfig {
    // id of each HTTP request methods
    public static final int GET = 1;
    public static final int POST = 2;

    // URL, method type, and parameters configurations
    private String url;
    private int methodtype;
    private HashMap<String,String> params ;


    // Setters and getters
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public int getMethodtype() {
        return methodtype;
    }
    public void setMethodtype(int methodtype) {
        this.methodtype = methodtype;
    }
    public HashMap<String, String> getParams() {
        return params;
    }
    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }
}
