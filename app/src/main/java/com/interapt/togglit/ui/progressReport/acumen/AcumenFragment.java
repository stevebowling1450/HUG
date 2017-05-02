package com.interapt.togglit.ui.progressReport.acumen;

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
import com.interapt.togglit.data.model.session.StudentLessonAcumen;
import com.interapt.togglit.ui.progressReport.lesson.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Matthew.Watson on 2/22/17.
 */

public class AcumenFragment extends BaseFragment implements AcumenFragmentView{

    @Inject
    AcumenLessonListAdapter mAcumenLessonListAdapter;

    @Inject
    AcumenFragmentPresenter mAcumenFragmentPresenter;


    @BindView(R.id.recycler_view_acumen)
    RecyclerView mAcumenRecycler;

    @BindView(R.id.acumen_layout_frame)
    FrameLayout mAcumenLayoutFrame;




    private final static String STUDENT_ID = "StudentId";

    public static AcumenFragment newInstance(Integer studentId) {
        Bundle args = new Bundle();
        args.putInt(STUDENT_ID, studentId);
        AcumenFragment fragment = new AcumenFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
    }

    @Override public void onResume() {
        super.onResume();
        setupRecyclerView(mAcumenRecycler);
    }

    @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                                 Bundle savedInstanceState) {
        FrameLayout lessonLayout =
                (FrameLayout) inflater.inflate(R.layout.fragment_acumen, container,
                        false);
        ButterKnife.bind(this, lessonLayout);
        return lessonLayout;
    }

    private void setupRecyclerView(RecyclerView lessonRecycler) {
        mAcumenFragmentPresenter.attachView(this);
        Integer studentId = getArguments().getInt(STUDENT_ID);
        mAcumenFragmentPresenter.onCreate(studentId);

        lessonRecycler.setAdapter(mAcumenLessonListAdapter);
        lessonRecycler.setLayoutManager(new LinearLayoutManager(lessonRecycler.getContext()));

    }

    @Override public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public void showError(String message, @Constants.ErrorType int errorType) {

    }

    @Override
    public void showRefresh(boolean show) {

    }


    @Override
    public void setFragmentSessions(List<StudentLessonAcumen> sessions) {
        mAcumenLessonListAdapter.populateSessionList(sessions);


    }

    @Override
    public void showError(String s) {

    }

}
