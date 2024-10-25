package com.example.rentcarapp.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.rentcarapp.data.entity.Customer;

import java.util.List;

@Dao
public interface CustomerDao {

    @Query("SELECT * FROM Customer WHERE id = :id")
    Customer getCustomerById(int id);

    @Insert
    void insertCustomer(Customer customer);

    @Delete
    void deleteCustomer(Customer customer);

    @Query("SELECT * FROM Customer")
    List<Customer> getAllCustomers();
}