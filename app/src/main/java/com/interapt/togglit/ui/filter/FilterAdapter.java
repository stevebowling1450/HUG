package com.interapt.togglit.ui.filter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.interapt.togglit.R;
import com.interapt.togglit.data.model.lists.Schools;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by stevebowling on 3/8/17.
 */

    class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.SchoolAdapterViewHolder> {

    private List<SchoolViewModel> mSchoolListItems;
    private Callback callback;

    void setCallback(Callback callback) {
        this.callback = callback;
    }

    boolean anySchoolChecked() {
        for (SchoolViewModel school : mSchoolListItems) {
            if (school.isChecked()) {
                return true;
            }
        }
        return false;
    }

    interface Callback {
        void onSchoolCheckClicked(SchoolViewModel schoolViewModel, boolean isChecked);
    }


    @Inject
    FilterAdapter() {
        mSchoolListItems = new ArrayList<>();
    }

    List<SchoolViewModel> getmSchoolListItems() {
        return new ArrayList<>(mSchoolListItems);
    }

    @Override
    public FilterAdapter.SchoolAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter_school, parent, false);
        return new FilterAdapter.SchoolAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SchoolAdapterViewHolder holder, int position) {
        holder.bindSchool(mSchoolListItems.get(position));

    }

    void unCheckAllSchools() {
        for (SchoolViewModel school :
                mSchoolListItems) {
            school.setChecked(false);
        }
    }

    void populateSchoolList(List<Schools> schoolList) {
        List<SchoolViewModel> list = new ArrayList<>();
        for (Schools schools : schoolList) {
            list.add(new SchoolViewModel(schools.getId(), schools.getSchoolName()));
        }
        mSchoolListItems = list;
    }

    @Override
    public int getItemCount() {
        return mSchoolListItems.size();
    }

    public SchoolViewModel get(int index) {
        return mSchoolListItems.get(index);
    }

//    public interface OnItemClickListener {
//        void onItemClick(View view, Schools school);
//    }

    class SchoolAdapterViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {

        @BindView(R.id.filter_school_name)
        TextView schoolItemName;

        @BindView(R.id.select_school_image)
        CheckBox selectedSchool;

        @BindView(R.id.filter_item_container)
        RelativeLayout schoolItemContainer;

        private SchoolViewModel school;


        SchoolAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindSchool(SchoolViewModel school) {
            this.school = school;
            schoolItemName.setText(school.getSchoolName());
            selectedSchool.setOnCheckedChangeListener(null);
            selectedSchool.setChecked(school.isChecked());
            if (school.isChecked()){
                schoolItemName.setTextColor(Color.rgb(20,162,219));
            }else {
                schoolItemName.setTextColor(Color.rgb(0,0,0));
            }
            selectedSchool.setOnCheckedChangeListener(this);
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            callback.onSchoolCheckClicked(school, isChecked);
        }
    }

    static class SchoolViewModel {
        private int schoolId;

        private String schoolName;

        private boolean isChecked;

        SchoolViewModel(int schoolId, String schoolName) {
            this.schoolId = schoolId;
            this.schoolName = schoolName;
            isChecked = false;
        }

        int getSchoolId() {
            return schoolId;
        }

        public String getSchoolName() {
            return schoolName;
        }

        boolean isChecked() {
            return isChecked;
        }

        void setChecked(boolean checked) {
            isChecked = checked;
        }
    }
}
