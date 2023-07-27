package com.group2.androidbankingapp.splitbill;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group2.androidbankingapp.R;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {
    private List<ContactRowModel> mContacts;
    private OnSelectedContactChangeListener listener;

    public ContactsAdapter(List<ContactRowModel> mContacts) {
        this.mContacts = mContacts;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView contactNameTextView;
        public TextView contactPhoneNumberTextView;
        public TextView sectionTextView;

        public CheckBox selectContactCheckbox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            contactNameTextView = itemView.findViewById(R.id.textView_name);
            contactPhoneNumberTextView = itemView.findViewById(R.id.textView_phone_number);
            sectionTextView = itemView.findViewById(R.id.textView_section);
            selectContactCheckbox = itemView.findViewById(R.id.checkBox_select_contact);
            contactNameTextView.setTag(getAdapterPosition());

            selectContactCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (listener != null) {
                        int contactPosition = getAdapterPosition();
                        if (isChecked) {
                            listener.contactAdded(contactPosition);
                            mContacts.get(contactPosition).isSelected = true;
                        } else {
                            listener.contactRemoved(contactPosition);
                            mContacts.get(contactPosition).isSelected = false;
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View contactView = inflater.inflate(R.layout.layout_contact_row, parent, false);
        ViewHolder contactViewHolder = new ViewHolder(contactView);

        return contactViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ContactRowModel contactRowModel = mContacts.get(position);

        if (!contactRowModel.isSectionIndicator) {
            holder.contactNameTextView.setText(contactRowModel.data.getName());
            holder.contactPhoneNumberTextView.setText(contactRowModel.data.getPhoneNumber());
            holder.selectContactCheckbox.setChecked(contactRowModel.isSelected);

            holder.sectionTextView.setVisibility(View.GONE);
        } else {
            holder.sectionTextView.setText(contactRowModel.sectionName);
            holder.contactPhoneNumberTextView.setVisibility(View.GONE);
            holder.contactNameTextView.setVisibility(View.GONE);
            holder.selectContactCheckbox.setVisibility(View.GONE);
            holder.sectionTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public void setOnSelectedContactChangeListener(OnSelectedContactChangeListener listener) {
        this.listener = listener;
    }

    public void updateIsContactSelect(int contactAdapterPosition) {
        mContacts.get(contactAdapterPosition).isSelected = false;
        notifyDataSetChanged();
    }
}

interface OnSelectedContactChangeListener {
    void contactAdded(int position);

    void contactRemoved(int position);
}
