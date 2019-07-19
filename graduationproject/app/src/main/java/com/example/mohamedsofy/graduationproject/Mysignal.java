package com.example.mohamedsofy.graduationproject;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by M_Sofy on 3/5/2019.
 */
public class Mysignal {
    private static Mysignal mInstance;
    private RequestQueue requestQueue;
    private static Context mCtx;

    private Mysignal(Context context){
        mCtx = context;
        requestQueue = getRequestQueue();
    }

    private RequestQueue getRequestQueue(){
        if(requestQueue == null)
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        return requestQueue;
    }

    public static synchronized Mysignal getInstance(Context context){
        if(mInstance == null){
            mInstance = new Mysignal(context);
        }
        return mInstance;
    }

    public<T> void addToRequestQue(Request<T> request){
        getRequestQueue().add(request);
    }

}
