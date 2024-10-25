package com.example.rentcarapp.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.rentcarapp.data.entity.Review;

import java.util.List;

@Dao
public interface ReviewDao {
    @Query("SELECT * FROM Review WHERE id = :id")
    Review getReviewById(int id);

    @Insert
    void insertReview(Review review);

    @Query("SELECT * FROM Review")
    List<Review> getAllReviews();
}