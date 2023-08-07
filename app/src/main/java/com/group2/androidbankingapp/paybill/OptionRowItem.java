package com.group2.androidbankingapp.paybill;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.google.android.material.imageview.ShapeableImageView;
import com.group2.androidbankingapp.R;

public class OptionRowItem extends ConstraintLayout {

    private int itemIcon;
    private String itemTitle;
    private String subTitle;

    private boolean optionEnabled;

    public OptionRowItem(@NonNull Context context) {
        this(context, null);
    }

    public OptionRowItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OptionRowItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public OptionRowItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.OptionRowItem, 0, 0);
        try {
            itemIcon = a.getResourceId(R.styleable.OptionRowItem_optionIcon, -1);
            itemTitle = a.getString(R.styleable.OptionRowItem_title);
            subTitle = a.getString(R.styleable.OptionRowItem_subTitle);
            optionEnabled = a.getBoolean(R.styleable.OptionRowItem_optionEnabled, true);
        } finally {
            a.recycle();
        }

        init();
    }

    private void init() {
        inflate(getContext(), R.layout.layout_option_row_item, this);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                getContext().getResources().getDimensionPixelSize(R.dimen.bottom_nav_height)));

        ShapeableImageView shapeableImageView = findViewById(R.id.shapeableImageView);
        TextView titleTextView = findViewById(R.id.textView_title);
        TextView subTitleTextView = findViewById(R.id.textView_sub_title);
        if (itemIcon != -1) {
            shapeableImageView.setImageResource(itemIcon);
        }
        if (itemTitle != null) {
            titleTextView.setText(itemTitle);
        }
        if (subTitle != null) {
            subTitleTextView.setText(subTitle);
        } else {
            subTitleTextView.setVisibility(GONE);
        }
        if (!optionEnabled) {
            titleTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.grey));
            subTitleTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.grey));
            shapeableImageView.setStrokeColor(AppCompatResources.
                    getColorStateList(getContext(), R.color.grey));
            shapeableImageView.setColorFilter(ContextCompat.getColor(getContext(), R.color.grey), PorterDuff.Mode.SRC_IN);
        }
    }

    public void setOnContainerClickListener(OnClickListener listener) {
        ConstraintLayout container = findViewById(R.id.container_option_row_item);
        container.setOnClickListener(listener);
    }
}
