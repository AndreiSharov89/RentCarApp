package com.example.rentcarapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentcarapp.R;
import com.example.rentcarapp.data.entity.Review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private List<Review> reviews;

    public ReviewAdapter(List<Review> reviews) {
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.customerReview.setText(review.customerReview);
        holder.serviceRating.setText("Оценка сервиса: " + review.serviceRating);
        holder.carRating.setText("Оценка автомобиля: " + review.carRating);
        holder.customerRating.setText("Общая оценка: " + review.customerRating);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    static class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView customerReview;
        TextView serviceRating;
        TextView carRating;
        TextView customerRating;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            customerReview = itemView.findViewById(R.id.customer_review);
            serviceRating = itemView.findViewById(R.id.service_rating);
            carRating = itemView.findViewById(R.id.car_rating);
            customerRating = itemView.findViewById(R.id.customer_rating);
        }
    }
}
