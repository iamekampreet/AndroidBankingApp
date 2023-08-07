package com.group2.androidbankingapp.paybill.scheduledpayments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group2.androidbankingapp.R;
import com.group2.androidbankingapp.models.PayeeModel;
import com.group2.androidbankingapp.paybill.PayBillModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class UpcomingPaymentsAdapter extends RecyclerView.Adapter<UpcomingPaymentsAdapter.UpcomingPaymentViewHolder> {

    List<PayBillModel> upcomingPayments;

    public UpcomingPaymentsAdapter(List<PayBillModel> upcomingPayments) {
        this.upcomingPayments = upcomingPayments;
    }

    public class UpcomingPaymentViewHolder extends RecyclerView.ViewHolder {

        public TextView payeeName;
        public TextView date;
        public TextView amount;
        public UpcomingPaymentViewHolder(@NonNull View itemView) {
            super(itemView);

            payeeName = itemView.findViewById(R.id.textView_name);
            date = itemView.findViewById(R.id.textView_date);
            amount = itemView.findViewById(R.id.textView_amount);
        }
    }

    @NonNull
    @Override
    public UpcomingPaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.layout_scheduled_payment_row, parent, false);
        UpcomingPaymentViewHolder viewHolder = new UpcomingPaymentViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingPaymentViewHolder holder, int position) {
        PayBillModel upcomingPayment = upcomingPayments.get(position);

        DateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        holder.payeeName.setText(upcomingPayment.getAccountTo().getDisplayName());
        holder.date.setText(dateFormat.format(upcomingPayment.getWhen()));
        holder.amount.setText(Double.toString(upcomingPayment.getAmount()));
    }

    @Override
    public int getItemCount() {
        return upcomingPayments.size();
    }
}
