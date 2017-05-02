package com.interapt.togglit.ui.progressReport.lesson.lessonResult;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.interapt.togglit.R;
import com.interapt.togglit.common.Constants;
import com.interapt.togglit.data.model.session.Session;
import com.interapt.togglit.ui.progressReport.lesson.BaseFragment;

import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Matthew.Watson on 2/22/17.
 */

public class LessonResultFragment extends BaseFragment implements LessonResultFragmentView {

    @Inject
    LessonResultFragmentPresenter mLessonResultFragmentPresenter;

    private final static String LESSON_ID = "LESSON_ID";
    private final static String STUDENT_ID = "StudentId";
    private final static String LESSON_IDENT = "LESSON_IDent";

    @BindView(R.id.lesson_result_lesson_number)
    TextView lessonNumber;

    @BindView(R.id.first_attempt_date_display)
    TextView firstAttemptDate;

    @BindView(R.id.second_attempt_date_display)
    TextView secondAttemptDate;

    @BindView(R.id.first_attempt_fluency_display)
    TextView firstAttemptFluency;

    @BindView(R.id.second_attempt_fluency_display)
    TextView secondAttemptFluency;

    @BindView(R.id.first_attempt_accumen_display)
    TextView firstAttemptAccumen;

    @BindView(R.id.second_attempt_accumen_display)
    TextView secondAttemptAccumen;

    @BindView(R.id.first_attempt_mood_display)
    ImageView firstAttemptMood;

    @BindView(R.id.second_attempt_mood_display)
    ImageView secondAttemptMood;


    public static LessonResultFragment newInstance(Integer lessonId, Integer studentId, String lessonIdentifier) {
        Bundle args = new Bundle();
        args.putString(LESSON_IDENT, lessonIdentifier);
        args.putInt(LESSON_ID, lessonId);
        args.putInt(STUDENT_ID, studentId);
        LessonResultFragment fragment = new LessonResultFragment();
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
        mLessonResultFragmentPresenter.attachView(this);
        mLessonResultFragmentPresenter
                .doActionGetLessonsForFragment(getLesson(), getStudentId());

    }

    private Integer getStudentId() {
        return getArguments().getInt(STUDENT_ID);
    }

    private String getLessonIdent() {
        return getArguments().getString(LESSON_IDENT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lesson_results, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private Integer getLesson() {
        return getArguments().getInt(LESSON_ID);
    }


    @Override
    public void setFragmentLesson(List<Session> sessions) {

        lessonNumber.setText("Lesson " + getLessonIdent());

        firstAttemptDate.setText(formatDate(sessions.get(0).getDate()));
        firstAttemptFluency.setText(formatTime(sessions.get(0).getTime()).toString());
        firstAttemptAccumen.setText((100 - sessions.get(0).getAcumen()) + "%");
        setMood(firstAttemptMood, sessions.get(0).getStudentMood());

        secondAttemptDate.setText(formatDate(sessions.get(1).getDate()));
        secondAttemptFluency.setText(formatTime(sessions.get(1).getTime()).toString());
        secondAttemptAccumen.setText((100 - sessions.get(1).getAcumen()) + "%");
        setMood(secondAttemptMood, sessions.get(1).getStudentMood());
    }


    private void setMood(ImageView imageView, Integer mood) {
        switch (mood) {
            case 1:
                imageView.setImageResource(R.drawable.bad_frown);
                break;
            case 2:
                imageView.setImageResource(R.drawable.frown);
                break;
            case 3:
                imageView.setImageResource(R.drawable.neutral);
                break;
            case 4:
                imageView.setImageResource(R.drawable.happy);
                break;
            case 5:
                imageView.setImageResource(R.drawable.very_happy);
                break;
        }
    }

    private static Formatter formatTime(int seconds) {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb, Locale.US);
        formatter.format("%02d:%02d", TimeUnit.SECONDS.toMinutes(seconds),
                TimeUnit.SECONDS.toSeconds(seconds) % 60);
        return formatter;
    }

    @Override
    public void showError(String message, @Constants.ErrorType int errorType) {

    }

    @Override
    public void showRefresh(boolean show) {

    }


}
