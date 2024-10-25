package com.example.rentcarapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentcarapp.R;
import com.example.rentcarapp.data.AppDatabase;
import com.example.rentcarapp.data.entity.Car;
import com.example.rentcarapp.ui.adapters.CarAdapter;

import java.util.List;

public class CarListFragment extends Fragment {

    private RecyclerView recyclerView;
    private CarAdapter carAdapter;
    private AppDatabase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_list, container, false);

        db = AppDatabase.getInstance(getContext());

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        new Thread(() -> {
            List<Car> cars = db.carDao().getAllCars();

            getActivity().runOnUiThread(() -> {
                carAdapter = new CarAdapter(cars);
                recyclerView.setAdapter(carAdapter);
            });
        }).start();

        return view;
    }
}