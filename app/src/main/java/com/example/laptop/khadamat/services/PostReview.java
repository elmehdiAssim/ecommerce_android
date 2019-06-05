package com.example.laptop.khadamat.services;

import android.app.Activity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.laptop.khadamat.entities.Message;
import com.example.laptop.khadamat.entities.Review;

import java.util.HashMap;
import java.util.Map;

public class PostReview {

    String url;

    public PostReview(String url, Activity activity) {
        this.url = url;
    }

    public void addReview(final Review r, Activity activity) {

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
                parameters.put("comment", r.getComment() );
                parameters.put("starsNumber", Integer.toString(r.getStarsNumber()));
                parameters.put("publicationDate", r.getPublicationDate());
                parameters.put("idUser", r.getIdUser());
                parameters.put("idService", r.getIdService());

                return parameters;
            }
        };
        queue.add(request);
    }

}
