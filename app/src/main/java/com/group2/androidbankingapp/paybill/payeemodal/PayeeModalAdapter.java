package com.group2.androidbankingapp.paybill.payeemodal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group2.androidbankingapp.R;
import com.group2.androidbankingapp.models.PayeeModel;
import com.group2.androidbankingapp.utils.Singleton;

import java.util.List;

public class PayeeModalAdapter extends RecyclerView.Adapter<PayeeModalAdapter.PayeeModalViewHolder> {

    List<PayeeModel> payeeList = Singleton.getInstance().user.getPayee();

    OnPayeeSelectedListener listener;
    PayeeModalBottomSheet bottomSheet;

    public PayeeModalAdapter(OnPayeeSelectedListener listener, PayeeModalBottomSheet bottomSheet) {
        this.listener = listener;
        this.bottomSheet = bottomSheet;
    }

    public class PayeeModalViewHolder extends RecyclerView.ViewHolder {

        public TextView payeeNameTextView;
        public TextView payeeAccountNumberTextView;


        public PayeeModalViewHolder(@NonNull View itemView) {
            super(itemView);

            payeeNameTextView = itemView.findViewById(R.id.textView_payee_name);
            payeeAccountNumberTextView = itemView.findViewById(R.id.textView_payee_account_number);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onPayeeSelected(payeeList.get(getAdapterPosition()));
                    }
                    bottomSheet.dismiss();
                }
            });
        }
    }

    @NonNull
    @Override
    public PayeeModalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.layout_modal_payee_row, parent, false);
        PayeeModalViewHolder viewHolder = new PayeeModalViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PayeeModalViewHolder holder, int position) {
        PayeeModel payeeModel = payeeList.get(position);

        holder.payeeNameTextView.setText(payeeModel.getDisplayName());
        holder.payeeAccountNumberTextView.setText("#" + payeeModel.getAccountNumber());
    }

    @Override
    public int getItemCount() {
        return payeeList.size();
    }
}
