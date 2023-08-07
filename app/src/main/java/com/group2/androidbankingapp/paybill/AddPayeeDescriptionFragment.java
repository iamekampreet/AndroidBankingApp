package com.group2.androidbankingapp.paybill;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.group2.androidbankingapp.R;
import com.group2.androidbankingapp.api.ApiError;
import com.group2.androidbankingapp.api.PayBillService;
import com.group2.androidbankingapp.models.PayeeModel;
import com.group2.androidbankingapp.models.UserModel;
import com.group2.androidbankingapp.utils.Singleton;
import com.group2.androidbankingapp.utils.Utils;

import org.parceler.Parcels;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPayeeDescriptionFragment extends DialogFragment implements Callback<UserModel> {

    private static final String ARG_PAYEE_INFO = "PAYEE_INFO";

    PayeeModel payeeModel;

    ProgressBar updateUserPayeeProgressBar;

    public AddPayeeDescriptionFragment() {
    }

    public static AddPayeeDescriptionFragment newInstance(PayeeModel payeeModel) {
        AddPayeeDescriptionFragment fragment = new AddPayeeDescriptionFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PAYEE_INFO, Parcels.wrap(payeeModel));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            payeeModel = (PayeeModel) Parcels.unwrap(getArguments().getParcelable(ARG_PAYEE_INFO ));
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_fragment_add_payee_description, null);

        TextView payeeNameTextView = view.findViewById(R.id.textView_payee_name_value);
        TextView payeeAccountTextView = view.findViewById(R.id.textView_payee_account_number);
        updateUserPayeeProgressBar = view.findViewById(R.id.progressBar_update_payee);

        payeeNameTextView.setText(payeeModel.getDisplayName());
        payeeAccountTextView.setText("#" + payeeModel.getAccountNumber());

        setupListeners(view);

        builder.setView(view);
        return builder.create();
    }

    private void setupListeners(View view) {
        TextInputEditText descriptionEditText = view.findViewById(R.id.edittext_description);
        Button addPayeeButton = view.findViewById(R.id.button_add_payee_confirm);
        Button cancelPayeeButton = view.findViewById(R.id.button_add_payee_canel);

        addPayeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = descriptionEditText.getText().toString();
                payeeModel.setDescription(description);
                updateUserPayeeProgressBar.setVisibility(View.VISIBLE);

                PayBillService service = Singleton.getRetrofitInstance().create(PayBillService.class);
                String userId = Singleton.getInstance().user.get_id();
                Call<UserModel> response = service.updateUserPayee(userId, payeeModel);
                response.enqueue(AddPayeeDescriptionFragment.this);
            }
        });

        cancelPayeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddPayeeDescriptionFragment.this.dismiss();
            }
        });

    }

    @Override
    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
        updateUserPayeeProgressBar.setVisibility(View.INVISIBLE);
        if (response.isSuccessful()) {
            Singleton.getInstance().user = response.body();
            Toast.makeText(getContext(),
                    "Payee Added Successfully",
                    Toast.LENGTH_LONG).show();
            this.dismiss();
            getActivity().getSupportFragmentManager().popBackStack();
        } else {
            ApiError error = Utils.parseError(response);
            Log.d("AddPayeeDescriptionFragment", "Error: " + error.getMessage());
            Toast.makeText(getContext(),
                    "Error: " + error.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(Call<UserModel> call, Throwable t) {
        Log.d("AddPayeeDescriptionFragment", "Error: " + t.getMessage());
        Toast.makeText(getContext(),
                "Error2: " + t.getMessage(),
                Toast.LENGTH_LONG).show();
        updateUserPayeeProgressBar.setVisibility(View.INVISIBLE);
    }
}