package com.interapt.togglit.ui.filter;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TextView;

import com.interapt.togglit.R;
import com.interapt.togglit.data.model.lists.Schools;
import com.interapt.togglit.ui.base.BaseActivity;
import com.interapt.togglit.ui.events.EventsActivity;
import com.interapt.togglit.ui.events.eventsCalendar.EventsCalendarActivity;
import com.interapt.togglit.ui.studentDirectory.StudentDirectoryActivity;
import com.interapt.togglit.ui.volunteerDirectory.VolunteerDirectoryActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FilterActivity extends BaseActivity implements FilterView, NavigationView.OnNavigationItemSelectedListener {

    @Inject
    FilterPresenter filterPresenter;

    FilterAdapter filterAdapter;

    @BindView(R.id.schools_recycler)
    RecyclerView schoolRecyclerView;

    @BindView(R.id.select_all_image)
    CheckBox allSelected;

    @BindView(R.id.header_name)
    TextView headerName;

    public static int whereTOGo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);
        filterAdapter = new FilterAdapter();
        schoolRecyclerView.setAdapter(filterAdapter);
        schoolRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        filterPresenter.attachView(this);
        filterPresenter.onCreate();
        allSelected.setChecked(true);
        headerName.setTextColor(getResources().getColor(R.color.azureBlue));
        allSelected.setClickable(false);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.color_control_normal_blue));
        }

        filterAdapter.setCallback((schoolViewModel, isChecked) -> {
            schoolViewModel.setChecked(isChecked);
            if (isChecked && allSelected.isChecked()) {
                allSelected.setChecked(false);

            } else if (!filterAdapter.anySchoolChecked() && !allSelected.isChecked()) {
                allSelected.setChecked(true);
            }
            allSelected.setClickable(filterAdapter.anySchoolChecked());
            filterAdapter.notifyDataSetChanged();
        });

        allSelected.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                allSelected.setClickable(false);
                filterAdapter.unCheckAllSchools();
                filterAdapter.notifyDataSetChanged();
                headerName.setTextColor(getResources().getColor(R.color.azureBlue));

            } else if (filterAdapter.getmSchoolListItems().isEmpty()) {
                allSelected.setChecked(true);
                headerName.setTextColor(getResources().getColor(R.color.azureBlue));
            } else if (!allSelected.isChecked()) {
                headerName.setTextColor(getResources().getColor(R.color.black_alpha_40));
            }
        });

    }

    @OnClick(R.id.cancel_text)
    public void cancel() {
        if (whereTOGo == 1) {
            Intent intent3 = new Intent(this, StudentDirectoryActivity.class);
            intent3.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent3);

        } else if (whereTOGo == 2) {
            Intent intent = new Intent(this, VolunteerDirectoryActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);

        } else if (whereTOGo == 3) {
            Intent intent = new Intent(this, EventsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);

        } else if (whereTOGo == 4) {
            Intent intent = new Intent(this, EventsCalendarActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        }
    }


    @OnClick(R.id.apply_text)
    public void applyFilter() {
        List<Integer> filters = new ArrayList<>();

        for (FilterAdapter.SchoolViewModel schoolViewModel : filterAdapter.getmSchoolListItems()) {
            if (allSelected.isChecked() || schoolViewModel.isChecked()) {
                filters.add(schoolViewModel.getSchoolId());
            }
        }

        if (whereTOGo == 1) {
            Intent intent3 = new Intent(this, StudentDirectoryActivity.class);
            intent3.putIntegerArrayListExtra("filteredList", new ArrayList<>(filters));
            startActivity(intent3);

        } else if (whereTOGo == 2) {
            Intent intent = new Intent(this, VolunteerDirectoryActivity.class);
            intent.putIntegerArrayListExtra("filteredList", new ArrayList<>(filters));
            startActivity(intent);

        } else if (whereTOGo == 3) {
            Intent intent = new Intent(this, EventsActivity.class);
            intent.putIntegerArrayListExtra("filteredList", new ArrayList<>(filters));
            startActivity(intent);

        } else if (whereTOGo == 4) {
            Intent intent = new Intent(this, EventsCalendarActivity.class);
            intent.putIntegerArrayListExtra("filteredList", new ArrayList<>(filters));
            startActivity(intent);
        }
    }


    @Override
    public void getSchoolList(List<Schools> schoolList) {
        filterAdapter.populateSchoolList(schoolList);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return filterAdapter.getItemCount();
            }

            @Override
            public int getNewListSize() {
                return schoolList.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return false;
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return false;
            }
        });

        result.dispatchUpdatesTo(filterAdapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onBackPressed() {
        if (whereTOGo == 1) {
            Intent intent3 = new Intent(this, StudentDirectoryActivity.class);
            intent3.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent3);

        } else if (whereTOGo == 2) {
            Intent intent = new Intent(this, VolunteerDirectoryActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);

        } else if (whereTOGo == 3) {
            Intent intent = new Intent(this, EventsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);

        } else if (whereTOGo == 4) {
            Intent intent = new Intent(this, EventsCalendarActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        }
    }
}







