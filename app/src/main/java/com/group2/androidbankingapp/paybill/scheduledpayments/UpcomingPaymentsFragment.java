package com.group2.androidbankingapp.paybill.scheduledpayments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.group2.androidbankingapp.R;
import com.group2.androidbankingapp.api.ApiError;
import com.group2.androidbankingapp.api.PayBillService;
import com.group2.androidbankingapp.paybill.PayBillModel;
import com.group2.androidbankingapp.utils.Singleton;
import com.group2.androidbankingapp.utils.Utils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpcomingPaymentsFragment extends Fragment implements Callback<List<PayBillModel>> {

    RecyclerView recyclerView;
    ProgressBar upcomingPaymentProgressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_upcoming_payments, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PayBillService payBillService = Singleton.getRetrofitInstance(getActivity().getApplicationContext()).create(PayBillService.class);
        String userId = Singleton.getInstance().user.get_id();
        Call<List<PayBillModel>> request = payBillService.getAllUpcomingPayment(userId);

        recyclerView = view.findViewById(R.id.recyclerView_upcoming_payments);
        upcomingPaymentProgressBar = view.findViewById(R.id.progressBar_upcoming_payments);
        request.enqueue(this);

        MaterialToolbar toolbar = view.findViewById(R.id.topAppBar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    @Override
    public void onResponse(Call<List<PayBillModel>> call, Response<List<PayBillModel>> response) {
        upcomingPaymentProgressBar.setVisibility(View.INVISIBLE);
        if (response.isSuccessful()) {
            List<PayBillModel> payBillModels = response.body();
            List<PayBillModel> payBillModelsSorted = payBillModels.stream()
                    .sorted(Comparator.comparing(PayBillModel::getWhen))
                    .collect(Collectors.toList());
            UpcomingPaymentsAdapter adapter = new UpcomingPaymentsAdapter(payBillModelsSorted);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            ApiError apiError = Utils.parseError(response, getActivity().getApplicationContext());
            Log.d("UpcomingPaymentsFragment", apiError.getMessage());
            Toast.makeText(getContext(),
                    "Error: " + apiError.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(Call<List<PayBillModel>> call, Throwable t) {
        upcomingPaymentProgressBar.setVisibility(View.INVISIBLE);
        Log.d("UpcomingPaymentsFragment", t.getMessage());
        Toast.makeText(getContext(),
                "Error: " + t.getMessage(),
                Toast.LENGTH_LONG).show();
    }
}