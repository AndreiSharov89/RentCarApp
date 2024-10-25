package com.example.rentcarapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.rentcarapp.R;
import com.example.rentcarapp.data.AppDatabase;
import com.example.rentcarapp.data.entity.Car;
import com.example.rentcarapp.data.entity.Customer;
import com.example.rentcarapp.data.entity.Rental;

import java.util.List;

public class RentalInfoFragment extends Fragment {

    private AppDatabase db;
    private TextView rentalInfoTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rental_info, container, false);

        rentalInfoTextView = view.findViewById(R.id.rental_info_textview);
        db = AppDatabase.getInstance(getContext());

        loadRentalInfo();

        return view;
    }

    private void loadRentalInfo() {
        new Thread(() -> {
            List<Rental> rentals = db.rentalDao().getAllRentals();
            StringBuilder rentalInfo = new StringBuilder();
            for (Rental rental : rentals) {
                Customer customer = db.customerDao().getCustomerById(rental.customerId);
                Car car = db.carDao().getCarById(rental.carId);
                rentalInfo.append("Клиент: ").append(customer.fullName).append("\n")
                        .append("Автомобиль: ").append(car.brand).append(" ").append(car.model).append("\n")
                        .append("Дата аренды: ").append(rental.rentalDate).append("\n")
                        .append("Дата возврата: ").append(rental.returnDate).append("\n")
                        .append("Статус оплаты: ").append(rental.payed).append("\n\n");
            }

            getActivity().runOnUiThread(() -> rentalInfoTextView.setText(rentalInfo.toString()));
        }).start();
    }
}
