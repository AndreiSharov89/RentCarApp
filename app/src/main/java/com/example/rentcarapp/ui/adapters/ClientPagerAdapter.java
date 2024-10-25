package com.example.rentcarapp.ui.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.rentcarapp.ui.fragments.ClientListFragment;
import com.example.rentcarapp.ui.fragments.RentalInfoFragment;

public class ClientPagerAdapter extends FragmentStateAdapter {

    public ClientPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ClientListFragment();
            case 1:
                return new RentalInfoFragment();
            default:
                return new ClientListFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}