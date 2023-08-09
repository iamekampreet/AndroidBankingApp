package com.group2.androidbankingapp.api;

import com.group2.androidbankingapp.betweenaccounts.TransferBetweenAccountsModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TransferBetweenAccountsService {

    @POST("/api/between-accounts/{userId}")
    Call<SingleMessageResponseModel> schedulePayment(
            @Path("userId") String userId,
            @Body TransferBetweenAccountsModel transferBetweenAccountsModel
    );

    @GET("/api/between-accounts/upcoming-payments/{userId}")
    Call<List<TransferBetweenAccountsModel>> getAllUpcomingPayment(
            @Path("userId") String userId
    );
}
