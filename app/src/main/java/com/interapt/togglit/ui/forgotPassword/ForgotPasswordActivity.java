package com.interapt.togglit.ui.forgotPassword;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.interapt.togglit.InteraptApplication;
import com.interapt.togglit.R;
import com.interapt.togglit.common.Constants;
import com.interapt.togglit.common.SharedPreferencesManager;
import com.interapt.togglit.data.connectivity.ConnectivityReceiver;
import com.interapt.togglit.ui.custom.views.CustomizableButtonView;
import com.interapt.togglit.ui.custom.views.CustomizableToolbar;
import com.interapt.togglit.util.ViewUtil;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.wang.avi.AVLoadingIndicatorView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgotPasswordActivity extends AppCompatActivity implements ForgotPasswordView, ConnectivityReceiver.ConnectivityReceiverListener {


    @Inject
    ForgotPasswordPresenter mForgotPasswordPresenter;
    @Inject
    SharedPreferencesManager mSharedPreferencesManager;
    @BindView(R.id.forgot_password_container)
    LinearLayout mForgotPasswordContainer;
    @BindView(R.id.forgot_password_email_wrapper)
    TextInputLayout mTextInputLayoutEmail;
    @BindView(R.id.forgot_password_til_email)
    EditText mEmail;
    @BindView(R.id.button_forgot_password_submit_action)
    CustomizableButtonView mButtonSubmit;
    @BindView(R.id.forgot_password_success_views_container)
    LinearLayout mSuccessContainer;
    @BindView(R.id.tv_check_email_message)
    TextView mTvCheckEmail;
    @BindView(R.id.tv_forgot_pass_success_message)
    TextView mTvSuccess;
    @BindView(R.id.button_forgot_password_open_email_action)
    CustomizableButtonView mButtonOpenEmail;
    @BindView(R.id.home_reset_password_toolbar)
    CustomizableToolbar mToolbar;
    @BindView(R.id.tool_bar_title)
    TextView mToolbarTitle;
    @BindView(R.id.loading_avl_panel_progress_container)
    RelativeLayout mAVLoading;
    @BindView(R.id.avi)
    AVLoadingIndicatorView mAviProgress;
    @BindView(R.id.forgot_password_activity_container)
    LinearLayout mActivityContainer;
    @BindView(R.id.forgot_pass_img_logo)
    ImageView mImageLogo;
    @BindView(R.id.img_success_check)
    ImageView mImgSuccessCheck;
    private MaterialDialog mConnectionDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InteraptApplication.get(this).getComponent().inject(this);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);

        Window window = this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }

        initializeViews();

    }

    private void initializeViews() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_left);
            upArrow.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(null);
        }

        mForgotPasswordPresenter.attachView(this);

        mConnectionDialog = new MaterialDialog.Builder(this).title(R.string.offline_mod_dialog_title)
                .autoDismiss(false).content(R.string.offline_mod_dialog_content)
                .titleColorRes(R.color.tv_color_black)
                .contentColor(ContextCompat.getColor(this, R.color.tv_color_black))
                .positiveText(R.string.offline_mod_dialog_button).progress(true, 0).cancelable(false)
                .canceledOnTouchOutside(false).build();


        mForgotPasswordPresenter.validateRx(RxTextView.textChanges(mEmail));
    }


    public void setCustomizableImageLogo(String urlImage, ImageView imageView) {
        Glide.with(this).load(urlImage).diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter().crossFade().into(imageView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mForgotPasswordPresenter.detachView();
    }


    @OnClick(R.id.button_forgot_password_submit_action)
    public void submitAction() {
        mForgotPasswordPresenter.callForgotPassword(mEmail.getText().toString());
        mForgotPasswordContainer.setVisibility(View.GONE);
    }

    @OnClick(R.id.button_forgot_password_open_email_action)
    public void openEmailAction() {
        Intent intentOpenEmail = new Intent(Intent.ACTION_MAIN);
        intentOpenEmail.addCategory(Intent.CATEGORY_APP_EMAIL);
        startActivity(intentOpenEmail);
        finish();

    }


    @Override
    public void showSuccessForgotPassword() {
        mForgotPasswordContainer.setVisibility(View.GONE);
        mSuccessContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void isValidEmail(Boolean aBoolean) {
        if (aBoolean) {
            mTextInputLayoutEmail.setErrorEnabled(false);
            mButtonSubmit.setEnabled(true);
        } else {
            if (mEmail.getText().length() > 0) {
                mTextInputLayoutEmail.setError(getString(R.string.error_invalid_email));
            } else {
                mTextInputLayoutEmail.setErrorEnabled(false);
            }

        }

    }


    @Override
    public void showError(String message, @Constants.ErrorType int errorType) {
        switch (errorType) {
            case Constants.LOW_ERROR:
                showSnackBarError(message, null, errorType);
                mForgotPasswordContainer.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void showRefresh(boolean show) {
        mAVLoading.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void showSnackBarError(String message, @Nullable Intent intent, @Constants.ErrorType int errorType) {

        if (Constants.LOW_ERROR == errorType) {

            Snackbar snack = ViewUtil.snackbar(this, findViewById(android.R.id.content), message);


            snack.setDuration(Snackbar.LENGTH_LONG).show();

        }
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (!isConnected && !mConnectionDialog.isShowing()) {
            mConnectionDialog.show();
        } else if (isConnected && mConnectionDialog.isShowing()) {
            mConnectionDialog.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        InteraptApplication.get(this).setConnectivityListener(this);
        onNetworkConnectionChanged(ConnectivityReceiver.isConnected(this));
    }
}
