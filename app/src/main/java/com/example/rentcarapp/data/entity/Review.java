package com.example.rentcarapp.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Rental.class, parentColumns = "id", childColumns = "orderId", onDelete = ForeignKey.CASCADE))
public class Review {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int orderId;
    public int serviceRating;
    public int carRating;
    public String customerReview;
    public int customerRating;
    @Override
    public String toString() {
        return "Отзыв: " + customerReview + "\n" +
                "Оценка сервиса: " + serviceRating + "\n" +
                "Оценка автомобиля: " + carRating + "\n" +
                "Общая оценка: " + customerRating;
    }
}