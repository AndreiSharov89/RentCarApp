package com.example.rentcarapp.data.entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Location {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String address;

    @Override
    public String toString() {
        return address;
    }
}
