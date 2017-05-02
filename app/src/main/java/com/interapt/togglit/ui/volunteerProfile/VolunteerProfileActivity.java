package com.interapt.togglit.ui.volunteerProfile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.interapt.togglit.R;
import com.interapt.togglit.data.model.user.School;
import com.interapt.togglit.data.model.user.Student;
import com.interapt.togglit.data.model.user.Volunteer;
import com.interapt.togglit.ui.base.BaseActivity;
import com.interapt.togglit.ui.custom.views.CustomizableToolbar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Matthew.Watson on 2/9/17.
 */

public class VolunteerProfileActivity extends BaseActivity implements VolunteerProfileView {

    @Inject
    VolunteerProfilePresenter mVolunteerProfilePresenter;

    @BindView(R.id.circle_profile_iv)
    ImageView volunteerProfile;

    @BindView(R.id.vol_name)
    TextView volunteerFullName;

    @BindView(R.id.commitment_level_profile_top)
    TextView commitmentLevel;

    @BindView(R.id.phone_no_profile_display)
    TextView phoneNoDisplay;

    @BindView(R.id.alt_phone_no_profile_display)
    TextView altPhoneNoDisplay;

    @BindView(R.id.emergency_cont_display)
    TextView emergencyContact;

    @BindView(R.id.emergency_cont_phone_display)
    TextView emergencyContactPhone;

    @BindView(R.id.email_profile_display)
    TextView emailDisplay;

    @BindView(R.id.vol_profile_toolbar)
    CustomizableToolbar volBar;

    @BindView(R.id.volunteer_bar_title)
    TextView volBarTitle;

    @BindView(R.id.vol_profile_initial)
    TextView volInitial;

    @BindView(R.id.vol_students_display)
    TextView volStudents;

    @BindView(R.id.vol_schools_display)
    TextView volSchools;

    @BindView(R.id.vol_schools)
    TextView volSchoolsLabel;

    @BindView(R.id.vol_students)
    TextView volStudentsLabel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_volunteer_profile);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        int volId = intent.getIntExtra("VolId", 0);
        mVolunteerProfilePresenter.attachView(this);
        mVolunteerProfilePresenter.onCreate(volId);


        setSupportActionBar(volBar);
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

        volBarTitle.setText(R.string.volunteer_profile_toolbar_title);
    }


    @Override
    public void setVolunteer(Volunteer volunteer) {
        if (volunteer.getFirstName() != null && volunteer.getLastName() != null) {
            String firstI = String.valueOf(volunteer.getFirstName().charAt(0));
            String lastI = (String.valueOf(volunteer.getLastName().charAt(0)));
            String initials = firstI + lastI;
            volInitial.setText(initials);
            volunteerFullName.setText(volunteer.getFirstName() + " " + volunteer.getLastName());

            if (volunteer.getImage() != null && volunteer.getImage().toCharArray().length >= 10) {
                volInitial.setVisibility(View.GONE);
                setItemImage(volunteer.getImage(), volunteerProfile);
            }
        }

        commitmentLevel.setText(volunteer.getCommitmentLevel());
        phoneNoDisplay.setText(formatPhoneNumber(volunteer.getPhone()));
        altPhoneNoDisplay.setText(formatPhoneNumber(volunteer.getSecondPhone()));
        emergencyContact.setText(volunteer.getEFirstName() + " " + volunteer.getELastName());
        emergencyContactPhone.setText(formatPhoneNumber(volunteer.getEmergencyContactNumber()));
        emailDisplay.setText(volunteer.getEmail());


        if (volunteer.getSchools().size() != 0) {
            StringBuilder schoolBuilder = new StringBuilder();
            for (School school : volunteer.getSchools()) {
                schoolBuilder.append(school.getSchoolName()).append("\n");
            }
            volSchools.setText(schoolBuilder.toString());
        } else volSchoolsLabel.setVisibility(View.GONE);

        if (volunteer.getStudents().size() != 0) {
            StringBuilder studentBuilder = new StringBuilder();
            for (Student student : volunteer.getStudents()) {
                studentBuilder.append(student.getFirstName()).append(" ").append(student.getLastName()).append("\n");
            }
            volStudents.setText(studentBuilder.toString());
        } else volStudentsLabel.setVisibility(View.GONE);
    }

    public void setItemImage(String bitImage, ImageView imageView) {
        byte[] imageByteArray = Base64.decode(bitImage, Base64.DEFAULT);

        Glide.with(this).load(imageByteArray).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(VolunteerProfileActivity.this.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }


}
