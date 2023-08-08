package com.group2.androidbankingapp.authorization;

import com.group2.androidbankingapp.models.UserModel;

public class LoginResponseModel {
    String errorCode;

    UserModel user;
    String token;

    public LoginResponseModel() {

    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
