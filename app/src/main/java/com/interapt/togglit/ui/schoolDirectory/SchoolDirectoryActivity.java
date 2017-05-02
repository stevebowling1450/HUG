package com.interapt.togglit.ui.schoolDirectory;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.interapt.togglit.R;
import com.interapt.togglit.data.model.lists.Schools;
import com.interapt.togglit.ui.base.BaseActivity;
import com.interapt.togglit.ui.custom.views.CustomizableToolbar;
import com.interapt.togglit.ui.schoolProfile.SchoolProfileActivity;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.myinnos.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView;

/**
 * Created by nicholashall on 2/22/17.
 */

public class SchoolDirectoryActivity extends BaseActivity implements SchoolDirectoryView, NavigationView.OnNavigationItemSelectedListener {

    @Inject
    SchoolDirectoryPresenter schoolDirectoryPresenter;

    @Inject
    SchoolDirectoryAdapter schoolDirectoryAdapter;

    @BindView(R.id.school_toolbar)
    CustomizableToolbar customizableToolbar;

    @BindView(R.id.school_bar_title)
    TextView vToolBarTitle;

    @BindView(R.id.recycler_view_school)
    IndexFastScrollRecyclerView recyclerView;

//    @BindView(R.id.swipe_layout)
//    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.loading_layout)
    RelativeLayout loadingLayout;

    @BindView(R.id.avi)
    AVLoadingIndicatorView mAviProgress;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_school_directory);
        ButterKnife.bind(this);
        showRefresh(true);


        recyclerView.setAdapter(schoolDirectoryAdapter);
//        mSwipeRefreshLayout.setOnRefreshListener(() -> scrollRefresh());
        Intent intent = new Intent(this, SchoolProfileActivity.class);

        schoolDirectoryAdapter.setOnItemClickListener((view, school) -> {
            intent.putExtra("SchoolId", school.getId());
            startActivity(intent);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        schoolDirectoryPresenter.attachView(this);

        schoolDirectoryPresenter.onCreate();

        setSupportActionBar(customizableToolbar);
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

        vToolBarTitle.setText("School Directory");

        initialiseUI();
    }


    protected void initialiseUI() {
        mAviProgress.setIndicatorColor(Color.parseColor("#00b2e2"));
        recyclerView.setIndexTextSize(10);
        recyclerView.setIndexBarColor("#ffffff");
        recyclerView.setIndexBarCornerRadius(0);
        recyclerView.setIndexBarTransparentValue((float) 0);
        recyclerView.setIndexbarMargin(5);
        recyclerView.setIndexbarWidth(20);
        recyclerView.setPreviewPadding(1);
        recyclerView.setIndexBarTextColor("#00b3e2");

    }

//    public void scrollRefresh(){
//        schoolDirectoryPresenter.onCreate();
//        mSwipeRefreshLayout.setRefreshing(false);
//    }

    @Override
    public void getSchoolList(List<Schools> schoolList) {
        schoolDirectoryAdapter.populateSchoolList(schoolList);
    }

    @Override
    public void showRefresh(boolean show) {
        loadingLayout.setVisibility(show ? View.VISIBLE : View.GONE);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

}
