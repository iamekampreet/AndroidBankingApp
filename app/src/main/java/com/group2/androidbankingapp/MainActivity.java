package com.group2.androidbankingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group2.androidbankingapp.bottomnav.MyBottomNavigationView;
import com.group2.androidbankingapp.bottomnav.NavMenuSelectedListener;
import com.group2.androidbankingapp.models.AccountInfo;
import com.group2.androidbankingapp.models.PayeeModel;
import com.group2.androidbankingapp.models.UserModel;
import com.group2.androidbankingapp.paybill.PayBillModel;
import com.group2.androidbankingapp.utils.Singleton;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavMenuSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyBottomNavigationView myBottomNavigationView = findViewById(R.id.myBottomNavigationView);
        myBottomNavigationView.setMenuSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        navMenuChanged(R.id.home);
        //TODO handle lifecycle for bottom nav
    }

    @Override
    public void navMenuChanged(int id) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (id == R.id.home) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, HomeFragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
        } else if (id == R.id.accounts) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, AccountFragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
        } else if (id == R.id.move_money) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, MoveMoneyFragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack(MoveMoneyFragment.TAG)
                    .commit();
        } else if (id == R.id.more) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, MoreFragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
        }
    }

}