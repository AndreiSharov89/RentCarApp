package com.example.rentcarapp.data.entity;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(entity = Customer.class,
                parentColumns = "id",
                childColumns = "customerId",
                onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Car.class,
                parentColumns = "id",
                childColumns = "carId",
                onDelete = ForeignKey.CASCADE)
})
public class Rental {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int customerId;
    public int carId;
    public String rentalDate;
    public String returnDate;
    public String payed;

    @Override
    public String toString() {
        return "Аренда от " + rentalDate + " до " + returnDate + " (Оплата: " + payed + ")";
    }
}