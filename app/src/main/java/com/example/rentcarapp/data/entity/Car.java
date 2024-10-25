package com.example.rentcarapp.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        foreignKeys = @ForeignKey(
                entity = Location.class,
                parentColumns = "id",
                childColumns = "locationId",
                onDelete = ForeignKey.CASCADE
        )
)
public class Car {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String brand;
    public String model;
    public int year;
    public String registrationNumber;
    public int locationId;
    public String segment;
    public String condition;

    @Override
    public String toString() {
        return brand + " " + model + " (" + registrationNumber + ")";
    }
}