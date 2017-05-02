package com.interapt.togglit.ui.progressReport.overview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.interapt.togglit.R;
import com.interapt.togglit.common.Constants;
import com.interapt.togglit.data.model.Average;
import com.interapt.togglit.ui.progressReport.lesson.BaseFragment;

import java.util.Formatter;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Matthew.Watson on 2/22/17.
 */

public class OverviewFragment extends BaseFragment implements OverviewFragmentView {

    @Inject
    OverviewFragmentPresenter mOverviewFragmentPresenter;


    @BindView(R.id.first_attempt_fluency_display_average)
    TextView firstAttemptFluency;

    @BindView(R.id.second_attempt_fluency_display_average)
    TextView secondAttemptFluency;

    @BindView(R.id.first_attempt_accumen_display_average)
    TextView firstAttemptAccumen;

    @BindView(R.id.second_attempt_accumen_display_average)
    TextView secondAttemptAccumen;

    @BindView(R.id.first_attempt_mood_display_average)
    ImageView firstAttemptMood;

    @BindView(R.id.second_attempt_mood_display_average)
    ImageView secondAttemptMood;

    private final static String STUDENT_ID = "StudentId";


    public static OverviewFragment newInstance(Integer studentId) {

        Bundle args = new Bundle();
        args.putInt(STUDENT_ID, studentId);
        OverviewFragment fragment = new OverviewFragment();
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

        mOverviewFragmentPresenter.attachView(this);
        mOverviewFragmentPresenter.onCreate(getStudentId());
    }

    private Integer getStudentId() {
        return getArguments().getInt(STUDENT_ID);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FrameLayout lessonLayout =
                (FrameLayout) inflater.inflate(R.layout.fragment_overview, container,
                        false);
        ButterKnife.bind(this, lessonLayout);
        return lessonLayout;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public void showError(String message, @Constants.ErrorType int errorType) {

    }

    @Override
    public void showRefresh(boolean show) {

    }

    @Override
    public void setAverage(Average averages) {

        firstAttemptFluency.setText(formatTime(averages.getTimeOne()).toString());
        firstAttemptAccumen.setText(100 - averages.getAcumenOne() + "%");
        setMood(firstAttemptMood, averages.getMoodOne());

        secondAttemptFluency.setText(formatTime(averages.getTimeTwo()).toString());
        secondAttemptAccumen.setText(100 - averages.getAcumenTwo() + "%");
        setMood(secondAttemptMood, averages.getMoodTwo());
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
}
