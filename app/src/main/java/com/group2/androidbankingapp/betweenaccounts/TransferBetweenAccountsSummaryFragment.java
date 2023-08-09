package com.group2.androidbankingapp.betweenaccounts;

import static androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.group2.androidbankingapp.MoveMoneyFragment;
import com.group2.androidbankingapp.R;
import com.group2.androidbankingapp.api.ApiError;
import com.group2.androidbankingapp.api.SingleMessageResponseModel;
import com.group2.androidbankingapp.api.TransferBetweenAccountsService;
import com.group2.androidbankingapp.models.UserModel;
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

public class TransferBetweenAccountsSummaryFragment extends Fragment implements Callback<SingleMessageResponseModel> {

    private static final String ARG_MODEL = "MODEL_PARAMS";
    TransferBetweenAccountsModel transferBetweenAccountsModel;

    public TransferBetweenAccountsSummaryFragment() {
        // Required empty public constructor
    }

    public static TransferBetweenAccountsSummaryFragment newInstance(TransferBetweenAccountsModel transferBetweenAccountsModel) {
        TransferBetweenAccountsSummaryFragment fragment = new TransferBetweenAccountsSummaryFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_MODEL, Parcels.wrap(transferBetweenAccountsModel));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            transferBetweenAccountsModel = (TransferBetweenAccountsModel) Parcels.unwrap(getArguments().getParcelable(ARG_MODEL));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_transfer_commit, container, false);

        // Retrieve arguments
//        Bundle args = getArguments();
//        if (args != null) {
//            String fromAccount = args.getString("FROM_ACCOUNT");
//            String toAccount = args.getString("TO_ACCOUNT");
//            String amount = args.getString("AMOUNT");
//            String date = args.getString("DATE");
//            String frequency = args.getString("FREQUENCY");
//
//            // Set data to the appropriate TextViews
//            TextView tvFromSelectedAccount = rootView.findViewById(R.id.tv_from_selected_account);
//            TextView tvToSelectedAccount = rootView.findViewById(R.id.tv_to_selected_account);
//            TextView tvAmountSelected = rootView.findViewById(R.id.tv_amount_selected);
//            TextView tvDateSelected = rootView.findViewById(R.id.tv_date_selected);
//            TextView tvFrequencySelected = rootView.findViewById(R.id.tv_frequency_selected);
//
//            tvFromSelectedAccount.setText(fromAccount);
//            tvToSelectedAccount.setText(toAccount);
//            tvAmountSelected.setText(amount);
//            tvDateSelected.setText(date);
//            tvFrequencySelected.setText(frequency);
//        }

//        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set data to the appropriate TextViews
        TextView tvFromSelectedAccount = view.findViewById(R.id.tv_from_selected_account);
        TextView tvToSelectedAccount = view.findViewById(R.id.tv_to_selected_account);
        TextView tvAmountSelected = view.findViewById(R.id.tv_amount_selected);
        TextView tvDateSelected = view.findViewById(R.id.tv_date_selected);
        TextView tvFrequencySelected = view.findViewById(R.id.tv_frequency_selected);

        Button btnTransferNow = view.findViewById(R.id.btn_transfer_now);
        Button btnEdit = view.findViewById(R.id.btn_edit);

        tvFromSelectedAccount.setText(transferBetweenAccountsModel.accountFrom.toString());
        tvToSelectedAccount.setText(transferBetweenAccountsModel.accountTo.toString());
        tvAmountSelected.setText(Double.toString(transferBetweenAccountsModel.amount));
        DateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        tvDateSelected.setText(dateFormat.format(transferBetweenAccountsModel.when));
        try {
            tvFrequencySelected.setText(Utils.getFrequencyString(transferBetweenAccountsModel.frequency));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        btnTransferNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserModel userModel = Singleton.getInstance().user;
                TransferBetweenAccountsService transferBetweenAccountsService = Singleton.getRetrofitInstance().create(TransferBetweenAccountsService.class);
                Call<SingleMessageResponseModel> response
                        = transferBetweenAccountsService.schedulePayment(
                        userModel.get_id(),
                        transferBetweenAccountsModel);

                response.enqueue(TransferBetweenAccountsSummaryFragment.this);
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }


//    public static TransferBetweenAccountsSummaryFragment newInstance(String fromAccount, String toAccount, String amount, String date, String frequency) {
//        TransferBetweenAccountsSummaryFragment fragment = new TransferBetweenAccountsSummaryFragment();
//        Bundle args = new Bundle();
//        args.putString("FROM_ACCOUNT", fromAccount);
//        args.putString("TO_ACCOUNT", toAccount);
//        args.putString("AMOUNT", amount);
//        args.putString("DATE", date);
//        args.putString("FREQUENCY", frequency);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onResponse(Call<SingleMessageResponseModel> call, Response<SingleMessageResponseModel> response) {
        if (response.isSuccessful()) {
            Toast.makeText(getContext(),
                    response.body().getMessage(),
                    Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager()
                    .popBackStack(MoveMoneyFragment.TAG, POP_BACK_STACK_INCLUSIVE);
        } else {
            ApiError error = Utils.parseError(response);
            Log.d("AddPayeeFragment", "Error: " + error.getMessage());
            Toast.makeText(getContext(),
                    "Error: " + error.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(Call<SingleMessageResponseModel> call, Throwable t) {
        Log.d("TransferBetweenAccountsSummaryFragment", "Error: " + t.getMessage());
        Toast.makeText(getContext(),
                "Error: " + t.getMessage(),
                Toast.LENGTH_LONG).show();
    }
}
