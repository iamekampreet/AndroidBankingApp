package com.group2.androidbankingapp.api;

import com.group2.androidbankingapp.models.PayeeModel;
import com.group2.androidbankingapp.models.UserModel;
import com.group2.androidbankingapp.paybill.PayBillModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PayBillService {
    @POST("/api/pay-bill/{userId}")
    Call<SingleMessageResponseModel> schedulePayment(
            @Path("userId") String userId,
            @Body PayBillModel payBillModel
    );

    @GET("/api/pay-bill/upcoming-payments/{userId}")
    Call<List<PayBillModel>> getAllUpcomingPayment(
            @Path("userId") String userId
    );

    @GET("/api/pay-bill/payee")
    Call<List<PayeeModel>> getAllPayee();

    @POST("/api/pay-bill/updateUserPayee/{userId}")
    Call<UserModel> updateUserPayee(
            @Path("userId") String userId,
            @Body PayeeModel payeeModel
    );
}
