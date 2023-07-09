package com.group2.androidbankingapp.bottomnav;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;

import com.group2.androidbankingapp.R;

public class BottomNavItem extends LinearLayout {
    private Drawable itemIcon;
    private String itemName;

    public BottomNavItem(Context context) {
        super(context);
        init();
    }

    public BottomNavItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.BottomNavItem, 0, 0);
        try {
            int drawableResId = a.getResourceId(R.styleable.BottomNavItem_icon, -1);
            itemIcon = AppCompatResources.getDrawable(getContext(), drawableResId);
            itemName = a.getString(R.styleable.BottomNavItem_item_name);
        } finally {
            // TypedArray objects are shared and must be recycled.
            a.recycle();
        }

        init();
    }

    public BottomNavItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public BottomNavItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.layout_bottom_nav_item, this);

        ImageView icon = findViewById(R.id.itemImageView);
        TextView itemNameTextView = findViewById(R.id.itemNameTextView);

        itemNameTextView.setText(itemName);
        icon.setImageDrawable(itemIcon);

        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
    }
}
