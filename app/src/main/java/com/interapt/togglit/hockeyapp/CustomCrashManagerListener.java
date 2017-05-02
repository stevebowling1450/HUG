package com.interapt.togglit.hockeyapp;

import net.hockeyapp.android.CrashManagerListener;

/**
 * Created by miller.barrera on 30/11/2016.
 */

public class CustomCrashManagerListener extends CrashManagerListener {

    @Override
    public boolean shouldAutoUploadCrashes() {
        return true;
    }
}
