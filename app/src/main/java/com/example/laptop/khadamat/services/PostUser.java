package com.example.laptop.khadamat.services;

import android.app.Activity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.laptop.khadamat.entities.User;

import java.util.HashMap;
import java.util.Map;

public class PostUser {

    String url;

    public PostUser(String url, Activity activity) {
        this.url = url;
    }

    public void postUser(final User user, Activity activity) {

        RequestQueue queue = Volley.newRequestQueue(activity);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("***********response :"+response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("************error :"+error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parameters  = new HashMap<String, String>();
                parameters.put("fullName", user.getFullName());
                parameters.put("email", user.getEmail());
                parameters.put("username", user.getUsername());
                parameters.put("password", user.getPassword());
                parameters.put("userType", user.getUserType());

                return parameters;
            }
        };
        queue.add(request);
    }

}
