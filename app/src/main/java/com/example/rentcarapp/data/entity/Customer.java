package com.example.rentcarapp.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Customer {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String fullName;
    public String address;
    public String phone;
    public String email;
    public int segment;
    public int rating;

    @Override
    public String toString() {
        return fullName;
    }
}