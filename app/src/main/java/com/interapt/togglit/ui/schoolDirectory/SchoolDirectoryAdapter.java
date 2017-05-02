package com.interapt.togglit.ui.schoolDirectory;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.interapt.togglit.R;
import com.interapt.togglit.data.model.lists.Schools;
import com.interapt.togglit.data.model.user.School;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.myinnos.alphabetsindexfastscrollrecycler.utilities_fs.StringMatcher;

/**
 * Created by nicholashall on 2/22/17.
 */

public class SchoolDirectoryAdapter extends RecyclerView.Adapter<SchoolDirectoryAdapter.SchoolAdapterViewHolder> implements SectionIndexer {


    SchoolDirectoryAdapter.OnItemClickListener mItemClickListener;
    private String mSections = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private Context mContext;
    private List<Schools> mSchoolListItems;
    // Allows to remember the last item shown on screen
    private int lastPosition = -1;

    @Inject
    public SchoolDirectoryAdapter(Context context) {
        this.mContext = context;
        mSchoolListItems = new ArrayList<>();

    }

    public void setCallback(Callback callback) {
        Callback callback1 = callback;
    }

    @Override
    public SchoolDirectoryAdapter.SchoolAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_school, parent, false);
        return new SchoolDirectoryAdapter.SchoolAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SchoolDirectoryAdapter.SchoolAdapterViewHolder holder, int position) {
        Schools school = mSchoolListItems.get(position);
        holder.bindSchool(mSchoolListItems.get(position));
        holder.schoolItemName.setText(school.getSchoolName());
        holder.schoolItemPerson.setText(school.getPrincipalName());

    }

    @Override
    public void onViewDetachedFromWindow(SchoolDirectoryAdapter.SchoolAdapterViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.schoolItemContainer.clearAnimation();
    }

    public void populateSchoolList(List<Schools> schoolList) {
        mSchoolListItems = schoolList;
        notifyDataSetChanged();
    }

    public String getInitals(String string, String string2) {

        String firstI = String.valueOf(string.charAt(0));
        String lastI = String.valueOf(string2.charAt(0));
        String initals = firstI + lastI;
        return initals;
    }

    @Override
    public int getItemCount() {
        return mSchoolListItems.size();
    }

    public Schools get(int index) {
        return mSchoolListItems.get(index);
    }

    @Override
    public Object[] getSections() {
        String[] sections = new String[mSections.length()];
        for (int i = 0; i < mSections.length(); i++)
            sections[i] = String.valueOf(mSections.charAt(i));
        return sections;
    }

    @Override
    public int getPositionForSection(int section) {
        // If there is no item for current section, previous section will be selected
        for (int i = section; i >= 0; i--) {
            for (int j = 0; j < getItemCount(); j++) {
                if (i == 0) {
                    // For numeric section
                    for (int k = 0; k <= 9; k++) {
                        if (StringMatcher.match(String.valueOf(mSchoolListItems.get(j).getSchoolName().charAt(0)), String.valueOf(k)))
                            return j;
                    }
                } else {
                    if (StringMatcher.match(String.valueOf(mSchoolListItems.get(j).getSchoolName().charAt(0)), String.valueOf(mSections.charAt(i))))
                        return j;
                }
            }
        }
        return 0;
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    public void setOnItemClickListener(final SchoolDirectoryAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


    public interface Callback {
        void onSchoolClick(School school);


    }


    public interface OnItemClickListener {
        void onItemClick(View view, Schools school);
    }

    public class SchoolAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.school_name)
        TextView schoolItemName;

        @BindView(R.id.school_person)
        TextView schoolItemPerson;

        @BindView(R.id.item_school_container)
        RelativeLayout schoolItemContainer;

        private Schools school;


        public SchoolAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            schoolItemContainer.setOnClickListener(this);
        }

        public void setItemImage(String urlImage) {
            Glide.with(mContext).load(urlImage).diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .placeholder(R.drawable.img_pod_place_holder).centerCrop().into(6, 6);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, school);
            }
        }

        public void bindSchool(Schools school) {
            this.school = school;
            schoolItemName.setText(school.getSchoolName());
            schoolItemPerson.setText(school.getPrincipalName());

        }
    }


}
