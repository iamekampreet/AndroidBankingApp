package com.group2.androidbankingapp.splitbill;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group2.androidbankingapp.R;

import java.util.List;

public class SelectedContactWithAmountAdapter
        extends RecyclerView.Adapter<SelectedContactWithAmountAdapter.SelectdContactWithAmountViewHolder> {

    List<ContactModel> contactsWithAmount;

    public SelectedContactWithAmountAdapter(List<ContactModel> contactsWithAmount) {
        this.contactsWithAmount = contactsWithAmount;
    }

    public class SelectdContactWithAmountViewHolder extends RecyclerView.ViewHolder {
        public TextView contactNameTextView;
        public TextView contactPhoneNumberTextView;

        public TextView perPersonAmountTextView;

        public SelectdContactWithAmountViewHolder(@NonNull View itemView) {
            super(itemView);

            contactNameTextView = itemView.findViewById(R.id.textView_name);
            contactPhoneNumberTextView = itemView.findViewById(R.id.textView_phone_number);
            perPersonAmountTextView = itemView.findViewById(R.id.textView_split_per_person_amount);
        }
    }

    @NonNull
    @Override
    public SelectdContactWithAmountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View contactRowWithAmount = inflater.inflate(R.layout.layout_contact_row_with_amount, parent, false);
        SelectedContactWithAmountAdapter.SelectdContactWithAmountViewHolder viewHolder =
                new SelectedContactWithAmountAdapter.SelectdContactWithAmountViewHolder(contactRowWithAmount);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectdContactWithAmountViewHolder holder, int position) {
        ContactModel contactRowModel = contactsWithAmount.get(position);

        holder.contactNameTextView.setText(contactRowModel.getName());
        holder.contactPhoneNumberTextView.setText(contactRowModel.getPhoneNumber());
        holder.perPersonAmountTextView.setText(contactRowModel.getAmount() + "");
    }

    @Override
    public int getItemCount() {
        return contactsWithAmount.size();
    }
}
