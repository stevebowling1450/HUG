package com.interapt.togglit.ui.lessonList;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.interapt.togglit.R;
import com.interapt.togglit.data.model.lists.LessonsNoImage;
import com.interapt.togglit.ui.base.BaseActivity;
import com.interapt.togglit.ui.custom.views.CustomizableToolbar;
import com.interapt.togglit.ui.lessonDetail.LessonDetailActivity;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Matthew.Watson on 2/16/17.
 */

public class LessonListActivity extends BaseActivity implements LessonListView {

    @Inject
    LessonListPresenter mLessonListPresenter;

    @Inject
    LessonListAdapter mLessonListAdapter;

    @BindView(R.id.lesson_recycler)
    RecyclerView mLessonRecycler;

    @BindView(R.id.lesson_list_toolbar)
    CustomizableToolbar lessonListToolbar;

    @BindView(R.id.lesson_list)
    TextView lessonListTitle;

    @BindView(R.id.loading_layout)
    RelativeLayout loadingLayout;

//    @BindView(R.id.swipe_layout)
//    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.avi)
    AVLoadingIndicatorView mAviProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_lesson_list);
        ButterKnife.bind(this);

        showRefresh(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.color_control_normal_blue));
        }
        setSupportActionBar(lessonListToolbar);
        if (getSupportActionBar() != null) {
            final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.ic_arrow_left);
            upArrow.setColorFilter(ContextCompat.getColor(this, R.color.colorAccentWhite), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(null);
        }

        lessonListTitle.setText(R.string.lesson_guide);

        mLessonListPresenter.attachView(this);
        mLessonListPresenter.onCreate();

        mLessonRecycler.setAdapter(mLessonListAdapter);
        mLessonRecycler.setLayoutManager(new LinearLayoutManager(this));
        mAviProgress.setIndicatorColor(Color.parseColor("#00b2e2"));
//        mSwipeRefreshLayout.setOnRefreshListener(() -> scrollRefresh());



        Intent intent = new Intent(this, LessonDetailActivity.class);

        mLessonListAdapter.setOnItemClickListener(new LessonListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, LessonsNoImage lesson) {
                intent.putExtra("id", lesson.getId());
                LessonListActivity.this.startActivity(intent);
            }
        });

    }

    @Override
    public void showRefresh(boolean show) {
        loadingLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }
    @Override
    public void setLesson(List<LessonsNoImage> lesson) {
        mLessonListAdapter.populateLessonList(lesson);
    }
}
