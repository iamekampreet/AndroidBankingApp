package com.group2.androidbankingapp.api;

import com.group2.androidbankingapp.splitbill.SplitInfoDetailModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SplitBillService {
    @POST("/api/splitBill")
    Call<SingleMessageResponseModel> requestSplitBill(@Body SplitInfoDetailModel splitInfoDetailModel);
}
