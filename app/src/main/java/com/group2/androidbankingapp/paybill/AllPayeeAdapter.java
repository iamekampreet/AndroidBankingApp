package com.group2.androidbankingapp.paybill;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group2.androidbankingapp.R;
import com.group2.androidbankingapp.models.PayeeModel;
import com.group2.androidbankingapp.paybill.payeemodal.OnPayeeSelectedListener;
import com.group2.androidbankingapp.paybill.payeemodal.PayeeModalBottomSheet;
import com.group2.androidbankingapp.utils.Singleton;

import java.util.List;
import java.util.stream.Collectors;

public class AllPayeeAdapter extends RecyclerView.Adapter<AllPayeeAdapter.AllPayeeViewHolder> {

    List<PayeeModel> payeeList;
    OnPayeeSelectedListener listener;

    List<PayeeModel> filteredPayeeList;

    public AllPayeeAdapter(List<PayeeModel> payeeList, OnPayeeSelectedListener listener) {
        this.listener = listener;
        this.payeeList = payeeList;
        filteredPayeeList = payeeList;
    }

    public class AllPayeeViewHolder extends RecyclerView.ViewHolder {

        public TextView payeeNameTextView;
        public TextView payeeAccountNumberTextView;

        public AllPayeeViewHolder(@NonNull View itemView) {
            super(itemView);

            payeeNameTextView = itemView.findViewById(R.id.textView_payee_name);
            payeeAccountNumberTextView = itemView.findViewById(R.id.textView_payee_account_number);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onPayeeSelected(payeeList.get(getAdapterPosition()));
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public AllPayeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.layout_modal_payee_row, parent, false);
        AllPayeeViewHolder viewHolder = new AllPayeeViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AllPayeeViewHolder holder, int position) {
        PayeeModel payeeModel = filteredPayeeList.get(position);

        holder.payeeNameTextView.setText(payeeModel.getDisplayName());
        holder.payeeAccountNumberTextView.setText("Account#" + payeeModel.getAccountNumber());
    }

    @Override
    public int getItemCount() {
        return filteredPayeeList.size();
    }

    public  void setFilter(String filter) {
        filteredPayeeList = payeeList.stream()
                .filter(payeeModel ->
                        payeeModel.getDisplayName().toLowerCase()
                                .contains(filter.toLowerCase()))
                .collect(Collectors.toList());
        notifyDataSetChanged();
    }
}
