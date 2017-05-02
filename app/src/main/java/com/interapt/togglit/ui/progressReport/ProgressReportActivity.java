package com.interapt.togglit.ui.progressReport;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.ViewPager;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.interapt.togglit.R;
import com.interapt.togglit.data.model.user.Student;
import com.interapt.togglit.ui.base.BaseActivity;
import com.interapt.togglit.ui.custom.views.CustomizableToolbar;
import com.interapt.togglit.ui.progressReport.acumen.AcumenFragment;
import com.interapt.togglit.ui.progressReport.lesson.LessonFragment;
import com.interapt.togglit.ui.progressReport.lesson.lessonList.LessonListFragment;
import com.interapt.togglit.ui.progressReport.overview.OverviewFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Matthew.Watson on 2/20/17.
 */

public class ProgressReportActivity extends BaseActivity implements ProgressReportView {

    @Inject
    ProgressReportPresenter mProgressReportPresenter;

    @BindView(R.id.progress_report_iv)
    ImageView imageView2;

    private Fragment selectedFragment;

    @BindView(R.id.progress_report_toolbar)
    CustomizableToolbar reportToolbar;

    @BindView(R.id.progress_report_title)
    TextView progressReportTitle;

    @BindView(R.id.progress_report_student_name)
    TextView studentName;

    @BindView(R.id.progress_report_school_name)
    TextView schoolName;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.student_profile_initial)
    TextView profileInitials;

    private Integer getStudentId() {
        return getIntent().getIntExtra("StudentId", 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_progress_report);
        ButterKnife.bind(this);

        mProgressReportPresenter.attachView(this);

        Intent intent = getIntent();
        int studentId = intent.getIntExtra("StudentId", 0);
        mProgressReportPresenter.onCreate(studentId);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //noinspection deprecation
            getWindow().setStatusBarColor(
                    getResources().getColor(R.color.color_control_normal_blue));
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        setSupportActionBar(reportToolbar);
        if (getSupportActionBar() != null) {
            final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.ic_arrow_left);
            upArrow.setColorFilter(ContextCompat.getColor(this, R.color.colorAccentWhite),
                    PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(null);
        }

        progressReportTitle.setText(R.string.progress_report);

        if(intent.getExtras().getString("imageString") != null){
            String imageString = intent.getExtras().getString("imageString");
            setItemImage(imageString,imageView2);
            profileInitials.setVisibility(View.INVISIBLE);
        }
        
    }

    private void setInitials(Student student) {
        String firstI = String.valueOf(student.getFirstName().charAt(0));
        String lastI = (String.valueOf(student.getLastName().charAt(0)));
        String initials = firstI + lastI;
        profileInitials.setText(initials);
    }

    private Adapter adapter;

    private Adapter getAdapter() {
        if (adapter == null) {
            adapter = new Adapter(getSupportFragmentManager());
            adapter.addFragment(OverviewFragment.newInstance(getStudentId()), "Overview");
            adapter.addFragment(AcumenFragment.newInstance(getStudentId()), "Acumen");
            adapter.addFragment(LessonFragment.newInstance(getStudentId()), "Lessons");
        }
        return adapter;
    }

    private void setupViewPager(ViewPager viewPager) {
        viewPager.setAdapter(getAdapter());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                selectedFragment = adapter.getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void setStudent(Student student) {
        setInitials(student);
        studentName.setText(student.getFirstName() + " " + student.getLastName());
        schoolName.setText(student.getSchoolName());


        Bundle bundle = new Bundle();
        bundle.putInt("StudentId", student.getId());
        LessonListFragment lessonListFragment = new LessonListFragment();
        lessonListFragment.setArguments(bundle);

        if(student.getImage().length() > 50){
            setItemImage(student.getImage(),imageView2);
            profileInitials.setVisibility(View.INVISIBLE);
        }
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        if (selectedFragment != null
                && selectedFragment.getChildFragmentManager().getBackStackEntryCount() > 1) {
            selectedFragment.getChildFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public void setItemImage(String bitImage, ImageView imageView) {

        byte[] imageByteArray = Base64.decode(bitImage, Base64.DEFAULT);

        Glide.with(ProgressReportActivity.this).load(imageByteArray).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(ProgressReportActivity.this.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }
}

