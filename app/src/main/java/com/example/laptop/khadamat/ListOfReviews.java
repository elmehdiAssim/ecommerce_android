package com.example.laptop.khadamat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.laptop.khadamat.Adapter.AdapterReviews;
import com.example.laptop.khadamat.entities.Review;
import com.example.laptop.khadamat.interfaces.IPAddress;
import com.example.laptop.khadamat.interfaces.VolleyCallback;
import com.example.laptop.khadamat.services.GetReviews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListOfReviews extends AppCompatActivity implements IPAddress {

    String idService;
    GetReviews s;
    String urlReviews = "http://"+ipaddress+"/projects/khadamat_rest_api/userServices/getReviews.php";

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    AdapterReviews adapter;
    List<Review> reviewsList;
    List<String> fullNames;

    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_reviews);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_reviews);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        btnBack = (Button) findViewById(R.id.btnBack);

        idService = getIntent().getExtras().getString("idService");

        s = new GetReviews(urlReviews, ListOfReviews.this);
        s.searchReviews(idService, ListOfReviews.this, new VolleyCallback() {
            @Override
            public void onSuccessResponse(String result) throws JSONException {

                JSONObject jsonObject = null;
                try {

                    jsonObject = new JSONObject(result.toString());
                    String fullName = null;
                    JSONArray reviews = jsonObject.getJSONArray("reviews");
                    reviewsList = new ArrayList<Review>();
                    fullNames = new ArrayList<String>();

                    for (int i = 0; i < reviews.length(); i++) {

                        JSONObject reviewJson = reviews.getJSONObject(i);
                        Review review = new Review();
                        review.setComment(reviewJson.getString("comment"));
                        review.setStarsNumber(Integer.parseInt(reviewJson.getString("starsNumber")));
                        fullName = reviewJson.getString("fullName");
                        reviewsList.add(review);
                        fullNames.add(fullName);

                    }
                    adapter = new AdapterReviews(reviewsList, fullNames);
                    recyclerView.setAdapter(adapter);
                    System.out.println("------------    : "+fullName);

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
