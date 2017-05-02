package com.interapt.togglit.ui.custom.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.TextView;

import com.interapt.togglit.InteraptApplication;
import com.interapt.togglit.common.SharedPreferencesManager;
import com.interapt.togglit.ui.custom.FontManager;

import java.util.HashMap;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by miller.barrera on 01/12/2016.
 */

public class CustomizableTextViewFontRegular extends TextView {


    private static HashMap<String, Typeface> fontCache = new HashMap<>();
    @Inject
    SharedPreferencesManager sharedPreferencesManager;


    public CustomizableTextViewFontRegular(Context context) {
        super(context);
        InteraptApplication.get(context).getComponent().inject(this);


    }

    public CustomizableTextViewFontRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        InteraptApplication.get(context).getComponent().inject(this);


    }

    public CustomizableTextViewFontRegular(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        InteraptApplication.get(context).getComponent().inject(this);


    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomizableTextViewFontRegular(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        InteraptApplication.get(context).getComponent().inject(this);


    }


    private void applyCustomFont(Context context, String fontName) {
        Typeface customFont = FontManager.getInstance(context).loadFont(fontName + "-Regular.ttf");
        if (customFont != null) {
            setTypeface(customFont);
        } else {
            Timber.e("Custom Font REGULAR : %s", "TYPEFACE DO NO EXIST,  APPLYING DEFAULT ROROBOTO REGULAR");
            //Set default Typeface in case that the App settings font family does not exist.
            Typeface defaultRobotoRegular = InteraptApplication.get(context).getmRobotoRegular();
            if (defaultRobotoRegular != null) {
                setTypeface(defaultRobotoRegular);
            }
        }

    }


}
