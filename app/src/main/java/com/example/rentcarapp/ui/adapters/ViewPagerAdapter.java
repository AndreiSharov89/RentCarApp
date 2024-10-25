package com.example.rentcarapp.ui.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.rentcarapp.ui.fragments.AddClientFragment;
import com.example.rentcarapp.ui.fragments.AssignCarFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new AddClientFragment();
            case 1:
                return new AssignCarFragment();
            default:
                return new AddClientFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}