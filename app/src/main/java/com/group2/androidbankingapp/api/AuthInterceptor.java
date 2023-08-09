package com.group2.androidbankingapp.api;

import android.content.Context;

import com.group2.androidbankingapp.utils.SessionManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    Context context;
    private SessionManager sessionManager;

    public AuthInterceptor(Context context) {
        this.context = context;
        sessionManager = new SessionManager(context);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder requestBuilder = chain.request().newBuilder();

        String token = sessionManager.fetchAuthToken();
        if (token != null) {
            requestBuilder.addHeader("Authorization", "Bearer " + token);
        }

        return chain.proceed(requestBuilder.build());
    }
}
