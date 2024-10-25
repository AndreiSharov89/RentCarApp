package com.example.rentcarapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentcarapp.R;
import com.example.rentcarapp.data.AppDatabase;
import com.example.rentcarapp.data.entity.Review;
import com.example.rentcarapp.ui.adapters.ReviewAdapter;

import java.util.List;

public class ReviewFragment extends Fragment {

    private RecyclerView recyclerView;
    private ReviewAdapter reviewAdapter;
    private AppDatabase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);

        db = AppDatabase.getInstance(getContext());
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        new Thread(() -> {
            List<Review> reviews = db.reviewDao().getAllReviews();

            getActivity().runOnUiThread(() -> {
                reviewAdapter = new ReviewAdapter(reviews);
                recyclerView.setAdapter(reviewAdapter);
            });
        }).start();

        return view;
    }
}
