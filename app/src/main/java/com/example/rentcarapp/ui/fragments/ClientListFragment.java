package com.example.rentcarapp.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
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
import com.example.rentcarapp.data.entity.Customer;
import com.example.rentcarapp.ui.adapters.CustomerAdapter;

import java.util.List;

public class ClientListFragment extends Fragment {

    private RecyclerView recyclerView;
    private CustomerAdapter adapter;
    private AppDatabase db;
    private Handler backgroundHandler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_client_list, container, false);

        db = AppDatabase.getInstance(getContext());

        HandlerThread handlerThread = new HandlerThread("DatabaseThread");
        handlerThread.start();
        backgroundHandler = new Handler(handlerThread.getLooper());

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        backgroundHandler.post(() -> {
            List<Customer> customers = db.customerDao().getAllCustomers();

            new Handler(Looper.getMainLooper()).post(() -> {
                adapter = new CustomerAdapter(customers, db);
                recyclerView.setAdapter(adapter);
            });
        });

        return view;
    }
}