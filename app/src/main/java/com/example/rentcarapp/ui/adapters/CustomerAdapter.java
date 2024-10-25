package com.example.rentcarapp.ui.adapters;

import android.app.AlertDialog;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentcarapp.R;
import com.example.rentcarapp.data.AppDatabase;
import com.example.rentcarapp.data.entity.Customer;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {
    private List<Customer> customers;
    private AppDatabase db;
    private Handler backgroundHandler;

    public CustomerAdapter(List<Customer> customers, AppDatabase db) {
        this.customers = customers;
        this.db = db;

        HandlerThread handlerThread = new HandlerThread("DatabaseThread");
        handlerThread.start();
        backgroundHandler = new Handler(handlerThread.getLooper());
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer, parent, false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        Customer customer = customers.get(position);
        String numberedFullName = (position + 1) + ". " + customer.fullName;
        holder.fullName.setText(numberedFullName);
        holder.address.setText(customer.address);
        holder.phone.setText(customer.phone);
        holder.email.setText(customer.email);
        holder.segment.setText(String.valueOf(customer.segment));
        holder.rating.setText(String.valueOf(customer.rating));

        holder.itemView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(holder.itemView.getContext())
                    .setTitle("Удалить клиента")
                    .setMessage("Вы уверены, что хотите удалить этого клиента?")
                    .setPositiveButton("Да", (dialog, which) -> {
                        backgroundHandler.post(() -> {
                            db.customerDao().deleteCustomer(customer);
                            new Handler(Looper.getMainLooper()).post(() -> {
                                customers.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, customers.size());
                            });
                        });
                    })
                    .setNegativeButton("Нет", null)
                    .show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    static class CustomerViewHolder extends RecyclerView.ViewHolder {
        TextView fullName;
        TextView address;
        TextView phone;
        TextView email;
        TextView segment;
        TextView rating;

        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.full_name);
            address = itemView.findViewById(R.id.address);
            phone = itemView.findViewById(R.id.phone);
            email = itemView.findViewById(R.id.email);
            segment = itemView.findViewById(R.id.segment);
            rating = itemView.findViewById(R.id.rating);
        }
    }
}