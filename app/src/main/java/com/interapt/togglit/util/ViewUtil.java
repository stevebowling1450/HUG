package com.interapt.togglit.util;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.interapt.togglit.R;

/**
 * Created by miller.barrera on 21/10/2016.
 */

public class ViewUtil {
    public static Snackbar snackbar(Context context, View v, String message) {
        Snackbar snackbar = Snackbar.make(v, message, Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        snackbar.setActionTextColor(ContextCompat.getColor(context, R.color.tv_color_black));
        // Changing message text color
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(context, R.color.azureBlue));
        return snackbar;
    }
}
