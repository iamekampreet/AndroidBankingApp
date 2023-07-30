package com.group2.androidbankingapp.api;

public class SingleMessageResponseModel {
    String errorCode;
    String message;

    public SingleMessageResponseModel() {

    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
