package com.interapt.togglit.ui.progressReport.lesson.lessonList;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.interapt.togglit.R;
import com.interapt.togglit.common.Constants;
import com.interapt.togglit.data.model.lists.LessonsNoImage;
import com.interapt.togglit.ui.progressReport.lesson.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Matthew.Watson on 2/21/17.
 */

public class LessonListFragment extends BaseFragment implements LessonListFragmentView {

    @Inject
    LessonListFragmentAdapter mLessonListFragmentAdapter;

    @Inject
    LessonListFragmentPresenter mLessonListFragmentPresenter;

    @BindView(R.id.lessons_recycler)
    RecyclerView mLessonRecycler;

    @BindView(R.id.lesson_container)
    FrameLayout mLessonContainer;

    List<Integer> studentLessons;

    private final static String STUDENT_ID = "StudentId";

    private Callback callback;

    public interface Callback {
        void onLessonClicked(Integer lessonId, String lessonIdentifier);
    }

    public static LessonListFragment newInstance(Integer studentId) {
        Bundle args = new Bundle();
        args.putInt(STUDENT_ID, studentId);
        LessonListFragment fragment = new LessonListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        setupRecyclerView(mLessonRecycler);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FrameLayout lessonLayout =
                (FrameLayout) inflater.inflate(R.layout.fragment_progress_report_lessons, container,
                        false);
        ButterKnife.bind(this, lessonLayout);
        return lessonLayout;
    }

    private void setupRecyclerView(RecyclerView lessonRecycler) {
        mLessonListFragmentPresenter.attachView(this);
        lessonRecycler.setAdapter(mLessonListFragmentAdapter);
        lessonRecycler.setLayoutManager(new LinearLayoutManager(lessonRecycler.getContext()));

        Integer studentId = getArguments().getInt(STUDENT_ID);
        mLessonListFragmentPresenter.onCreate(studentId);

        mLessonListFragmentAdapter.setOnItemClickListener((view, lesson) -> {
            if (callback != null) {
                callback.onLessonClicked(lesson.getId() ,lesson.getLessonIdentifier());
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (Callback) getParentFragment();
    }


    @Override
    public void showError(String message, @Constants.ErrorType int errorType) {

    }

    @Override
    public void showRefresh(boolean show) {
    }

    @Override
    public void showLessons(List<Integer> lessons) {
        mLessonListFragmentPresenter.getLessons(lessons);
    }

    @Override
    public void setLesson(List<LessonsNoImage> allLesson) {
        mLessonListFragmentAdapter.populateLessonList(allLesson);

    }




}