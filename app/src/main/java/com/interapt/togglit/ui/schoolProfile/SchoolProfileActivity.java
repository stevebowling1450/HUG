package com.interapt.togglit.ui.schoolProfile;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.interapt.togglit.R;
import com.interapt.togglit.data.model.user.School;
import com.interapt.togglit.ui.base.BaseActivity;
import com.interapt.togglit.ui.custom.views.CustomizableToolbar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SchoolProfileActivity extends BaseActivity implements SchoolProfileView {

    @Inject
    SchoolProfilePresenter mSchoolProfilePresenter;

    @BindView(R.id.school_profile_toolbar)
    CustomizableToolbar schoolBar;

    @BindView(R.id.school_bar_title)
    TextView schoolBarTitle;

    @BindView(R.id.phone_number)
    TextView schoolPhoneNumber;

    @BindView(R.id.principal_name)
    TextView schoolPrincipalName;

    @BindView(R.id.student_goal_clarity_person_name)
    TextView studentGoalClarityPersonName;

    @BindView(R.id.student_goal_clarity_person_phone_number)
    TextView studentGoalClarityPersonPhoneNumber;

    @BindView(R.id.student_goal_clarity_person_email_address)
    TextView studentGoalClarityPersonEmailAddress;

    @BindView(R.id.school_address)
    TextView schoolAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_school_profile);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        int volId = intent.getIntExtra("SchoolId", 0);
        mSchoolProfilePresenter.attachView(this);
        mSchoolProfilePresenter.onCreate(volId);


        setSupportActionBar(schoolBar);
        if (getSupportActionBar() != null) {
            final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.ic_arrow_left);
            upArrow.setColorFilter(ContextCompat.getColor(this, R.color.colorAccentWhite), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(null);

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //noinspection deprecation
            getWindow().setStatusBarColor(getResources().getColor(R.color.color_control_normal_blue));
        }

    }


    @Override
    public void setSchool(School school) {

        schoolBarTitle.setText(school.getSchoolName());

        schoolPhoneNumber.setText(school.getSchoolPhone());

        schoolPrincipalName.setText(school.getPrincipalName());

        studentGoalClarityPersonName.setText(school.getClarityPersonName());

        studentGoalClarityPersonPhoneNumber.setText(formatPhoneNumber(school.getClarityPersonPhone()));

        studentGoalClarityPersonEmailAddress.setText(school.getClarityPersonEmail());

        schoolAddress.setText(school.getAddress() + "\n" + school.getSchoolCity() + ", " + school.getSchoolState() + " " + school.getSchoolZip());

    }
}
