package com.interapt.togglit.ui.custom.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.widget.TextView;

import com.interapt.togglit.InteraptApplication;
import com.interapt.togglit.common.SharedPreferencesManager;

import javax.inject.Inject;

/**
 * Created by miller.barrera on 21/11/2016.
 */


public class CustomizableToolbar extends Toolbar {

    @Inject
    SharedPreferencesManager sharedPreferencesManager;

    private boolean isValidHexColor = false;



    private TextView mTvHexColor;

    public CustomizableToolbar(Context context) {
        super(context);
        InteraptApplication.get(context).getComponent().inject(this);
        mTvHexColor = new TextView(context);


    }

    public CustomizableToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        InteraptApplication.get(context).getComponent().inject(this);
        mTvHexColor = new TextView(context);

    }

    public CustomizableToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        InteraptApplication.get(context).getComponent().inject(this);
        mTvHexColor = new TextView(context);

    }


}
