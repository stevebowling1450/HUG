package com.interapt.togglit.ui.custom;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by miller.barrera on 30/01/2017.
 */

public class CustomLayoutManager extends LinearLayoutManager {
    private boolean isScrollEnabled = true;

    public CustomLayoutManager(Context context) {
        super(context);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollEnabled && super.canScrollVertically();
    }
}