package com.interapt.togglit.ui.progressReport.acumen;

import android.content.Context;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.interapt.togglit.R;
import com.interapt.togglit.data.model.session.StudentLessonAcumen;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.*;

/**
 * Created by nicholashall on 3/3/17.
 */

public class AcumenLessonListAdapter extends RecyclerView.Adapter<AcumenLessonListAdapter.AcumenListAdapterViewHolder> {

    private Context mContext;
    private List<StudentLessonAcumen> mSessionList;


    @Inject
    public AcumenLessonListAdapter(Context context) {
        this.mContext = context;
        mSessionList = new ArrayList<>();
    }


    @Override
    public AcumenListAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.session_item, parent, false);

        return new AcumenListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AcumenListAdapterViewHolder holder, int position) {
        StudentLessonAcumen sessionFake = mSessionList.get(position);
        holder.setLessonText(sessionFake);
    }

    @Override
    public int getItemCount() {
        return mSessionList.size();
    }

    public StudentLessonAcumen get(int index) {
        return mSessionList.get(index);
    }


    public void populateSessionList(List<StudentLessonAcumen> sessionFakeList) {
        mSessionList = sessionFakeList;
        notifyDataSetChanged();
    }


    class AcumenListAdapterViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.session_list_item)
        LinearLayout sessionListItem;

        @BindView(R.id.students_lesson)
        TextView studentsLesson;

        @BindView(R.id.attempt_one)
        ImageView attemptOne;

        @BindView(R.id.attempt_one_text)
        TextView attemptOneText;

        @BindView(R.id.attempt_two)
        ImageView attemptTwo;

        @BindView(R.id.attempt_two_text)
        TextView attemptTwoText;


        private StudentLessonAcumen sessions;


        AcumenListAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void setLessonText(StudentLessonAcumen sessions) {
            this.sessions = sessions;
            String lessonText = String.format("LESSON %s", sessions.getLessonIdentifier());
            studentsLesson.setText(lessonText);

            PercentRelativeLayout.LayoutParams layoutParams = (PercentRelativeLayout.LayoutParams) attemptOne.getLayoutParams();
            layoutParams.getPercentLayoutInfo().widthPercent = ((float) turnAcumen(sessions.getAttemptOne()) / 100f) * 0.8f;
            attemptOne.setLayoutParams(layoutParams);

            if (!sessions.getDate2().equals("") && sessions.getMood2()!=0 && sessions.getFluency2() !=0){
                PercentRelativeLayout.LayoutParams layoutParams1 = (PercentRelativeLayout.LayoutParams) attemptTwo.getLayoutParams();
                layoutParams1.getPercentLayoutInfo().widthPercent = ((float) turnAcumen(sessions.getAttemptTwo()) / 100f) * 0.8f;
                attemptTwo.setLayoutParams(layoutParams1);
            } else {
                attemptTwo.setVisibility(GONE);
                attemptTwoText.setVisibility(GONE);
            }


            attemptOneText.setText(String.format("%s%%", turnAcumen(sessions.getAttemptOne()).toString()));
            attemptTwoText.setText(String.format("%s%%", turnAcumen(sessions.getAttemptTwo()).toString()));

        }


        Integer turnAcumen(Integer integer) {
            int i = 100;
            i = i - integer;
            return i;
        }

    }
}
