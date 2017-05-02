package com.interapt.togglit.ui.volunteerDirectory;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.interapt.togglit.R;
import com.interapt.togglit.data.model.lists.Volunteers;
import com.interapt.togglit.ui.base.BaseActivity;
import com.interapt.togglit.ui.custom.views.CustomizableToolbar;
import com.interapt.togglit.ui.filter.FilterActivity;
import com.interapt.togglit.ui.homeScreenPods.HomeScreenPodsActivity;
import com.interapt.togglit.ui.volunteerProfile.VolunteerProfileActivity;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.myinnos.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView;

/**
 * Created by nicholashall on 2/13/17.
 */

public class VolunteerDirectoryActivity extends BaseActivity implements VolunteerDirectoryView, NavigationView.OnNavigationItemSelectedListener {


    @Inject
    VolunteerDirectoryPresenter volunteerDirectoryPresenter;

    @Inject
    VolunteerDirectoryAdapter volunteerDirectoryAdapter;

    @BindView(R.id.vol_toolbar)
    CustomizableToolbar customizableToolbar;

    @BindView(R.id.vol_bar_title)
    TextView vToolBarTitle;

    @BindView(R.id.recycler_view_volunteer)
    IndexFastScrollRecyclerView recyclerView;

    @BindView(R.id.filter)
    ImageView filter;

    public ArrayList<Integer> filteredList = new ArrayList();

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
        setContentView(R.layout.activity_volunteer_directory);
        ButterKnife.bind(this);
        Intent intent2 = getIntent();
        if (intent2.getExtras() != null) {
            filteredList = intent2.getIntegerArrayListExtra("filteredList");
            if (filteredList == null) {
                filteredList = new ArrayList<>();
            }
        }
        showRefresh(true);
        volunteerDirectoryPresenter.attachView(this);
        volunteerDirectoryPresenter.onCreate(filteredList);

        recyclerView.setAdapter(volunteerDirectoryAdapter);

//        mSwipeRefreshLayout.setOnRefreshListener(() -> scrollRefresh());

        Intent intent = new Intent(this, VolunteerProfileActivity.class);

        volunteerDirectoryAdapter.setOnItemClickListener((view, volunteer) -> {
            intent.putExtra("VolId", volunteer.getId());
            VolunteerDirectoryActivity.this.startActivity(intent);
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //noinspection deprecation
            getWindow().setStatusBarColor(getResources().getColor(R.color.color_control_normal_blue));
        }

        vToolBarTitle.setText(R.string.volunteer_toolbar_title);
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

    @Override
    public void getVolunteerList(List<Volunteers> volunteerList) {

        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return volunteerDirectoryAdapter.getItemCount();
            }

            @Override
            public int getNewListSize() {
                return volunteerList.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return volunteerDirectoryAdapter.get(oldItemPosition).getId().equals(volunteerList.get(newItemPosition).getId());

            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return volunteerDirectoryAdapter.get(oldItemPosition) == volunteerList.get(newItemPosition);
            }
        });
        volunteerDirectoryAdapter.populateVolunteerList(volunteerList);
        result.dispatchUpdatesTo(volunteerDirectoryAdapter);

    }


    @Override
    public void showRefresh(boolean show) {
        loadingLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @OnClick(R.id.filter)
    public void goFilter(){
        FilterActivity.whereTOGo=2;
        Intent intent = new Intent(this, FilterActivity.class);
        if (!filteredList.isEmpty()) {
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        }
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeScreenPodsActivity.class);
        startActivity(intent);

    }

    @OnClick(R.id.back_arrow)
    public void goHome(){
        Intent intent = new Intent(this, HomeScreenPodsActivity.class);
        startActivity(intent);
    }


//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if(ev.getAction() == MotionEvent.ACTION_DOWN && )
////        Get the x and y of the index
////        If the x and y of the index == Action down don't send MotionEvents to the recycler view
//        ev.getActionIndex();
//        ev.getAction();
//        ev.getY();
//        ev.getX();
//        return super.dispatchTouchEvent(ev);
//    }


}