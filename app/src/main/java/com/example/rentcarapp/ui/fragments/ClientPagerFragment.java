package com.example.rentcarapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.rentcarapp.R;
import com.example.rentcarapp.ui.adapters.ClientPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ClientPagerFragment extends Fragment {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private ClientPagerAdapter clientPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_client_pager, container, false);

        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);

        clientPagerAdapter = new ClientPagerAdapter(getActivity());
        viewPager.setAdapter(clientPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Список клиентов");
                    break;
                case 1:
                    tab.setText("Информация об аренде");
                    break;
            }
        }).attach();

        return view;
    }
}
