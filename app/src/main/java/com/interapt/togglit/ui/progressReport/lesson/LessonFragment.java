package com.interapt.togglit.ui.progressReport.lesson;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.interapt.togglit.R;
import com.interapt.togglit.ui.progressReport.lesson.lessonList.LessonListFragment;
import com.interapt.togglit.ui.progressReport.lesson.lessonResult.LessonResultFragment;

import butterknife.ButterKnife;

/**
 * @author Holden Easley <heasley@interapthq.com>
 * @version 1
 * @since 2/27/17
 */
public class LessonFragment extends BaseFragment implements LessonListFragment.Callback {

    private final static String LESSON_ID = "LESSON_ID";
    private final static String STUDENT_ID = "Student_ID";


    private LessonListFragment lessonListFragment;
    private LessonResultFragment lessonResultFragment;


    public static LessonFragment newInstance(Integer studentId) {
        Bundle args = new Bundle();
        args.putInt(STUDENT_ID, studentId);
        LessonFragment fragment = new LessonFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lesson, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);

        lessonListFragment = LessonListFragment.newInstance(getStudentId());

        getChildFragmentManager().beginTransaction()
            .add(R.id.fragment_container, lessonListFragment)
            .addToBackStack(null)
            .commit();
    }

    private Integer getStudentId(){
        return getArguments().getInt(STUDENT_ID);
    }

    @Override public void onLessonClicked(Integer lessonId, String lessonIdentifier) {
        // based on lesson
        lessonResultFragment = LessonResultFragment.newInstance(lessonId, getStudentId(), lessonIdentifier);
        getChildFragmentManager().beginTransaction()
            .replace(R.id.fragment_container, lessonResultFragment)
            .addToBackStack(null)
            .commit();
    }



}
