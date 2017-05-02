package com.interapt.togglit;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.anupcowkur.reservoir.Reservoir;
import com.interapt.togglit.common.FakeCrashLibrary;
import com.interapt.togglit.data.connectivity.ConnectivityReceiver;
import com.interapt.togglit.injection.component.ApplicationComponent;
import com.interapt.togglit.injection.component.DaggerApplicationComponent;
import com.interapt.togglit.injection.module.ApplicationModule;
import com.interapt.togglit.injection.module.NetworkModule;
import com.interapt.togglit.injection.module.SignInModule;

import timber.log.Timber;

/**
 * Created by miller.barrera on 14/10/2016.
 */

public class InteraptApplication extends Application {


    ApplicationComponent mApplicationComponent;
    Typeface mRobotoLight;
    Typeface mRobotoBold;
    Typeface mRobotoRegular;


    @Override
    public void onCreate() {
        super.onCreate();


        MultiDex.install(this);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }

        //Initialize Reservoir with the cache size.
        try {
            Reservoir.init(this, 2024);
        } catch (Exception e) {
            Timber.e(e, e.getMessage());
        }

//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
//                .setFontAttrId(R.attr.fontPath)
//                .build()
//        );

        //Get the Default Typeface
        mRobotoLight = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
        mRobotoBold = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        mRobotoRegular = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");

    }


    public Typeface getmRobotoLight() {
        return mRobotoLight;
    }

    public Typeface getmRobotoBold() {
        return mRobotoBold;
    }

    public Typeface getmRobotoRegular() {
        return mRobotoRegular;
    }

    /**
     * To get Context
     *
     * @param context
     * @return InteraptApplication
     */
    public static InteraptApplication get(Context context) {
        return (InteraptApplication) context.getApplicationContext();
    }


    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

    /**
     * To get ApplicationComponent
     *
     * @return ApplicationComponent
     */
    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .networkModule(new NetworkModule())
                    .signInModule(new SignInModule())
                    .build();
        }
        return mApplicationComponent;
    }

    /**
     * Only with testing purposes
     * Need to replace the component with a test specific one
     *
     * @param applicationComponent
     */
    public void setComponent(ApplicationComponent applicationComponent) {
        this.mApplicationComponent = applicationComponent;
    }

    /**
     * A tree which logs important information for crash reporting.
     */
    private static class CrashReportingTree extends Timber.Tree {
        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }

            FakeCrashLibrary.log(priority, tag, message);

            if (t != null) {
                if (priority == Log.ERROR) {
                    FakeCrashLibrary.logError(t);
                } else if (priority == Log.WARN) {
                    FakeCrashLibrary.logWarning(t);
                }
            }
        }
    }
}
