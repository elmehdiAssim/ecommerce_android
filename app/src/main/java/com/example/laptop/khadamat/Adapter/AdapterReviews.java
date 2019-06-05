package com.example.laptop.khadamat.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.laptop.khadamat.R;
import com.example.laptop.khadamat.entities.Review;

import org.w3c.dom.Text;

import java.util.List;

public class AdapterReviews extends RecyclerView.Adapter<AdapterReviews.ViewHolder> {

    private List<Review> reviews;
    private List<String> fullNames;

    public AdapterReviews(List<Review> reviews, List<String> fullNames) {
        this.reviews = reviews;
        this.fullNames = fullNames;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_list_of_reviews, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.fullNameReview.setText(this.fullNames.get(position));
        holder.comment.setText(reviews.get(position).getComment());
        holder.ratingBarReview.setRating(reviews.get(position).getStarsNumber());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public int currentItem;
        public TextView fullNameReview;
        public TextView comment;
        public RatingBar ratingBarReview;


        public ViewHolder(View itemView) {
            super(itemView);
            fullNameReview = (TextView) itemView.findViewById(R.id.fullNameReview);
            comment = (TextView) itemView.findViewById(R.id.comment);
            ratingBarReview = (RatingBar) itemView.findViewById(R.id.ratingBarReview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                }
            });
        }
    }



}
