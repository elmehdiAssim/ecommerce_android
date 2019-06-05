package com.example.laptop.khadamat.services;

import android.app.Activity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.laptop.khadamat.interfaces.VolleyCallback;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class GetServices {

    String url;

    public GetServices(String url, Activity activity) {
        this.url = url;
    }

    public void searchServices(Activity activity, final VolleyCallback callback) {

        RequestQueue queue = Volley.newRequestQueue(activity);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    callback.onSuccessResponse(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("************ error : "+error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parameters  = new HashMap<String, String>();

                return parameters;
            }
        };
        queue.add(request);
    }
}
