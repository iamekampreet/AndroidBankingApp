package com.group2.androidbankingapp.paybill;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.group2.androidbankingapp.MoveMoneyFragment;
import com.group2.androidbankingapp.R;
import com.group2.androidbankingapp.models.AccountInfo;
import com.group2.androidbankingapp.models.PayeeModel;
import com.group2.androidbankingapp.paybill.payeemodal.OnPayeeSelectedListener;
import com.group2.androidbankingapp.paybill.payeemodal.PayeeModalBottomSheet;
import com.group2.androidbankingapp.splitbill.accountModal.AccountModalBottomSheet;
import com.group2.androidbankingapp.splitbill.accountModal.OnAccountSelectedListener;
import com.group2.androidbankingapp.utils.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class PayBillInputFragment extends Fragment {

    PayBillModel payBillModel = new PayBillModel();

    CalendarConstraints calendarConstraints
            = new CalendarConstraints.Builder()
                    .setValidator(DateValidatorPointForward.now())
                    .build();

    MaterialDatePicker datePicker =
            MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select Date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .setCalendarConstraints(calendarConstraints)
                    .build();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pay_bill_input, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextInputLayout fromTextInputLayout = view.findViewById(R.id.textField_from);
        TextInputEditText fromEditText = view.findViewById(R.id.edittext_from);

        TextInputLayout toTextInputLayout = view.findViewById(R.id.textField_to);
        TextInputEditText toEditText = view.findViewById(R.id.edittext_to);
        TextInputEditText amountEditText = view.findViewById(R.id.edittext_pay_bill_amount);
        TextInputEditText whenEditText = view.findViewById(R.id.edittext_pay_bill_when);
        TextInputEditText frequencyEditText = view.findViewById(R.id.edittext_search);

        Button nextButton = view.findViewById(R.id.button_go_to_summary);

        fromEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountModalBottomSheet accountModalBottomSheet = new AccountModalBottomSheet(new OnAccountSelectedListener() {
                    @Override
                    public void onAccountSelected(AccountInfo accountInfo) {
                        payBillModel.accountFrom = accountInfo;
                        fromEditText.setText(accountInfo.toString());
                    }
                });
                accountModalBottomSheet.show(getParentFragmentManager(), "AccountModalBottomSheet");
            }
        });

        toEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayeeModalBottomSheet payeeModalBottomSheet = new PayeeModalBottomSheet(new OnPayeeSelectedListener() {
                    @Override
                    public void onPayeeSelected(PayeeModel payeeModel) {
                        payBillModel.setAccountTo(payeeModel);
                        toEditText.setText(payeeModel.toString());
                    }
                });
                payeeModalBottomSheet.show(getParentFragmentManager(), "AccountModalBottomSheet");
            }
        });

        whenEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.show(getActivity().getSupportFragmentManager(), MoveMoneyFragment.TAG);
            }
        });

        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                Date date = new Date((long) selection);
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MMMM-dd", Locale.getDefault());
                dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                whenEditText.setText(dateFormat.format(date));
                payBillModel.setWhen(date);
            }
        });

        frequencyEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] singleItems = {"Once", "Weekly", "Monthly"};
                int checkedItem = payBillModel.frequency;

                new MaterialAlertDialogBuilder(getContext());

                MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(getContext());
                materialAlertDialogBuilder.setTitle("Frequency?");
                materialAlertDialogBuilder.setSingleChoiceItems(singleItems, checkedItem, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                payBillModel.setFrequency(which);
                                try {
                                    frequencyEditText.setText(Utils.getFrequencyString(which));
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                                dialog.dismiss();
                            }
                        });
                materialAlertDialogBuilder.show();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double amount = Double.parseDouble(amountEditText.getText().toString());
                payBillModel.setAmount(amount);
                if (payBillModel.isValid()) {
                    PayBillSummaryFragment payBillFragment = PayBillSummaryFragment.newInstance(payBillModel);
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, payBillFragment)
                            .addToBackStack(MoveMoneyFragment.TAG)
                            .commit();
                }
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
}