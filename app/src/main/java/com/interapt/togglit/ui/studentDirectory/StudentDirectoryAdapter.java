package com.interapt.togglit.ui.studentDirectory;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.interapt.togglit.R;
import com.interapt.togglit.data.model.lists.Students;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.myinnos.alphabetsindexfastscrollrecycler.utilities_fs.StringMatcher;

/**
 * Created by Matthew.Watson on 12/15/16.
 */

public class StudentDirectoryAdapter extends RecyclerView.Adapter<StudentDirectoryAdapter.StudentAdapterViewHolder> implements SectionIndexer {
    private final Context mContext;
    private List<Students> mStudentListItem;
    private StudentDirectoryAdapter.OnItemClickListener mItemClickListener;
    private String mSections = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private int lastPosition = -1;

    @Inject
    public StudentDirectoryAdapter(Context context) {
        this.mContext = context;
        mStudentListItem = new ArrayList<>();
    }

    @Override
    public StudentAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new StudentAdapterViewHolder(view);
    }


    @Override
    public void onBindViewHolder(StudentAdapterViewHolder holder, int position) {
        Students student = mStudentListItem.get(position);
        holder.bindUser(mStudentListItem.get(position));


        holder.name.setText(student.getFirstName() + " " + student.getLastName());
        holder.title.setText(student.getSchoolName());

        if (holder.student.getImage().length() > 50) {
            holder.studentInitial.setVisibility(View.GONE);
            holder.setItemImage(student.getImage());
        } else {
            holder.studentInitial.setText(getInitals(student.getFirstName(), student.getLastName()));
        }
    }

    private String getInitals(String string, String string2) {
        String firstI = String.valueOf(string.charAt(0)).toUpperCase();
        String lastI = String.valueOf(string2.charAt(0)).toUpperCase();
        return firstI + lastI;
    }

    @Override
    public void onViewDetachedFromWindow(StudentAdapterViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.studentItem.clearAnimation();
    }

    public void populateStudentList(List<Students> students) {
        mStudentListItem = students;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mStudentListItem.size();
    }

    public Students get(int index) {
        return mStudentListItem.get(index);
    }

    @Override
    public int getPositionForSection(int section) {
        // If there is no item for current section, previous section will be selected
        for (int i = section; i >= 0; i--) {
            for (int j = 0; j < getItemCount(); j++) {
                if (i == 0) {
                    // For numeric section
                    for (int k = 0; k <= 9; k++) {


                        if (StringMatcher.match(String.valueOf(get(j).getLastName().charAt(0)), String.valueOf(k)))
                            return j;
                    }
                } else {
                    if (StringMatcher.match(String.valueOf(mStudentListItem.get(j).getLastName().charAt(0)), String.valueOf(mSections.charAt(i))))
                        return j;
                }
            }
        }
        return 0;
    }

    public void setOnItemClickListener(final StudentDirectoryAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Students student);
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    @Override
    public Object[] getSections() {
        String[] sections = new String[mSections.length()];
        for (int i = 0; i < mSections.length(); i++)
            sections[i] = String.valueOf(mSections.charAt(i));
        return sections;
    }

    class StudentAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener { //populates view

        @BindView(R.id.student_name)
        TextView name;

        @BindView(R.id.school_name)
        TextView title;

        @BindView(R.id.student_imageview)
        ImageView studentImg;

        @BindView(R.id.student_initial)
        TextView studentInitial;


        @BindView(R.id.student_item_container)
        RelativeLayout studentItem;

        private Students student;


        private StudentAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            studentItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, student);
            }
        }


        private void bindUser(Students student) {
            this.student = student;
        }


        void setItemImage(String bitImage) {

            byte[] imageByteArray = Base64.decode(bitImage, Base64.DEFAULT);

            Glide.with(mContext).load(imageByteArray).asBitmap().centerCrop().into(new BitmapImageViewTarget(studentImg) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    studentImg.setImageDrawable(circularBitmapDrawable);
                }
            });
        }
    }
}