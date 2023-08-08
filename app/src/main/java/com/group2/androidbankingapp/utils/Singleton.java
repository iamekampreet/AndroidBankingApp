package com.group2.androidbankingapp.utils;

import android.content.Context;

import com.group2.androidbankingapp.api.AuthInterceptor;
import com.group2.androidbankingapp.models.UserModel;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class Singleton {
    private static Singleton instance = null;
    private static Retrofit retrofit;

    private static final String BASE_URL = "http://10.0.2.2:5001/";
    public UserModel user;

    private Singleton() { }

    public static synchronized Singleton getInstance()
    {
        if (instance == null)
            instance = new Singleton();

        return instance;
    }

    public static synchronized Retrofit getRetrofitInstance(Context context) {
        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            AuthInterceptor authInterceptor = new AuthInterceptor(context);

            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .addInterceptor(authInterceptor)
                    .build();

            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .client(httpClient)
                    .build();
        }
        return retrofit;
    }
}