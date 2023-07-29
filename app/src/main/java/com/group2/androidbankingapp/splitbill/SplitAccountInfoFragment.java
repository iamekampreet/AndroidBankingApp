package com.group2.androidbankingapp.splitbill;

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
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.group2.androidbankingapp.R;
import com.group2.androidbankingapp.common.AccountInfoModel;

import java.util.ArrayList;
import java.util.List;

public class SplitAccountInfoFragment extends Fragment implements OnAccountSelectedListener {

    private static final String ARG_PARAM_SELECTED_CONTACT = "param_selected_contact";

    private ArrayList<ContactModel> selectedContacts;
    private TextInputEditText accountEditText;

    public SplitAccountInfoFragment() {
        // Required empty public constructor
    }

    SplitInfoDetailModel splitInfoDetailModel = new SplitInfoDetailModel();

    public static SplitAccountInfoFragment newInstance(ArrayList<ContactModel> selectedContacts) {
        SplitAccountInfoFragment fragment = new SplitAccountInfoFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM_SELECTED_CONTACT, selectedContacts);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selectedContacts = getArguments().getParcelableArrayList(ARG_PARAM_SELECTED_CONTACT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_split_account_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        updateSplitAmountPerPerson();

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_split_amount);
        SelectedContactWithAmountAdapter adapter = new SelectedContactWithAmountAdapter(selectedContacts);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        accountEditText = view.findViewById(R.id.edittext_account);
        TextInputEditText noteEditText = view.findViewById(R.id.edittext_note);
        TextInputEditText yourEmailEditText = view.findViewById(R.id.edittext_your_email);
        Button nextButton = view.findViewById(R.id.button_next_account_info);

        accountEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    AccountModalBottomSheet accountModalBottomSheet = new AccountModalBottomSheet(SplitAccountInfoFragment.this);
                    accountModalBottomSheet.show(getParentFragmentManager(), "AccountModalBottomSheet");
                }
            }
        });

        noteEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    splitInfoDetailModel.setNote(noteEditText.getText().toString());
                }
            }
        });

        yourEmailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    splitInfoDetailModel.setUserEmail(yourEmailEditText.getText().toString());
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View current = getActivity().getCurrentFocus();
                if (current != null) {
                    current.clearFocus();
                }

                if (splitInfoDetailModel.getDepositInfo() == null) {
                    accountEditText.setError("Please select a deposit account");
                    return;
                }

                if (splitInfoDetailModel.getUserEmail() == null
                        || splitInfoDetailModel.getUserEmail().isEmpty()) {
                    yourEmailEditText.setError("Please provide email");
                    return;
                }

                SummaryFragment summaryFragment = SummaryFragment.newInstance(splitInfoDetailModel);

                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_split_bill,
                                summaryFragment)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void updateSplitAmountPerPerson() {
        double splitAmount = ((SplitBillActivity) getActivity()).getSplitAmount();
        Log.d("SplitAccountInfoFragment", selectedContacts.toString());
        double amountPerPerson = splitAmount / (selectedContacts.size() + 1);
        double amountPerPersonRounded = Math.round(amountPerPerson * 100.0) / 100.0;

        selectedContacts.forEach(contact -> contact.setAmount(amountPerPersonRounded));
        splitInfoDetailModel.setFriendInfos(selectedContacts);
    }

    @Override
    public void onAccountSelected(AccountInfoModel accountInfoModel) {
        splitInfoDetailModel.setDepositInfo(accountInfoModel);
        accountEditText.setText(accountInfoModel.toString());
    }
}