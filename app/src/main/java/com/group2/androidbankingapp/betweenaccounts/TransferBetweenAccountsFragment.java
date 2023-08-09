package com.group2.androidbankingapp.betweenaccounts;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group2.androidbankingapp.MoveMoneyFragment;
import com.group2.androidbankingapp.R;
import com.group2.androidbankingapp.models.AccountInfo;
import com.group2.androidbankingapp.paybill.PayBillSummaryFragment;
import com.group2.androidbankingapp.utils.Singleton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TransferBetweenAccountsFragment extends Fragment {
    TransferBetweenAccountsModel transferBetweenAccountsModel = new TransferBetweenAccountsModel();
    private PopupWindow selectAccountPopupWindow;
    private PopupWindow frequencyPopupWindow;
    private LayoutInflater inflater;
    private TextView tvDate;
    private ImageView imgWhenDropdown;
    private Calendar calendar;
    private TextView tvFrom;
    private TextView tvTo;
    private TextView tvFromSelectedAccount;
    private TextView tvToSelectedAccount;
    private EditText etAmount;
    private Button btnContinue;
    private TextView tvError;
    private ConstraintLayout constraintContinue;
    private TextView tvFrequencyValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_transfer_between_my_accounts, container, false);

        tvDate = rootView.findViewById(R.id.tv_date);
        imgWhenDropdown = rootView.findViewById(R.id.img_when_dropdown);
        calendar = Calendar.getInstance();
        tvFrom = rootView.findViewById(R.id.tv_from);
        tvTo = rootView.findViewById(R.id.tv_to);
        tvFromSelectedAccount = rootView.findViewById(R.id.tv_from_selected_account);
        tvToSelectedAccount = rootView.findViewById(R.id.tv_to_selected_account);
        etAmount = rootView.findViewById(R.id.et_amount);
        btnContinue = rootView.findViewById(R.id.btn_continue);
        tvError = rootView.findViewById(R.id.tv_error);
        constraintContinue = rootView.findViewById(R.id.constraint_continue);
        tvFrequencyValue = rootView.findViewById(R.id.tv_frequency_value);


        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click event for the "Continue" button
                onContinueButtonClick();
            }
        });

        // Set default date to today's date in tvDate
        updateDateInView();

        imgWhenDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show DatePickerDialog
                showDatePicker();
            }
        });

        // OnEditorActionListener to detect soft keyboard actions
        etAmount.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    validateAmount();

                    // Hide the soft keyboard
                    InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);

                    return true;
                }
                return false;
            }
        });

        etAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                String newText = charSequence.toString();
                updateContinueButtonState();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        this.inflater = inflater;
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView imgFromDropdown = view.findViewById(R.id.img_from_dropdown);
        ImageView imgToDropdown = view.findViewById(R.id.img_to_dropdown);
        ImageView imgFrequencyDropdown = view.findViewById(R.id.img_frequency_dropdown);

        imgFromDropdown.setOnClickListener(v -> showPopupWindowSelectAccount(v, true));
        imgToDropdown.setOnClickListener(v -> showPopupWindowSelectAccount(v, false));
        imgFrequencyDropdown.setOnClickListener(v -> showPopupWindowFrequency(v));
    }

    private void showPopupWindowSelectAccount(View anchorView, boolean isFromDropdown) {
        View selectAccountPopupView = inflater.inflate(R.layout.select_account_popup_layout, null);
        RecyclerView recyclerView = selectAccountPopupView.findViewById(R.id.recycler_view_accounts);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // Get the list of accounts
        List<AccountInfo> accountInfoList = Singleton.getInstance().user.getAccounts();

        // Create an instance of AccountAdapter and pass the list of accounts to it
        AccountAdapter accountAdapter = new AccountAdapter(accountInfoList);

        // Set the adapter for the RecyclerView
        recyclerView.setAdapter(accountAdapter);

        // Set the OnAccountSelectedListener to handle account selection
        accountAdapter.setOnAccountSelectedListener(new AccountAdapter.OnAccountSelectedListener() {
            @Override
            public void onAccountSelected(AccountInfo accountInfo) {
                // Check if it's from the "From" dropdown or the "To" dropdown
                if (isFromDropdown) {
                    onFromAccountSelected(accountInfo);
                    transferBetweenAccountsModel.accountFrom = accountInfo;
                } else {
                    onToAccountSelected(accountInfo);
                    transferBetweenAccountsModel.accountTo = accountInfo;
                }

                // Dismiss the popup window after selection
                selectAccountPopupWindow.dismiss();
            }
        });

        // Show the popup window
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        selectAccountPopupWindow = new PopupWindow(selectAccountPopupView, width, height, true);
        selectAccountPopupWindow.showAtLocation(anchorView, Gravity.BOTTOM, 0, 0);

        ImageView closeSelectAccount = selectAccountPopupView.findViewById(R.id.img_close_popup);
        closeSelectAccount.setOnClickListener(popupView1 -> selectAccountPopupWindow.dismiss());
    }

    private void showPopupWindowFrequency(View anchorView) {
        View frequencyPopupView = inflater.inflate(R.layout.frequency_popup_layout, null);

        // Show the popup window
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        frequencyPopupWindow = new PopupWindow(frequencyPopupView, width, height, true);
        frequencyPopupWindow.showAtLocation(anchorView, Gravity.BOTTOM, 0, 0);

        TextView tvOnce = frequencyPopupView.findViewById(R.id.tv_once);
        TextView tvMonthly = frequencyPopupView.findViewById(R.id.tv_monthly);
        TextView tvWeekly = frequencyPopupView.findViewById(R.id.tv_weekly);

        // Set click listeners for each frequency option
        tvOnce.setOnClickListener(v -> {
            updateFrequencyTextView("Once");
            transferBetweenAccountsModel.frequency = 0;
            frequencyPopupWindow.dismiss();
        });

        tvMonthly.setOnClickListener(v -> {
            updateFrequencyTextView("Monthly");
            transferBetweenAccountsModel.frequency = 2;
            frequencyPopupWindow.dismiss();
        });

        tvWeekly.setOnClickListener(v -> {
            updateFrequencyTextView("Weekly");
            transferBetweenAccountsModel.frequency = 1;
            frequencyPopupWindow.dismiss();
        });

        ImageView closeFrequency = frequencyPopupView.findViewById(R.id.img_close_popup);
        closeFrequency.setOnClickListener(popupView1 -> frequencyPopupWindow.dismiss());
    }

    private void updateFrequencyTextView(String frequency) {
        // Update the TextView with the selected frequency
        TextView tvFrequencyValue = getView().findViewById(R.id.tv_frequency_value);
        tvFrequencyValue.setText(frequency);
    }

    private void onFromAccountSelected(AccountInfo accountInfo) {
        String selectedAccountText = accountInfo.toString();
        tvFromSelectedAccount.setVisibility(View.VISIBLE);
        tvFromSelectedAccount.setText(selectedAccountText);
        tvFrom.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

        // Update the "Continue" button state
        updateContinueButtonState();
        showFromToError();
    }

    private void onToAccountSelected(AccountInfo accountInfo) {
        String selectedAccountText = accountInfo.toString();
        tvToSelectedAccount.setVisibility(View.VISIBLE);
        tvToSelectedAccount.setText(selectedAccountText);
        tvTo.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

        // Update the "Continue" button state
        updateContinueButtonState();
        showFromToError();
    }


    private void showDatePicker() {
        // Get the current date
        Calendar currentDate = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(), // Use requireContext() to get the Context
                R.style.DatePickerTheme, // Set the custom theme here
                dateSetListener,
                currentDate.get(Calendar.YEAR),
                currentDate.get(Calendar.MONTH),
                currentDate.get(Calendar.DAY_OF_MONTH)
        );

        // Set the minimum date to today's date
        datePickerDialog.getDatePicker().setMinDate(currentDate.getTimeInMillis());

        datePickerDialog.show();
    }

    private void updateDateInView() {
        String myFormat = "MMM dd, yyyy"; // Format for displaying the date
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        tvDate.setText(sdf.format(calendar.getTime()));
        transferBetweenAccountsModel.when = calendar.getTime();
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // Create a new Calendar instance and set it to the selected date
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(Calendar.YEAR, year);
            selectedDate.set(Calendar.MONTH, monthOfYear);
            selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            // Check if the selected date is earlier than today's date
            Calendar currentDate = Calendar.getInstance();
            if (selectedDate.before(currentDate)) {
                // Show a message to the user indicating that the selected date is invalid
                Toast.makeText(requireContext(), "Please select a valid date.", Toast.LENGTH_SHORT).show();
            } else {
                // Update the calendar with the selected date
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                // Update the TextView with the selected date
                updateDateInView();
            }
        }
    };

    private void updateContinueButtonState() {
        boolean isFromAndToDifferent = isFromAndToDifferent();
        boolean isAmountValid = isAmountValid();

        // Enable/disable the "Continue" button based on the conditions
        btnContinue.setEnabled(isFromAndToDifferent && isAmountValid);

        // Set the backgroundTint of the "Continue" button based on its enabled state
        int backgroundTint = isFromAndToDifferent && isAmountValid
                ? R.color.colorPrimary // Enabled state tint color (blue)
                : R.color.lightGrey; // Disabled state tint color (light grey)
        int color = ContextCompat.getColor(requireContext(), backgroundTint);
        btnContinue.setBackgroundTintList(ColorStateList.valueOf(color));

        // Toggle the text color of the button based on its enabled state
        int textColor = isFromAndToDifferent && isAmountValid
                ? Color.WHITE // Enabled state text color (white)
                : ContextCompat.getColor(requireContext(), R.color.darkGrey); // Disabled state text color (dark grey)
        btnContinue.setTextColor(textColor);

        // Update the background color of the constraint based on the enabled state of the button
        int constraintBackgroundColor = isFromAndToDifferent && isAmountValid
                ? R.color.colorPrimary // Enabled state background color (blue)
                : R.color.lightGrey; // Disabled state background color (light grey)
        int constraintColor = ContextCompat.getColor(requireContext(), constraintBackgroundColor);
        constraintContinue.setBackgroundColor(constraintColor);
    }


    private void showFromToError() {
        // Show an error message if the "From" and "To" accounts are the same
        if (!isFromAndToDifferent()) {
            tvError.setText("Error: From and To accounts cannot be the same.");
            tvError.setVisibility(View.VISIBLE);
        } else {
            tvError.setVisibility(View.GONE);
        }
    }


    private void validateAmount() {
        // Show an error message if the amount is not valid (empty) or less than or equal to zero
        if (!isAmountValid()) {
            tvError.setText("Error: Amount must be greater than 0.");
            tvError.setVisibility(View.VISIBLE);
        } else {
            tvError.setVisibility(View.GONE);
        }
    }


    private boolean isFromAndToDifferent() {
        return !tvFromSelectedAccount.getText().toString().equals(tvToSelectedAccount.getText().toString());
    }

    private boolean isAmountValid() {
        boolean isAmountValid = !TextUtils.isEmpty(etAmount.getText());
        double amount = isAmountValid ? Double.parseDouble(etAmount.getText().toString()) : 0;
        boolean isAmountGreaterThanZero = amount > 0;

        if (isAmountValid && isAmountGreaterThanZero) {
            String formattedAmount = String.format("%.2f", amount);
            transferBetweenAccountsModel.amount = Double.parseDouble(formattedAmount);
        }

        return isAmountValid && isAmountGreaterThanZero;
    }

    public void onContinueButtonClick() {
        // Check if all validations are successful before proceeding
        if (isFromAndToDifferent() && isAmountValid()) {

            // Create an instance of the transfer fragment with arguments
            TransferBetweenAccountsSummaryFragment transferBetweenAccountsSummaryFragment = TransferBetweenAccountsSummaryFragment.newInstance(transferBetweenAccountsModel);

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, transferBetweenAccountsSummaryFragment)
                    .addToBackStack(MoveMoneyFragment.TAG)
                    .commit();
        }

    }
}