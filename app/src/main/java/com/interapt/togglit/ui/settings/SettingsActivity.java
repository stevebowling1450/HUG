package com.interapt.togglit.ui.settings;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.interapt.togglit.R;
import com.interapt.togglit.common.SharedPreferencesManager;
import com.interapt.togglit.data.model.user.Volunteer;
import com.interapt.togglit.ui.base.BaseActivity;
import com.interapt.togglit.ui.custom.views.CustomizableToolbar;
import com.interapt.togglit.ui.login.LoginActivity;
import com.interapt.togglit.util.StringUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends BaseActivity implements SettingsView {
    @Inject
    SharedPreferencesManager mSharedPreferencesManager;

    @Inject
    SettingsPresenter mSettingsPresenter;

    @BindView(R.id.home_settings_toolbar)
    CustomizableToolbar mToolbar;

    @BindView(R.id.tool_bar_title)
    TextView mToolbarTitle;

    @BindView(R.id.tv_app_support)
    TextView mTvAppSupport;

    @BindView(R.id.tv_share_app)
    TextView mTvShareApp;

    @BindView(R.id.tv_rate_app)
    TextView mTvRateApp;

    @BindView(R.id.tv_version)
    TextView mTvVersion;

    @BindView(R.id.tv_version_number)
    TextView mAppVersionNumber;

    @BindView(R.id.tv_terms)
    TextView mTvTerms;

    @BindView(R.id.tv_log_out)
    TextView mLogInItemTitle;

    @BindView(R.id.tv_log_out_email)
    TextView mLoggedEmailUser;


    private boolean isLogged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        initializeViews();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.color_control_normal_blue));
        }

        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.ic_arrow_left);
            upArrow.setColorFilter(ContextCompat.getColor(this, R.color.colorAccentWhite), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(null);
        }
    }

    public void initializeViews() {

        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.ic_arrow_left);
            upArrow.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(null);
        }
        mSettingsPresenter.attachView(this);
        mSettingsPresenter.validateAuthenticationSession();
    }

    @OnClick(R.id.content_app_support_and_feedback)
    public void appSupportAndFeedbackItemAction() {
    }

    @OnClick(R.id.content_share_app)
    public void contentShareAction() {

    }

    @OnClick(R.id.content_rate_app)
    public void rateAppAction() {

    }

    @OnClick(R.id.content_log_out)
    public void logoutAction() {
        if (isLogged) {
            mSettingsPresenter.logout();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.content_terms)
    public void termsAndConditionsAction() {

    }

    @Override
    public void showLoggedUserProfileInfo(Volunteer loggedUser) {
        if (loggedUser.getEmail() != null) {
            mLoggedEmailUser.setText(!StringUtil.isNullOrEmpty(loggedUser.getEmail()) ? loggedUser.getEmail() : "");
            isLogged = true;
        }
    }

    @Override
    public void showLogInActionItem() {
        mLogInItemTitle.setText(R.string.settings_log_in_item_title);
        isLogged = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSettingsPresenter.detachView();
    }
}
