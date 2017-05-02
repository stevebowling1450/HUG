package com.interapt.togglit.ui.progressReport.lesson;

import android.support.v4.app.Fragment;

import com.interapt.togglit.InteraptApplication;
import com.interapt.togglit.injection.component.ActivityComponent;
import com.interapt.togglit.injection.component.ApplicationComponent;
import com.interapt.togglit.ui.base.BaseActivity;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Matthew.Watson on 2/22/17.
 */

public abstract class BaseFragment extends Fragment {

    public ApplicationComponent getApplicationComponent() {
        return ((InteraptApplication) getActivity().getApplication()).getComponent();
    }

    public ActivityComponent getActivityComponent() {
        return ((BaseActivity) getActivity()).getActivityComponent();
    }

    public static String formatDate(Date date) {
        date = new Date();
        Format formatter = new SimpleDateFormat("MM/dd/yy", Locale.US);
        return formatter.format(date);
    }
}

