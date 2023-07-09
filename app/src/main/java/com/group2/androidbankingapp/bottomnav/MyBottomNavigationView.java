package com.group2.androidbankingapp.bottomnav;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.group2.androidbankingapp.LoginActivity;
import com.group2.androidbankingapp.MainActivity;
import com.group2.androidbankingapp.R;

public class MyBottomNavigationView extends ConstraintLayout {

    NavMenuSelectedListener navMenuSelectedListener;

    public MyBottomNavigationView(@NonNull Context context) {
        this(context, null);
    }

    public MyBottomNavigationView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyBottomNavigationView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public MyBottomNavigationView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.layout_bottom_nav, this);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                getContext().getResources().getDimensionPixelSize(R.dimen.bottom_nav_height)));

        BottomNavItem home = findViewById(R.id.home);
        BottomNavItem accounts = findViewById(R.id.accounts);
        BottomNavItem moveMoney = findViewById(R.id.move_money);
        BottomNavItem more = findViewById(R.id.more);
        home.setSelected(true);

        OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelection();
                v.setSelected(true);
                if (navMenuSelectedListener != null) {
                    navMenuSelectedListener.navMenuChanged(v.getId());
                }
            }
        };

        home.setOnClickListener(listener);
        accounts.setOnClickListener(listener);
        moveMoney.setOnClickListener(listener);
        more.setOnClickListener(listener);
    }

    public void setMenuSelectedListener(NavMenuSelectedListener navMenuSelectedListener) {
        this.navMenuSelectedListener = navMenuSelectedListener;
    }

    private void clearSelection() {
        ConstraintLayout menuContainer = findViewById(R.id.menu_container);

        for (int i = 0; i < menuContainer.getChildCount(); i++) {
            View bottomNavItem = menuContainer.getChildAt(i);
            String tag = (String) bottomNavItem.getTag();
            if (tag != null && tag.equals("menu_item")) {
                bottomNavItem.setSelected(false);
            }
        }
    }
}
