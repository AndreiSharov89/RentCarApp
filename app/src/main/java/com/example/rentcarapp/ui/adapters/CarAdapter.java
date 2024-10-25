package com.example.rentcarapp.ui.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentcarapp.R;
import com.example.rentcarapp.data.entity.Car;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    private List<Car> cars;

    public CarAdapter(List<Car> cars) {
        this.cars = cars;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car car = cars.get(position);

        String numberedBrand = (position + 1) + ". " + car.brand;
        holder.carNumber.setText(numberedBrand);
        holder.brand.setText(car.brand);
        holder.model.setText(car.model);
        holder.year.setText(String.valueOf(car.year));
        holder.registrationNumber.setText(car.registrationNumber);
        holder.segment.setText(car.segment);
        holder.condition.setText(car.condition);
    }

    @Override
    public int getItemCount() {
        Log.d("CarAdapter", "Number of items: " + cars.size());
        return cars.size();
    }

    static class CarViewHolder extends RecyclerView.ViewHolder {

        TextView carNumber;
        TextView brand;
        TextView model;
        TextView year;
        TextView registrationNumber;
        TextView segment;
        TextView condition;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            carNumber = itemView.findViewById(R.id.car_number);
            brand = itemView.findViewById(R.id.brand);
            model = itemView.findViewById(R.id.model);
            year = itemView.findViewById(R.id.year);
            registrationNumber = itemView.findViewById(R.id.registration_number);
            segment = itemView.findViewById(R.id.segment);
            condition = itemView.findViewById(R.id.condition);
        }
    }
}