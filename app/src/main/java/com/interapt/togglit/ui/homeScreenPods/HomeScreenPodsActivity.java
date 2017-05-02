package com.interapt.togglit.ui.homeScreenPods;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.interapt.togglit.R;
import com.interapt.togglit.common.SharedPreferencesManager;
import com.interapt.togglit.ui.base.BaseActivity;
import com.interapt.togglit.ui.custom.views.CustomizableToolbar;
import com.interapt.togglit.ui.events.EventsActivity;
import com.interapt.togglit.ui.lessonList.LessonListActivity;
import com.interapt.togglit.ui.schoolDirectory.SchoolDirectoryActivity;
import com.interapt.togglit.ui.settings.SettingsActivity;
import com.interapt.togglit.ui.studentDirectory.StudentDirectoryActivity;
import com.interapt.togglit.ui.volunteerDirectory.VolunteerDirectoryActivity;
import com.wang.avi.AVLoadingIndicatorView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class HomeScreenPodsActivity extends BaseActivity
        implements HomeScreenPodsView, NavigationView.OnNavigationItemSelectedListener {

    @Inject
    HomeScreenPodsPresenter mHomeScreenPodsPresenter;

    @Inject
    SharedPreferencesManager mSharedPreferencesManager;

    @BindView(R.id.home_screen_toolbar)
    CustomizableToolbar homeScreenBar;

    @BindView(R.id.drawer_layout_home_pods)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    @BindView(R.id.recycler_view_home_pods)
    RecyclerView mRecyclerViewPods;

    @BindView(R.id.drawer_menu_logo)
    ImageView mDrawerMenuLogo;

    @BindView(R.id.ic_nav_menu_settings)
    ImageView mItemSettings;

    @BindView(R.id.nav_menu_settings_item)
    TextView mTvNavMenuSettingsItems;

    @BindView(R.id.loading_avl_panel_progress_container)
    RelativeLayout mAVLoading;

    @BindView(R.id.avi)
    AVLoadingIndicatorView mAviProgress;

    @BindView(R.id.student_dir)
    TextView studentDir;

    @BindView(R.id.item_menu_settings_container)
    RelativeLayout mItemNavMenuSettingsContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_home_screen_pods);
        ButterKnife.bind(this);

        setSupportActionBar(homeScreenBar);
        if (getSupportActionBar() != null) {
            final Drawable menuIcon = ContextCompat.getDrawable(this, R.drawable.ic_hamburguer_custom_icon);
            menuIcon.setColorFilter(ContextCompat.getColor(this, R.color.colorAccentWhite), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(menuIcon);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(null);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.color_control_normal_blue));
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHomeScreenPodsPresenter.detachView();

    }


    // TODO: 2/16/17 Add switch to handle nav items
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Timber.d("NAVIGATION MENU SELECTION %s %s", item, id);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    public void closeDrawerMenu() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    @OnClick(R.id.item_menu_settings_container)
    public void navMenuSettingsAction() {
        Intent settingsIntent = new Intent(this, SettingsActivity.class);
        callActivity(settingsIntent);
        closeDrawerMenu();
    }


    //// TODO: 3/28/17 Find the rest of these and use them to display snackbar errors instead of toasts
    @Override
    public void showError(String message, int errorType) {
    }


    @Override
    public void showRefresh(boolean show) {
        mAVLoading.setVisibility(show ? View.VISIBLE : View.GONE);
    }


    //NavDrawer items
    @OnClick(R.id.student_dir)
    public void goToStudentDirectoryActivity() {
        Intent intent = new Intent(this, StudentDirectoryActivity.class);
        startActivity(intent);
        closeDrawerMenu();
    }

    @OnClick(R.id.lesson_guide)
    public void goToLessonGuideActivity() {
        Intent intent = new Intent(this, LessonListActivity.class);
        startActivity(intent);
        closeDrawerMenu();
    }

    @OnClick(R.id.volunteer_dir)
    public void goToVolunteerDirectoryActivity() {
        Intent intent = new Intent(this, VolunteerDirectoryActivity.class);
        startActivity(intent);
        closeDrawerMenu();
    }

    @OnClick(R.id.school_dir)
    public void goToSchoolDirectoryActivity() {
        Intent intent = new Intent(this, SchoolDirectoryActivity.class);
        startActivity(intent);
        closeDrawerMenu();
    }

    @OnClick(R.id.volunteer_schedule)
    public void goToEventsActivity() {
        Intent intent = new Intent(this, EventsActivity.class);
        startActivity(intent);
    }




    //Pods
    @OnClick(R.id.student_image)
    public void goToStudentProfileActivity() {
        Intent intent = new Intent(this, StudentDirectoryActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.volunteer_image)
    public void goToVolunteerProfileActivity() {
        Intent intent = new Intent(this, VolunteerDirectoryActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.school_image)
    public void goToSchoolDirPod() {
        Intent intent = new Intent(this, SchoolDirectoryActivity.class);
        startActivity(intent);
    }




}
