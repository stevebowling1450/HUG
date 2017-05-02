package com.interapt.togglit.ui.lessonList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.interapt.togglit.R;
import com.interapt.togglit.data.model.lists.LessonsNoImage;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by Matthew.Watson on 2/16/17.
 */

public class LessonListAdapter extends RecyclerView.Adapter<LessonListAdapter.LessonListAdapterViewHolder> {

    private Context mContext;
    private List<LessonsNoImage> mLessonListItems;
    private LessonListAdapter.OnItemClickListener mItemClickListener;


    @Inject
    public LessonListAdapter(Context context) {
        this.mContext = context;
        mLessonListItems = new ArrayList<>();
    }

    public void setOnItemClickListener(final LessonListAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, LessonsNoImage lesson);
    }

    @Override
    public LessonListAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lesson_list, parent, false);

        return new LessonListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LessonListAdapterViewHolder holder, int position) {
        LessonsNoImage lesson = mLessonListItems.get(position);
        holder.setLessonText(lesson);
    }


    @Override
    public int getItemCount() {
        return mLessonListItems.size();
    }

    public LessonsNoImage get(int index) {
        return mLessonListItems.get(index);
    }


    public void populateLessonList(List<LessonsNoImage> lessonList) {
        mLessonListItems = lessonList;
        notifyDataSetChanged();
    }


    public class LessonListAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        @BindView(R.id.lesson_list_item)
        RelativeLayout lessonListItem;

        @BindView(R.id.lesson_tv)
        TextView lessonTitleTV;

        private LessonsNoImage lesson;


        public LessonListAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            lessonListItem.setOnClickListener(this);
        }

        public void setLessonText(LessonsNoImage lesson) {
            this.lesson = lesson;
            String lessonText = "Lesson " + lesson.getLessonIdentifier() +": "+lesson.getLessonName();
            lessonTitleTV.setText(lessonText);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, lesson);
                Timber.d("CLICK");
            }

        }
    }
}


