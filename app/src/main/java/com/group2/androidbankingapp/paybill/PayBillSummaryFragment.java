package com.group2.androidbankingapp.paybill;

import static androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.group2.androidbankingapp.MoveMoneyFragment;
import com.group2.androidbankingapp.R;
import com.group2.androidbankingapp.api.ApiError;
import com.group2.androidbankingapp.api.PayBillService;
import com.group2.androidbankingapp.api.SingleMessageResponseModel;
import com.group2.androidbankingapp.models.UserModel;
import com.group2.androidbankingapp.splitbill.SplitBillActivity;
import com.group2.androidbankingapp.splitbill.SummaryFragment;
import com.group2.androidbankingapp.utils.Singleton;
import com.group2.androidbankingapp.utils.Utils;

import org.parceler.Parcels;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayBillSummaryFragment extends Fragment implements Callback<SingleMessageResponseModel> {

    private static final String ARG_PAYEE = "PAYEE_PARAM";

    PayBillModel payBillModel;

    public PayBillSummaryFragment() {
        // Required empty public constructor
    }

    public static PayBillSummaryFragment newInstance(PayBillModel payBillModel) {
        PayBillSummaryFragment fragment = new PayBillSummaryFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PAYEE, Parcels.wrap(payBillModel));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            payBillModel = (PayBillModel) Parcels.unwrap(getArguments().getParcelable(ARG_PAYEE));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_payee_summary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView from = view.findViewById(R.id.textView_summary_from_value);
        TextView to = view.findViewById(R.id.textView_summary_pay_bill_value);
        TextView amount = view.findViewById(R.id.textView_summary_pay_bill_amount_value);
        TextView when = view.findViewById(R.id.textView_summary_pay_bill_when_value);
        TextView frequency = view.findViewById(R.id.textView_summary_pay_bill_frequency_value);

        Button sendPayment = view.findViewById(R.id.button_pay_bill_send_payement);
        Button editPayment = view.findViewById(R.id.button_pay_bill_edit);

        from.setText(payBillModel.accountFrom.toString());
        to.setText(payBillModel.accountTo.toString());
        amount.setText(Double.toString(payBillModel.amount));
        DateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        when.setText(dateFormat.format(payBillModel.when));
        try {
            frequency.setText(Utils.getFrequencyString(payBillModel.frequency));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        sendPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserModel userModel = Singleton.getInstance().user;
                PayBillService payBillService = Singleton.getRetrofitInstance(getActivity().getApplicationContext()).create(PayBillService.class);
                Call<SingleMessageResponseModel> response
                        = payBillService.schedulePayment(
                                userModel.get_id(),
                                payBillModel);

                response.enqueue(PayBillSummaryFragment.this);
            }
        });

        editPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        MaterialToolbar toolbar = view.findViewById(R.id.topAppBar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    @Override
    public void onResponse(Call<SingleMessageResponseModel> call, Response<SingleMessageResponseModel> response) {
        if (response.isSuccessful()) {
            Toast.makeText(getContext(),
                    response.body().getMessage(),
                    Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager()
                    .popBackStack(MoveMoneyFragment.TAG, POP_BACK_STACK_INCLUSIVE);
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
        }
    }

    @Override
    public void onFailure(Call<SingleMessageResponseModel> call, Throwable t) {
        Log.d("AddPayeeFragment", "Error: " + t.getMessage());
        Toast.makeText(getContext(),
                "Error: " + t.getMessage(),
                Toast.LENGTH_LONG).show();
    }
}