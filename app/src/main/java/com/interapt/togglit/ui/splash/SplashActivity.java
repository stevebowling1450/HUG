package com.interapt.togglit.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;
import com.interapt.togglit.InteraptApplication;
import com.interapt.togglit.R;
import com.interapt.togglit.common.Constants;
import com.interapt.togglit.common.SharedPreferencesManager;
import com.interapt.togglit.data.connectivity.ConnectivityReceiver;
import com.interapt.togglit.ui.login.LoginActivity;
import com.interapt.togglit.util.ViewUtil;

import javax.inject.Inject;

/**
 * Created by miller.barrera on 19/10/2016.
 */

public class SplashActivity extends AppCompatActivity implements SplashView, ConnectivityReceiver.ConnectivityReceiverListener {

    protected boolean firstLoad = false;
    @Inject
    SplashPresenter mSplashPresenter;
    @Inject
    SharedPreferencesManager sharedPreferencesManager;
    private MaterialDialog mConnectionDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InteraptApplication.get(this).getComponent().inject(this);


        initializeViews();
    }

    public void initializeViews() {
        mSplashPresenter.attachView(this);

        mConnectionDialog = new MaterialDialog.Builder(this).title(R.string.offline_mod_dialog_title)
                .autoDismiss(false).content(R.string.offline_mod_dialog_content)
                .titleColorRes(R.color.tv_color_black)
                .contentColor(ContextCompat.getColor(this, R.color.tv_color_black))
                .positiveText(R.string.offline_mod_dialog_button).progress(true, 0).cancelable(false)
                .canceledOnTouchOutside(false).build();


        mSplashPresenter.goToLogin();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSplashPresenter.detachView();
    }


    @Override
    public void showError(String message, @Constants.ErrorType int errorType) {
        switch (errorType) {
            case Constants.LOW_ERROR:
                showSnackBarError(message, null, errorType);
                finish();
                break;
        }
    }

    @Override
    public void showRefresh(boolean show) {

    }

    public void showSnackBarError(String message, @Nullable Intent intent, @Constants.ErrorType int errorType) {
        if (Constants.LOW_ERROR == errorType) {
            Snackbar snack = ViewUtil.snackbar(this, findViewById(android.R.id.content), message);
            snack.setDuration(Snackbar.LENGTH_INDEFINITE)
                    .setAction(getString(R.string.snack_close_button), view -> snack.dismiss()).show();
        }
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (!isConnected && !mConnectionDialog.isShowing()) {
            mConnectionDialog.show();
        } else if (isConnected && mConnectionDialog.isShowing()) {
            mConnectionDialog.dismiss();
            mSplashPresenter.goToLogin();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        InteraptApplication.get(this).setConnectivityListener(this);
        onNetworkConnectionChanged(ConnectivityReceiver.isConnected(this));
    }

    @Override
    public void goToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}