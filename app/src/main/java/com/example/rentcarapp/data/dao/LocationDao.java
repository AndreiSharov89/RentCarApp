package com.example.rentcarapp.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.rentcarapp.data.entity.Location;

import java.util.List;

@Dao
public interface LocationDao {

    @Query("SELECT * FROM Location WHERE id = :id")
    Location getLocationById(int id);

    @Insert
    void insertLocation(Location location);

    @Query("SELECT * FROM Location")
    List<Location> getAllLocations();
}
