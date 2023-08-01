package com.group2.androidbankingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.group2.androidbankingapp.api.AuthorizeService;
import com.group2.androidbankingapp.authorization.LoginModel;
import com.group2.androidbankingapp.utils.Singleton;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    private LoginModel mLoginModel;
    private int colorRed;
    private int colorPrimary;

    TextView tv_status;
    EditText text_card;
    EditText text_lastname;
    EditText text_email;
    EditText text_password;
    EditText text_passwordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button registerButton = findViewById(R.id.btn_register);
        registerButton.setOnClickListener(v -> registerClick());

        Button backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(v -> backClick());

        tv_status = findViewById(R.id.tv_status);
        text_card = findViewById(R.id.text_card);
        text_lastname = findViewById(R.id.text_lastname);
        text_email = findViewById(R.id.text_email);
        text_password = findViewById(R.id.text_password);
        text_passwordConfirm = findViewById(R.id.text_passwordConfirm);

        mLoginModel = new LoginModel();
        colorRed = ContextCompat.getColor(this, R.color.red);
        colorPrimary = ContextCompat.getColor(this, R.color.colorPrimary);
    }

    protected void registerClick(){
        String lastName = text_lastname.getText().toString(); //Cartman
        String email = text_email.getText().toString(); //test@gmail.com
        String password = text_password.getText().toString(); //1234567890
        String passwordConfirm = text_passwordConfirm.getText().toString(); //1234567890

        if (email.isEmpty() || password.isEmpty()){
            tv_status.setText("Fill all fields");
            tv_status.setTextColor(colorRed);
            tv_status.setVisibility(View.VISIBLE);
            return;
        }

        if (!password.equals(passwordConfirm)){
            tv_status.setText("Passwords do not match");
            tv_status.setTextColor(colorRed);
            tv_status.setVisibility(View.VISIBLE);
            return;
        }

        mLoginModel.setEmail(email);
        mLoginModel.setPassword(password);
        mLoginModel.setLastName(lastName);

        AuthorizeService authorizeService = Singleton.getRetrofitInstance().create(AuthorizeService.class);
        Call<ResponseBody> response = authorizeService.signupUser(mLoginModel);

        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    tv_status.setText("Account registered successfully");
                    tv_status.setTextColor(colorPrimary);
                    tv_status.setVisibility(View.VISIBLE);

                    clearForm();
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMsg = jObjError.getString("message");
                        tv_status.setText(errorMsg);
                        tv_status.setTextColor(colorRed);
                        tv_status.setVisibility(View.VISIBLE);
                    } catch (Exception ignored) { }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("SIGNUP_ERROR", t.getMessage());
            }
        });
    }

    protected void backClick(){
        finish();
    }

    private void clearForm(){
        text_card.setText("");
        text_email.setText("");
        text_lastname.setText("");
        text_password.setText("");
        text_passwordConfirm.setText("");

        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}