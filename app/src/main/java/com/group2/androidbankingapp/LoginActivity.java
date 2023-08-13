package com.group2.androidbankingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.group2.androidbankingapp.api.AuthorizeService;
import com.group2.androidbankingapp.authorization.LoginModel;
import com.group2.androidbankingapp.authorization.LoginResponseModel;
import com.group2.androidbankingapp.utils.SessionManager;
import com.group2.androidbankingapp.utils.Singleton;
import com.group2.androidbankingapp.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private final boolean SKIP_LOGIN = true; // disable login verification for development
    private LoginModel mLoginModel;

    EditText text_email;
    EditText text_password;
    TextView tv_error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Spinner spinner = findViewById(R.id.spinner_service);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.services_array, R.layout.services_spiner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Button signInButton = findViewById(R.id.btn_signIn);
        signInButton.setOnClickListener(v -> signInClick());

        Button registerButton = findViewById(R.id.btn_register);
        registerButton.setOnClickListener(v -> registerClick());

        text_email = findViewById(R.id.text_email);
        text_password = findViewById(R.id.text_password);
        tv_error = findViewById(R.id.tv_error);

        mLoginModel = new LoginModel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        tv_error.setVisibility(View.GONE);
    }

    protected void signInClick(){
        if (SKIP_LOGIN) {
            Utils.dummyLogin();
            startActivity(new Intent(this, MainActivity.class));
            return;
        }

        String email = text_email.getText().toString(); //santosh.dhakal07@gmail.com
        String password = text_password.getText().toString(); //strong-password

        if (email.isEmpty() || password.isEmpty()){
            tv_error.setVisibility(View.VISIBLE);
            return;
        }

        mLoginModel.setEmail(email);
        mLoginModel.setPassword(password);

        AuthorizeService authorizeService = Singleton.getRetrofitInstance(getApplicationContext()).create(AuthorizeService.class);
        Call<LoginResponseModel> response = authorizeService.loginUser(mLoginModel);
        Intent intent = new Intent(this, MainActivity.class);
        response.enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                if (response.isSuccessful()) {
                    Singleton.getInstance().user = response.body().getUser();
                    SessionManager sessionManager = new SessionManager(getApplicationContext());
                    sessionManager.saveAuthToken(response.body().getToken());
                    startActivity(intent);
                } else {
                    tv_error.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                Log.d("LOGIN_ERROR", t.getMessage());
            }
        });
    }

    protected void registerClick(){
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }
}