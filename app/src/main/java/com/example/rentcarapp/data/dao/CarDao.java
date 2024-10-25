package com.example.rentcarapp.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.rentcarapp.data.entity.Car;

import java.util.List;

@Dao
public interface CarDao {

    @Query("SELECT * FROM Car WHERE id = :id")
    Car getCarById(int id);

    @Insert
    void insertCar(Car car);

    @Query("SELECT * FROM Car")
    List<Car> getAllCars();
}