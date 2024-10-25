package com.example.rentcarapp.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.rentcarapp.data.dao.CarDao;
import com.example.rentcarapp.data.dao.CustomerDao;
import com.example.rentcarapp.data.dao.LocationDao;
import com.example.rentcarapp.data.dao.RentalDao;
import com.example.rentcarapp.data.dao.ReviewDao;
import com.example.rentcarapp.data.entity.Car;
import com.example.rentcarapp.data.entity.Customer;
import com.example.rentcarapp.data.entity.Location;
import com.example.rentcarapp.data.entity.Rental;
import com.example.rentcarapp.data.entity.Review;

@Database(entities = {Customer.class, Car.class, Rental.class, Location.class, Review.class,}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract CustomerDao customerDao();
    public abstract CarDao carDao();
    public abstract RentalDao rentalDao();
    public abstract LocationDao locationDao();
    public abstract ReviewDao reviewDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "rental-db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}