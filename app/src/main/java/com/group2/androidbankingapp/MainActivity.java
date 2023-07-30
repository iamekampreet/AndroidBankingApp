package com.group2.androidbankingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group2.androidbankingapp.bottomnav.MyBottomNavigationView;
import com.group2.androidbankingapp.bottomnav.NavMenuSelectedListener;
import com.group2.androidbankingapp.models.UserModel;
import com.group2.androidbankingapp.utils.Singleton;

public class MainActivity extends AppCompatActivity implements NavMenuSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyBottomNavigationView myBottomNavigationView = findViewById(R.id.myBottomNavigationView);
        myBottomNavigationView.setMenuSelectedListener(this);

        String userString = "{\"_id\":\"64c57daca911c0cd596aab88\",\"firstName\":\"Santosh\",\"lastName\":\"Dhakal\",\"email\":\"santosh.dhakal07@gmail.com\",\"password\":\"$2a$12$LfdpvMqDYnI7lApL/Cn4A.C0Q88SVEWmeJd5C//JTIK6B4DTGR.kW\",\"address\":\"1234 Bloor St, Mississauga, ON, Canada\",\"phone\":\"+16478365807\",\"sinNumber\":\"123456789\",\"accountType\":0,\"displayName\":\"Santosh Dhakal\",\"accounts\":[{\"accountNumber\":1,\"accountType\":1,\"status\":0,\"accountBalance\":20000,\"_id\":\"64c57daca911c0cd596aab89\"},{\"accountNumber\":2,\"accountType\":0,\"status\":0,\"accountBalance\":100000.43,\"_id\":\"64c57daca911c0cd596aab8a\"}],\"cards\":[{\"cardType\":0,\"cardNumber\":\"1111222233334444\",\"expiryDate\":\"2025-02-01T05:00:00.000Z\",\"securityCode\":\"$2a$12$rjV.SM4fybN7KYGIAPWpnOEeREtfrIof.KEuyQDZaZYyz.cfRPoJy\",\"status\":0,\"_id\":\"64c57daca911c0cd596aab8b\"},{\"cardType\":1,\"cardNumber\":\"0000111122223333\",\"expiryDate\":\"2025-02-01T05:00:00.000Z\",\"securityCode\":\"$2a$12$rjV.SM4fybN7KYGIAPWpnOEeREtfrIof.KEuyQDZaZYyz.cfRPoJy\",\"maxLimit\":1000,\"accountBalance\":300,\"status\":0,\"_id\":\"64c57daca911c0cd596aab8c\"}],\"payee\":[],\"__v\":0}";

//        Gson gson = new Gson();
        ObjectMapper objectMapper = new ObjectMapper();
        UserModel userModel = null;
        try {
            userModel = objectMapper.readValue(userString, UserModel.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Singleton.getInstance().user = userModel;
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
                    .addToBackStack(null)
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