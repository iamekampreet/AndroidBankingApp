package com.group2.androidbankingapp.betweenaccounts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group2.androidbankingapp.R;
import com.group2.androidbankingapp.models.AccountInfo;
import com.group2.androidbankingapp.utils.Utils;

import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder> {

    private List<AccountInfo> accountInfoList;
    private OnAccountSelectedListener accountSelectedListener;

    public AccountAdapter(List<AccountInfo> accountInfoList) {
        this.accountInfoList = accountInfoList;
    }

    public interface OnAccountSelectedListener {
        void onAccountSelected(AccountInfo accountInfo);
    }

    public void setOnAccountSelectedListener(OnAccountSelectedListener listener) {
        this.accountSelectedListener = listener;
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_account, parent, false);
        return new AccountViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        // Get the account information for the current position
        AccountInfo accountInfo = accountInfoList.get(position);

        // Update the TextViews in the ViewHolder with the account information
        holder.accountNameTextView.setText(String.valueOf(123));
        holder.accountNumberTextView.setText(String.valueOf(accountInfo.getAccountNumber()));
        holder.accountBalanceTextView.setText(String.valueOf(accountInfo.getAccountBalance()));

        // Set a click listener on the root view of the account item
        holder.itemView.setOnClickListener(v -> {
            if (accountSelectedListener != null) {
                accountSelectedListener.onAccountSelected(accountInfo);
            }
        });

        holder.bind(accountInfo);
    }

    @Override
    public int getItemCount() {
        return accountInfoList.size();
    }

    static class AccountViewHolder extends RecyclerView.ViewHolder {
        TextView accountNameTextView;
        TextView accountNumberTextView;
        TextView accountBalanceTextView;

        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);
            accountNameTextView = itemView.findViewById(R.id.tv_account_name);
            accountNumberTextView = itemView.findViewById(R.id.tv_account_number);
            accountBalanceTextView = itemView.findViewById(R.id.tv_account_amount);
        }

        public void bind(AccountInfo accountInfo) {
            // Use the provided toString() method to display the account info in a formatted way.
            String accountTypeText = Utils.getAccounttype(accountInfo.getAccountType());
            accountNameTextView.setText(accountTypeText);

            // Display account number and balance separately (if needed).
            String accountNumberText = String.valueOf(accountInfo.getAccountNumber());
            String accountBalanceText = String.valueOf(accountInfo.getAccountBalance());

            accountNumberTextView.setText(accountNumberText);
            accountBalanceTextView.setText(accountBalanceText);
        }

    }
}
