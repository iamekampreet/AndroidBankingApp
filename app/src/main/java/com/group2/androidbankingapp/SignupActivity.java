package com.group2.androidbankingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button registerButton = (Button) findViewById(R.id.btn_register);
        registerButton.setOnClickListener(v -> registerClick());

        Button backButton = (Button) findViewById(R.id.btn_back);
        backButton.setOnClickListener(v -> backClick());
    }

    protected void registerClick(){
        //validate and register user
        finish();
    }

    protected void backClick(){
        finish();
    }
}