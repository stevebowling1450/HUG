package com.interapt.togglit.ui.studentProfile;

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
import com.interapt.togglit.common.ISharedPreferencesManager;
import com.interapt.togglit.data.model.user.Student;
import com.interapt.togglit.data.remote.IDataManager;
import com.interapt.togglit.ui.base.BaseActivity;
import com.interapt.togglit.ui.custom.views.CustomizableButtonView;
import com.interapt.togglit.ui.custom.views.CustomizableToolbar;
import com.interapt.togglit.ui.progressReport.ProgressReportActivity;
import com.interapt.togglit.ui.updateProgress.UpdateProgressActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StudentProfileActivity extends BaseActivity implements StudentProfileView {

    @Inject
    StudentProfilePresenter mStudentProfilePresenter;

    @Inject
    ISharedPreferencesManager iSharedPreferencesManager;

    @Inject
    IDataManager dataManager;

    @BindView(R.id.student_profile_iv)
    ImageView imageView1;

    @BindView(R.id.pri_vol_name_tv)
    TextView priVolName;

    @BindView(R.id.student_name)
    TextView studentName;

    @BindView(R.id.pri_vol_phone_tv)
    TextView priEmail;

    @BindView(R.id.emergency_cont_tv)
    TextView emergencyContName;

    @BindView(R.id.student_profile_toolbar)
    CustomizableToolbar studentBar;

    @BindView(R.id.student_profile_initial)
    TextView profileInitials;

    @BindView(R.id.button_update_prog)
    CustomizableButtonView updateButton;

    @BindView(R.id.button_view_prog)
    CustomizableButtonView viewButton;

    @BindView(R.id.student_school_name_profile_top)
    TextView schoolName;

    @BindView(R.id.volun_cont_phone_tv)
    TextView volPhone;


    private String sName;
    private String initials;
    private String schName;
    int studentId;
    String B64;
    Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_student_profile);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        studentId = intent.getIntExtra("StudentId", 0);
        int volID = intent.getIntExtra("UserId", 0);
        mStudentProfilePresenter.attachView(this);
        mStudentProfilePresenter.onCreate(studentId);
        mStudentProfilePresenter.getStudentVolunteer(volID);


        setSupportActionBar(studentBar);
        if (getSupportActionBar() != null) {
            final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.ic_arrow_left);
            upArrow.setColorFilter(ContextCompat.getColor(this, R.color.colorAccentWhite), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(null);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.color_control_normal_blue));
        }
    }


    @Override
    public void getStudent(Student student) {
        String fName = student.getFirstName();
        String lName = student.getLastName();
        sName = fName + " " + lName;
        studentName.setText(sName);
        schName = student.getSchoolName();
        schoolName.setText(student.getSchoolName());
        String firstI = String.valueOf(fName.charAt(0));
        String lastI = (String.valueOf(lName.charAt(0)));
        initials = firstI + lastI;
        profileInitials.setText(initials.toUpperCase());
        priVolName.setText(student.getVolName());
        priEmail.setText(student.getVolEmail());

        if (student.getImage().length() > 50) {
            B64 = student.getImage();
            profileInitials.setVisibility(View.INVISIBLE);
            setItemImage(B64, imageView1);
        }
        updateButton.setText("Update Progress");
        viewButton.setText("View Progress");
    }

    @Override
    public void getVolunteerPhoneNumber(String volunteerPhone) {
        volPhone.setText(formatPhoneNumber(volunteerPhone));

    }


    @OnClick(R.id.button_view_prog)
    public void viewStudentProgress() {
        intent = new Intent(this, ProgressReportActivity.class);
        intent.putExtra("StudentId", studentId);
        startActivity(intent);
    }

    @OnClick(R.id.button_update_prog)
    public void startUpdate() {
        Intent intent = new Intent(this, UpdateProgressActivity.class);
        intent.putExtra("StudentId", studentId);
        intent.putExtra("studentName", sName);
        intent.putExtra("initials", initials);
        intent.putExtra("schoolName", schName);
        intent.putExtra("imageString", B64);
        startActivity(intent);
    }

    public void setItemImage(String bitImage, ImageView imageView) {
        byte[] imageByteArray = Base64.decode(bitImage, Base64.DEFAULT);
        Glide.with(this).load(imageByteArray).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(StudentProfileActivity.this.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }
}
