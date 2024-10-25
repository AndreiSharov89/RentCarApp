package com.example.rentcarapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.rentcarapp.R;
import com.example.rentcarapp.data.AppDatabase;
import com.example.rentcarapp.data.entity.Car;
import com.example.rentcarapp.data.entity.Customer;
import com.example.rentcarapp.data.entity.Rental;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class AssignCarFragment extends Fragment {

    private Spinner customerSpinner;
    private Spinner carSpinner;
    private Button assignCarButton;
    private AppDatabase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assign_car, container, false);

        db = AppDatabase.getInstance(getContext());

        customerSpinner = view.findViewById(R.id.customer_spinner);
        carSpinner = view.findViewById(R.id.car_spinner);
        assignCarButton = view.findViewById(R.id.assign_car_button);

        loadSpinnersData();

        assignCarButton.setOnClickListener(v -> assignCarToCustomer());

        return view;
    }

    private void loadSpinnersData() {
        new Thread(() -> {
            List<Customer> customers = db.customerDao().getAllCustomers();
            List<Car> cars = db.carDao().getAllCars();

            getActivity().runOnUiThread(() -> {
                ArrayAdapter<Customer> customerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, customers);
                customerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                customerSpinner.setAdapter(customerAdapter);

                ArrayAdapter<Car> carAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, cars);
                carAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                carSpinner.setAdapter(carAdapter);
            });
        }).start();
    }

    private void assignCarToCustomer() {
        new Thread(() -> {
            Customer selectedCustomer = (Customer) customerSpinner.getSelectedItem();
            Car selectedCar = (Car) carSpinner.getSelectedItem();

            if (selectedCustomer == null || selectedCar == null) {
                getActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), "Выберите клиента и автомобиль", Toast.LENGTH_SHORT).show();
                });
                return;
            }

            Rental rental = new Rental();
            rental.customerId = selectedCustomer.id;
            rental.carId = selectedCar.id;
            rental.rentalDate = getCurrentDate();
            rental.returnDate = getRandomReturnDate();
            rental.payed = getRandomPaymentStatus();

            db.rentalDao().insertRental(rental);

            getActivity().runOnUiThread(() -> {
                Toast.makeText(getContext(), "Аренда оформлена", Toast.LENGTH_SHORT).show();
            });
        }).start();
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date());
    }

    private String getRandomReturnDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, new Random().nextInt(14) + 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(calendar.getTime());
    }

    private String getRandomPaymentStatus() {
        return new Random().nextBoolean() ? "Оплачено" : "Не оплачено";
    }
}
