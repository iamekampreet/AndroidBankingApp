package com.group2.androidbankingapp;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MoveMoneyFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_move_money, container, false);
        setInteracItalic(rootView);
        return rootView;
    }

    private void setInteracItalic(View rootView) {
        TextView tvSendMoneyInterac = rootView.findViewById(R.id.tv_send_money_with_interac_e_transfer);
        String fullText = "Send Money with Interac e-Transfer";

        // Create a SpannableString from the full text
        SpannableString spannableString = new SpannableString(fullText);

        // Find the start and end indices of the word "Interac"
        int startIndex = fullText.indexOf("Interac");
        int endIndex = startIndex + "Interac".length();

        // Apply italic style to the word "Interac"
        spannableString.setSpan(new StyleSpan(Typeface.ITALIC), startIndex, endIndex, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Set the modified SpannableString as the text of the TextView
        tvSendMoneyInterac.setText(spannableString);
    }
}