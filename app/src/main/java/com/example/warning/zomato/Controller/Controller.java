package com.example.warning.zomato.Controller;


import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.warning.zomato.Adater.ListAdapter;
import com.example.warning.zomato.Model.GetData;
import com.example.warning.zomato.Utils.LatLong;
import com.example.warning.zomato.Utils.LruBitmapCache;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Controller extends Application {
   public static final String TAG=Controller.class.getSimpleName();
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    LruBitmapCache mLruBitmapCache;
    String lat="28.5280357",lon="77.2569252";
    public static final ArrayList<GetData> data = new ArrayList<>();

    public static ProgressDialog progressDialog;

    private static Controller mInstance;
    private static Dialog dialoglogin;
    private static ListAdapter listAdapter;


    public static synchronized Controller getInstance()
    {
        return mInstance;
    }







    public void onCreate()
    {
        super.onCreate();
        mInstance=this;
    }

    public RequestQueue getRequestQueue()
    {
        if(requestQueue==null)
            requestQueue=Volley.newRequestQueue(getApplicationContext());
        return requestQueue;
    }
    public ImageLoader getImageLoader()
    {
        getRequestQueue();
        if(imageLoader==null)
            imageLoader=new ImageLoader(this.requestQueue,new LruBitmapCache());
        return this.imageLoader;

    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

        VolleyLog.d("Adding request to queue: %s", req.getUrl());

        getRequestQueue().add(req);
    }
    public LruBitmapCache getLruBitmapCache() {
        if (mLruBitmapCache == null)
            mLruBitmapCache = new LruBitmapCache();
        return this.mLruBitmapCache;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (requestQueue != null) {
            requestQueue.cancelAll(tag);
        }
    }


}
