package com.interapt.togglit.ui.studentDirectory;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.interapt.togglit.R;
import com.interapt.togglit.common.Constants;
import com.interapt.togglit.data.model.lists.Students;
import com.interapt.togglit.ui.base.BaseActivity;
import com.interapt.togglit.ui.custom.views.CustomizableToolbar;
import com.interapt.togglit.ui.filter.FilterActivity;
import com.interapt.togglit.ui.homeScreenPods.HomeScreenPodsActivity;
import com.interapt.togglit.ui.studentProfile.StudentProfileActivity;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.myinnos.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView;

public class StudentDirectoryActivity extends BaseActivity implements StudentDirectoryView, NavigationView.OnNavigationItemSelectedListener {

    @Inject
    StudentDirectoryPresenter studentDirectoryPresenter;

    @Inject
    StudentDirectoryAdapter studentDirectoryAdapter;

    @BindView(R.id.student_toolbar)
    CustomizableToolbar customizableToolbar;

    @BindView(R.id.student_recycler)
    IndexFastScrollRecyclerView studentRecycler;


//    @BindView(R.id.swipe_layout)
//    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.loading_layout)
    RelativeLayout loadingLayout;

    @BindView(R.id.avi)
    AVLoadingIndicatorView mAviProgress;

    public ArrayList<Integer> filteredList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_directory);
        ButterKnife.bind(this);
        getActivityComponent().inject(this);
        Intent intent2 = getIntent();
        if (intent2.getExtras() != null) {
            filteredList = intent2.getIntegerArrayListExtra("filteredList");
            if (filteredList == null) {
                filteredList = new ArrayList<>();
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.color_control_normal_blue));
        }


        showRefresh(true);
        studentRecycler.setAdapter(studentDirectoryAdapter);

//        mSwipeRefreshLayout.setOnRefreshListener(() -> scrollRefresh());

        Intent intent = new Intent(this, StudentProfileActivity.class);
        studentDirectoryAdapter.setOnItemClickListener((view, student) -> {
            intent.putExtra("StudentId", student.getId());
            intent.putExtra("UserId", student.getUserId());
           startActivity(intent);
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(StudentDirectoryActivity.this);
        studentRecycler.setLayoutManager(linearLayoutManager);
        studentDirectoryPresenter.attachView(this);
        studentDirectoryPresenter.onCreate(filteredList);
        initialiseUI();
    }

    @Override
    public void setStudentList(List<Students> students) {
        studentDirectoryAdapter.populateStudentList(students);
    }


    @Override
    public void showError(String message, @Constants.ErrorType int errorType) {

    }

    @Override
    public void showRefresh(boolean show) {
        loadingLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    protected void initialiseUI() {
        mAviProgress.setIndicatorColor(Color.parseColor("#00b2e2"));
        studentRecycler.setIndexTextSize(10);
        studentRecycler.setIndexBarColor("#ffffff");
        studentRecycler.setIndexBarCornerRadius(0);
        studentRecycler.setIndexBarTransparentValue((float) 0);
        studentRecycler.setIndexbarMargin(5);
        studentRecycler.setIndexbarWidth(20);
        studentRecycler.setPreviewPadding(1);
        studentRecycler.setIndexBarTextColor("#00b3e2");
    }

//    public void scrollRefresh(){
//        studentDirectoryPresenter.onCreate(filteredList);
//        mSwipeRefreshLayout.setRefreshing(false);
//    }

    @OnClick(R.id.filter)
    public void goToFilter() {
        FilterActivity.whereTOGo = 1;
        Intent intent = new Intent(this, FilterActivity.class);
        if (!filteredList.isEmpty()) {
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        }
        startActivity(intent);
    }

    @OnClick(R.id.back_arrow)
    public void goToHome() {
        Intent intent = new Intent(this, HomeScreenPodsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeScreenPodsActivity.class);
        startActivity(intent);

    }
}

