package com.group2.androidbankingapp.api;

import com.group2.androidbankingapp.authorization.LoginModel;
import com.group2.androidbankingapp.authorization.LoginResponseModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthorizeService {
    @POST("/api/users/login")
    Call<LoginResponseModel> loginUser(@Body LoginModel loginModel);

    @POST("/api/users/signup")
    Call<ResponseBody> signupUser(@Body LoginModel loginModel);
}