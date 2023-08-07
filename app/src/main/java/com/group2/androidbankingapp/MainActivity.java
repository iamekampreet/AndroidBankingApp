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

        String userString = "{\"_id\":\"64d04d630102aace3268a996\",\"firstName\":\"Santosh\",\"lastName\":\"Dhakal\",\"email\":\"santosh.dhakal07@gmail.com\",\"password\":\"$2a$12$Vkr0Q6h8aR3qy578E4mRFO7x1PUQuOCezB3TI8LjyLYg8TcYy5Y5C\",\"address\":\"1234 Bloor St, Mississauga, ON, Canada\",\"phone\":\"+16478365807\",\"sinNumber\":\"123456789\",\"accountType\":0,\"displayName\":\"Santosh Dhakal\",\"accounts\":[{\"accountNumber\":1,\"accountType\":1,\"status\":0,\"accountBalance\":20000,\"_id\":\"64d04d630102aace3268a997\"},{\"accountNumber\":2,\"accountType\":0,\"status\":0,\"accountBalance\":99877.43,\"_id\":\"64d04d630102aace3268a998\"}],\"cards\":[{\"cardType\":0,\"cardNumber\":\"1111222233334444\",\"expiryDate\":\"2025-02-01T05:00:00.000Z\",\"securityCode\":\"$2a$12$cOCNJwxMiN2HyVHo7SDLke2eYwyUlPFt2lZROaGE4TaxrVz.yFVv2\",\"status\":0,\"_id\":\"64d04d630102aace3268a999\"},{\"cardType\":1,\"cardNumber\":\"0000111122223333\",\"expiryDate\":\"2025-02-01T05:00:00.000Z\",\"securityCode\":\"$2a$12$cOCNJwxMiN2HyVHo7SDLke2eYwyUlPFt2lZROaGE4TaxrVz.yFVv2\",\"maxLimit\":1000,\"accountBalance\":300,\"status\":0,\"_id\":\"64d04d630102aace3268a99a\"}],\"payee\":[{\"payeeId\":\"64d04d630102aace3268a9aa\",\"displayName\":\"CRA(Revenue) Tax Amount Owing\",\"description\":\"CRA tax payments\",\"accountNumber\":10,\"_id\":\"64d04d640102aace3268a9c0\"},{\"payeeId\":\"64d04d630102aace3268a9a5\",\"displayName\":\"Humber College\",\"description\":\"Tuition Payment\",\"accountNumber\":8,\"_id\":\"64d04d640102aace3268a9c1\"},{\"payeeId\":\"64cea15e69bb6d8cf2d015dc\",\"displayName\":\"Bell Internet\",\"description\":\"Bell Internet payments\",\"accountNumber\":12,\"_id\":\"64d04dbe0102aace3268a9cc\"}],\"__v\":0}";
        String payeeString = "{\"_id\":\"64cea15e69bb6d8cf2d015da\",\"payeeId\":\"64cea15e69bb6d8cf2d015c5\",\"displayName\":\"Humber College\",\"accountNumber\":\"8\",\"__v\":0}";
        String payBillString = "{\"accountTo\":[{\"_id\":\"64cfc3d04021138fee18e73b\",\"payeeId\":\"64cfc3d04021138fee18e72a\",\"displayName\":\"CRA(Revenue) Tax Amount Owing\",\"accountNumber\":\"10\",\"__v\":0}],\"amount\":19000,\"when\":\"2023-08-06T18:03:45.810Z\"}";

//        Gson gson = new Gson();
        ObjectMapper objectMapper = new ObjectMapper();
        UserModel userModel = null;
        PayeeModel payeeModel = null;
        PayBillModel payBillModel1 = null;

        try {
            userModel = objectMapper.readValue(userString, UserModel.class);
            payeeModel = objectMapper.readValue(payeeString, PayeeModel.class);
            payBillModel1 = objectMapper.readValue(payBillString, PayBillModel.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Log.d("MainActivity", payeeModel.toString());

        PayBillModel payBillModel = new PayBillModel();
        payBillModel.setAccountFrom(userModel.getAccounts().get(1));
        payBillModel.setAccountTo(userModel.getPayee().get(0));
        payBillModel.setAmount(123.0);
        payBillModel.setWhen(new Date());
        payBillModel.setFrequency(0);
        try {
            String payBillModelString = objectMapper.writeValueAsString(payBillModel);
            Log.d(MainActivity.class.getSimpleName(), payBillModelString);
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