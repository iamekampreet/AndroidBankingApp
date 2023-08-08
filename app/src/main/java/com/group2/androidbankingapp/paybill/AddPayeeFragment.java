package com.group2.androidbankingapp.paybill;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.group2.androidbankingapp.MoveMoneyFragment;
import com.group2.androidbankingapp.R;
import com.group2.androidbankingapp.api.ApiError;
import com.group2.androidbankingapp.api.PayBillService;
import com.group2.androidbankingapp.models.PayeeModel;
import com.group2.androidbankingapp.paybill.payeemodal.OnPayeeSelectedListener;
import com.group2.androidbankingapp.utils.Singleton;
import com.group2.androidbankingapp.utils.Utils;

import java.util.List;
import java.util.concurrent.Callable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPayeeFragment extends Fragment implements OnPayeeSelectedListener, Callback<List<PayeeModel>> {

    AllPayeeAdapter adapter;
    RecyclerView allPayeeRecyclerView;

    ProgressBar allPayeeProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_payee, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        allPayeeRecyclerView = view.findViewById(R.id.recyclerView_all_payee);
        TextInputEditText searchEditText = view.findViewById(R.id.edittext_search);
        allPayeeProgressBar = view.findViewById(R.id.progressBar_all_payees);

        PayBillService payBillService = Singleton.getRetrofitInstance(getActivity().getApplicationContext()).create(PayBillService.class);
        Call<List<PayeeModel>> allPayee = payBillService.getAllPayee();

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (adapter != null) {
                    adapter.setFilter(s.toString());
                }
            }
        });

        allPayee.enqueue(this);

        MaterialToolbar toolbar = view.findViewById(R.id.topAppBar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    @Override
    public void onPayeeSelected(PayeeModel payeeModel) {
        AddPayeeDescriptionFragment addPayeeDescriptionFragment = AddPayeeDescriptionFragment.newInstance(payeeModel);
        addPayeeDescriptionFragment.show(getActivity().getSupportFragmentManager(), MoveMoneyFragment.TAG);
    }

    @Override
    public void onResponse(Call<List<PayeeModel>> call, Response<List<PayeeModel>> response) {
        if (response.isSuccessful()) {
            List<PayeeModel> payeeModels = response.body();
            adapter = new AllPayeeAdapter(payeeModels, AddPayeeFragment.this);
            allPayeeRecyclerView.setAdapter(adapter);
            allPayeeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            allPayeeRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
            allPayeeProgressBar.setVisibility(View.INVISIBLE);
        } else {
            if (response.raw().code() == 401) {
                Utils.handleUnauthorizedError(getActivity());
                return;
            }
            ApiError error = Utils.parseError(response, getActivity().getApplicationContext());
            Log.d("AddPayeeFragment", "Error: " + error.getMessage());
            Toast.makeText(getContext(),
                    "Error: " + error.getMessage(),
                    Toast.LENGTH_LONG).show();
            allPayeeProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onFailure(Call<List<PayeeModel>> call, Throwable t) {
        Log.d("AddPayeeFragment", "Error: " + t.getMessage());
        Toast.makeText(getContext(),
                "Error: " + t.getMessage(),
                Toast.LENGTH_LONG).show();
        allPayeeProgressBar.setVisibility(View.INVISIBLE);
    }
}