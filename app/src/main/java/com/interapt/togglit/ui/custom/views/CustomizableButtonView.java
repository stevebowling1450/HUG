package com.interapt.togglit.ui.custom.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;

import com.interapt.togglit.InteraptApplication;
import com.interapt.togglit.common.SharedPreferencesManager;
import com.interapt.togglit.ui.custom.FontManager;
import com.interapt.togglit.util.StringUtil;

import javax.inject.Inject;

import rx.Observable;
import timber.log.Timber;

/**
 * Created by miller.barrera on 21/11/2016.
 */

public class CustomizableButtonView extends Button {

    @Inject
    SharedPreferencesManager sharedPreferencesManager;


    private TextView mTvHexColor;


    public CustomizableButtonView(Context context) {
        super(context);
        InteraptApplication.get(context).getComponent().inject(this);
        mTvHexColor = new TextView(context);

    }

    public CustomizableButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        InteraptApplication.get(context).getComponent().inject(this);
        mTvHexColor = new TextView(context);


    }

    public CustomizableButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        InteraptApplication.get(context).getComponent().inject(this);
        mTvHexColor = new TextView(context);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomizableButtonView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        InteraptApplication.get(context).getComponent().inject(this);
        mTvHexColor = new TextView(context);



    }

    public void validateRxHexColor(Observable<CharSequence> hexColorCharSecuence) {

        Observable<Boolean> observableHexColor = hexColorCharSecuence.map(charSequence -> StringUtil.getHexColorPattern()
                .matcher(charSequence).matches());

        observableHexColor.map(aBoolean -> aBoolean).subscribe(aBoolean -> {
            if (aBoolean) {
                setButtonCustomizableColor();
            }
        });
    }


    public void setButtonCustomizableColor() {
        // Initialize a new GradientDrawable
        GradientDrawable gd = new GradientDrawable();

        // Specify the shape of drawable
        gd.setShape(GradientDrawable.RECTANGLE);


        // Make the border rounded
        gd.setCornerRadius(100.0f); // border corner radius

        setBackground(gd);

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
