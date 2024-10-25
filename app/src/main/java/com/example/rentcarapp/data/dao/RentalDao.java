package com.example.rentcarapp.data.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.rentcarapp.data.entity.Rental;

import java.util.List;

@Dao
public interface RentalDao {

    @Insert
    void insertRental(Rental rental);

    @Transaction
    @Query("SELECT * FROM Rental WHERE customerId = :customerId")
    List<Rental> getRentalsForCustomer(int customerId);

    @Query("SELECT * FROM Rental")
    List<Rental> getAllRentals();
}