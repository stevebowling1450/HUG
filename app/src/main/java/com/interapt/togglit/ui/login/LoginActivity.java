package com.interapt.togglit.ui.login;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.interapt.togglit.InteraptApplication;
import com.interapt.togglit.R;
import com.interapt.togglit.common.Constants;
import com.interapt.togglit.data.connectivity.ConnectivityReceiver;
import com.interapt.togglit.data.model.user.Volunteer;
import com.interapt.togglit.ui.forgotPassword.ForgotPasswordActivity;
import com.interapt.togglit.ui.homeScreenPods.HomeScreenPodsActivity;
import com.interapt.togglit.ui.custom.views.CustomizableButtonView;
import com.interapt.togglit.ui.custom.views.CustomizableToolbar;
import com.interapt.togglit.util.StringUtil;
import com.interapt.togglit.util.ViewUtil;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.wang.avi.AVLoadingIndicatorView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class LoginActivity extends AppCompatActivity implements LoginView, TextWatcher, ConnectivityReceiver.ConnectivityReceiverListener {

    protected boolean firstLoad = false;
    @Inject
    LoginPresenter mLoginPresenter;

    @BindView(R.id.home_log_in_toolbar)
    CustomizableToolbar mToolBar;
    @BindView(R.id.tool_bar_title)
    TextView mToolbarTitle;
    @BindView(R.id.email_wrapper)
    TextInputLayout mEmailWrapper;
    @BindView(R.id.et_email)
    EditText mEmail;
    @BindView(R.id.password_wrapper)
    TextInputLayout mPasswordWrapper;
    @BindView(R.id.et_password)
    TextInputEditText mPassword;
    @BindView(R.id.button_log_in_action)
    CustomizableButtonView mButtonLogin;
    @BindView(R.id.loading_avl_panel_progress_container)
    RelativeLayout mAVLoading;
    @BindView(R.id.avi)
    AVLoadingIndicatorView mAviProgress;
    @BindView(R.id.items_container)
    LinearLayout mViewsContainer;
    @BindView(R.id.log_in_activity_container)
    LinearLayout mActivityContainer;
    @BindView(R.id.log_in_img_logo)
    ImageView mImageLogo;
    private String mDeviceToken = "";
    private TextView mTxHexColor;
    private Window window;
    private boolean isColorValidToSnackbar = false;
    private MaterialDialog mConnectionDialog;
    private Typeface mNavMenuTypeFaceLight;
    private Typeface customFontLight;
    private Typeface customFontRegular;
    private String mCustomTypeFace = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InteraptApplication.get(this).getComponent().inject(this);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        window = this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        initializeViews();

        // TODO: 2/24/17 This is for testing, remove before deployment 
        mEmail.setText("lane@gmail.com");
        mPassword.setText("12345678");
    }

    private void initializeViews() {
        mLoginPresenter.attachView(this);

        mConnectionDialog = new MaterialDialog.Builder(this).title(R.string.offline_mod_dialog_title)
                .autoDismiss(false).content(R.string.offline_mod_dialog_content)
                .titleColorRes(R.color.tv_color_black)
                .contentColor(ContextCompat.getColor(this, R.color.tv_color_black))
                .positiveText(R.string.offline_mod_dialog_button).progress(true, 0).cancelable(false)
                .canceledOnTouchOutside(false).build();

        mTxHexColor = new TextView(this);

        mEmail.addTextChangedListener(this);
        mPassword.addTextChangedListener(this);

        //Set Compound drawable to password field
        final Drawable questionMark = ContextCompat.getDrawable(this, R.drawable.ic_question_mark);
        questionMark.setColorFilter(ContextCompat.getColor(this, R.color.tv_color_black), PorterDuff.Mode.SRC_ATOP);

        mPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(mPassword.getCompoundDrawablesRelative()[0],
                mPassword.getCompoundDrawablesRelative()[1], questionMark, mPassword.getCompoundDrawablesRelative()[3]);

        mPassword.setCompoundDrawablePadding(5);

        //Handling  password field compound drawable click listener
        mPassword.setOnTouchListener((v, event) -> {
            final int DRAWABLE_LEFT = 0;
            final int DRAWABLE_TOP = 1;
            final int DRAWABLE_RIGHT = 2;
            final int DRAWABLE_BOTTOM = 3;

            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (mPassword.getRight() - mPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    Timber.d("DRAWABLE RIGHT CLICKED ");
                    callForgotPasswordActivity();
                    return true;
                }
            }
            return false;
        });


    }


    public void callForgotPasswordActivity() {
        Intent forgotPassIntent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(forgotPassIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.log_in_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginPresenter.detachView();
    }

    @Override
    public void signInSuccessful() {
        showHomePodsActivity();
    }


    @Override
    public void getLogInUserData(Volunteer loggedUserProfile) {
        if (loggedUserProfile != null && !StringUtil.isNullOrEmpty(loggedUserProfile.getToken())) {
            mLoginPresenter.saveLoggedProfile(loggedUserProfile.getToken());
        }

    }

    @Override
    public void setEmailTextError(Boolean aBoolean) {
        if (!aBoolean) {
            mEmailWrapper.setError(getString(R.string.error_invalid_email));
        } else {
            mEmailWrapper.setErrorEnabled(false);
        }
    }


    @Override
    public void doLogInProcess(Boolean aBoolean) {
        if (aBoolean) {
            mButtonLogin.setEnabled(true);
        }
    }

    @Override
    public void setPasswordTextError(Boolean aBoolean) {
        if (!aBoolean) {
            mPasswordWrapper.setError(getString(R.string.error_invalid_password));
        } else {
            mPasswordWrapper.setErrorEnabled(false);
        }
    }

    @Override
    public void goToHomeScreen() {
        Intent intent = new Intent(this, HomeScreenPodsActivity.class);
        startActivity(intent);
    }


    public void showHomePodsActivity() {
        Intent homePodsIntent = new Intent(this, HomeScreenPodsActivity.class);
        homePodsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homePodsIntent);
        finish();
    }

    @OnClick(R.id.button_log_in_action)
    public void logInAction() {
        mLoginPresenter.doLogin(mEmail.getText().toString(), mPassword.getText().toString());
        mViewsContainer.setVisibility(View.GONE);
//        Intent intent = new Intent(this, HomeScreenPodsActivity.class);
//        startActivity(intent);
    }

    @Override
    public void showError(String message, @Constants.ErrorType int errorType) {
        switch (errorType) {
            case Constants.LOW_ERROR:
                showSnackBarError(message, null, errorType);
                mViewsContainer.setVisibility(View.VISIBLE);
                break;
        }

    }

    @Override
    public void showRefresh(boolean show) {
        mAVLoading.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    public void validateFieldsWithRx() {
        mLoginPresenter.validateRx(RxTextView.textChanges(mEmail), RxTextView.textChanges(mPassword));

    }


    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (mEmail.getText().length() > 0 && mPassword.getText().length() > 0) {
            //Reactive Validations
            validateFieldsWithRx();
        } else {
            mEmailWrapper.setErrorEnabled(false);
            mPasswordWrapper.setErrorEnabled(false);


        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void showSnackBarError(String message, @Nullable Intent intent, @Constants.ErrorType int errorType) {

        if (Constants.LOW_ERROR == errorType) {

            Snackbar snack = ViewUtil.snackbar(this, findViewById(android.R.id.content), message);


            if (isColorValidToSnackbar) {
                View view = snack.getView();

                TextView textView = (TextView) snack.getView().findViewById(android.support.design.R.id.snackbar_text);

            }

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



    //left blank so in built back button does nothing on login page.
    @Override
    public void onBackPressed() {

    }
}
