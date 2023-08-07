package com.group2.androidbankingapp.betweenaccounts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.group2.androidbankingapp.R;

public class TransferFragment extends Fragment {
    public TransferFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_transfer_commit, container, false);

        // Retrieve arguments
        Bundle args = getArguments();
        if (args != null) {
            String fromAccount = args.getString("FROM_ACCOUNT");
            String toAccount = args.getString("TO_ACCOUNT");
            String amount = args.getString("AMOUNT");
            String date = args.getString("DATE");
            String frequency = args.getString("FREQUENCY");

            // Set data to the appropriate TextViews
            TextView tvFromSelectedAccount = rootView.findViewById(R.id.tv_from_selected_account);
            TextView tvToSelectedAccount = rootView.findViewById(R.id.tv_to_selected_account);
            TextView tvAmountSelected = rootView.findViewById(R.id.tv_amount_selected);
            TextView tvDateSelected = rootView.findViewById(R.id.tv_date_selected);
            TextView tvFrequencySelected = rootView.findViewById(R.id.tv_frequency_selected);

            tvFromSelectedAccount.setText(fromAccount);
            tvToSelectedAccount.setText(toAccount);
            tvAmountSelected.setText(amount);
            tvDateSelected.setText(date);
            tvFrequencySelected.setText(frequency);
        }

        return rootView;
    }

    public static TransferFragment newInstance(String fromAccount, String toAccount, String amount, String date, String frequency) {
        TransferFragment fragment = new TransferFragment();
        Bundle args = new Bundle();
        args.putString("FROM_ACCOUNT", fromAccount);
        args.putString("TO_ACCOUNT", toAccount);
        args.putString("AMOUNT", amount);
        args.putString("DATE", date);
        args.putString("FREQUENCY", frequency);
        fragment.setArguments(args);
        return fragment;
    }
}
