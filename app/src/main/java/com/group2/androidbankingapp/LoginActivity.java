package com.group2.androidbankingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Spinner spinner = findViewById(R.id.spinner_service);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.services_array, R.layout.services_spiner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Button signInButton = (Button) findViewById(R.id.btn_signIn);
        signInButton.setOnClickListener(v -> signInClick());

        Button registerButton = (Button) findViewById(R.id.btn_register);
        registerButton.setOnClickListener(v -> registerClick());
    }

    protected void signInClick(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    protected void registerClick(){
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }
}