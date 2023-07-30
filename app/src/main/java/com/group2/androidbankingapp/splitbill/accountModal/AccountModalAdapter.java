package com.group2.androidbankingapp.splitbill.accountModal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group2.androidbankingapp.R;
import com.group2.androidbankingapp.models.AccountInfo;
import com.group2.androidbankingapp.utils.Singleton;
import com.group2.androidbankingapp.utils.Utils;

import java.util.List;

public class AccountModalAdapter extends RecyclerView.Adapter<AccountModalAdapter.AccountModalViewHolder> {

    List<AccountInfo> accountInfoList = Singleton.getInstance().user.getAccounts();

    OnAccountSelectedListener listener;
    AccountModalBottomSheet bottomSheet;

    public AccountModalAdapter(OnAccountSelectedListener listener, AccountModalBottomSheet bottomSheet) {
        this.listener = listener;
        this.bottomSheet = bottomSheet;
    }

    public class AccountModalViewHolder extends RecyclerView.ViewHolder {

        public TextView accountTypeTextView;
        public TextView amountTextView;

        public TextView accountNumberTextView;

        public AccountModalViewHolder(@NonNull View itemView) {
            super(itemView);

            accountTypeTextView = itemView.findViewById(R.id.textView_account_type);
            amountTextView = itemView.findViewById(R.id.textView_amount_in_account);
            accountNumberTextView = itemView.findViewById(R.id.textView_account_number);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onAccountSelected(accountInfoList.get(getAdapterPosition()));
                    }
                    bottomSheet.dismiss();
                }
            });
        }
    }

    @NonNull
    @Override
    public AccountModalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.layout_modal_account_row, parent, false);
        AccountModalViewHolder viewHolder = new AccountModalViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AccountModalViewHolder holder, int position) {
        AccountInfo accountInfo = accountInfoList.get(position);

        holder.accountTypeTextView.setText(Utils.getAccounttype(accountInfo.getAccountType()));
        holder.amountTextView.setText(accountInfo.getAccountBalance() + "");
        holder.accountNumberTextView.setText("#" + accountInfo.getAccountNumber());
    }

    @Override
    public int getItemCount() {
        return accountInfoList.size();
    }
}
