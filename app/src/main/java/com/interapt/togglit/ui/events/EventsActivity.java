package com.interapt.togglit.ui.events;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.maps.GeoPoint;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.interapt.togglit.R;
import com.interapt.togglit.common.Constants;
import com.interapt.togglit.common.SharedPreferencesManager;
import com.interapt.togglit.data.model.events.Events;
import com.interapt.togglit.ui.base.BaseActivity;
import com.interapt.togglit.ui.custom.views.CustomizableToolbar;
import com.interapt.togglit.ui.events.eventsCalendar.EventsCalendarActivity;
import com.interapt.togglit.ui.events.eventsDetail.EventDetailActivity;
import com.interapt.togglit.ui.filter.FilterActivity;
import com.interapt.togglit.ui.homeScreenPods.HomeScreenPodsActivity;
import com.interapt.togglit.util.StringUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class EventsActivity extends BaseActivity implements EventsView {

    @Inject
    EventsPresenter mEventsPresenter;

    @Inject
    SharedPreferencesManager mSharedPreferencesManager;

    @Inject
    EventsAdapter mEventsAdapter;

    @BindView(R.id.home_events_toolbar)
    CustomizableToolbar mToolbar;

    @BindView(R.id.tool_bar_title)
    TextView mTvToolbarTitle;

    @BindView(R.id.loading_avl_panel_progress_container)
    RelativeLayout mAVLoading;

    @BindView(R.id.toolbar_ic_filter)
    ImageView mIcToolbarFilter;

    @BindView(R.id.avi)
    AVLoadingIndicatorView mAviProgress;

    @BindView(R.id.recycler_view_home_events)
    RecyclerView mRecyclerViewEvents;

    @Inject
    @Named("simpleDialog")
    MaterialDialog.Builder mBuilderDialog;


    public List<Events> mListEvents;
    private List<Events> mListSelectedEvent;
    private boolean isAllSelected = true;
    public ArrayList<Integer> filteredList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_events);
        Intent intent2 = getIntent();
        if (intent2.getExtras() != null) {
            filteredList = intent2.getIntegerArrayListExtra("filteredList");
            if (filteredList == null) {
                filteredList = new ArrayList<>();
            }
        }
        ButterKnife.bind(this);
        initializeViews();
    }

    public void initializeViews() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.ic_arrow_left);
            upArrow.setColorFilter(ContextCompat.getColor(this, R.color.colorAccentWhite), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(null);
        }

        mEventsPresenter.attachView(this);

        mListEvents = new ArrayList<>();
        mListSelectedEvent = new ArrayList<>();

        mRecyclerViewEvents.setHasFixedSize(true);
        mRecyclerViewEvents.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewEvents.setAdapter(mEventsAdapter);


        mEventsPresenter.onCreate(filteredList);


        mEventsAdapter.setOnItemClickListener((view, position) -> {
            switch (view.getId()) {
                case R.id.content_main_event_item_layout:
                    Timber.d("Clicked Main Content Event %s ", "Position " + position);
                    if (mListSelectedEvent.size() > 0) {
                        mListSelectedEvent.clear();
                    }
                    mListSelectedEvent.addAll(mEventsAdapter.getmListEvents());
                    showEventDetailActivity(mListSelectedEvent, position);
                    break;
                case R.id.img_calendar:
                    Timber.d("Clicked Icon calendar %s ", "Position " + position);
                    addEventToNativeCalendar(mEventsAdapter.get(position));
                    break;
                case R.id.img_directions:
                    Timber.d("Clicked icon directions %s ", "Position " + position);
                    startLocationIntent(mEventsAdapter.get(position));
                    break;
                case R.id.img_share:
                    Timber.d("Clicked icon share %s ", "Position " + position);
                    startShareIntent(mEventsAdapter.get(position));
                    break;
                case R.id.img_info:
                    Timber.d("Clicked icon info %s ", "Position " + position);
                    openWebBrowser(mEventsAdapter.get(position).getUrl());
                    break;
                default:
                    break;

            }

        });
    }


    @OnClick(R.id.toolbar_ic_calendar)
    public void onCalendarOptionEvent() {
        Timber.d("Calendar Events Click event");
        showCalendarEventsActivity();
    }

    @OnClick(R.id.toolbar_ic_filter)
    public void onFilterOptionEvent() {
        FilterActivity.whereTOGo = 3;
        Intent intent = new Intent(this, FilterActivity.class);
        if (!filteredList.isEmpty()) {
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        }
        startActivity(intent);
    }

    private void startLocationIntent(Events ev) {
        String address = ev.getAddress();
        String city = ev.getCity();
        String state = ev.getState();
        String zip_code = ev.getZip().toString();
        String completeAddress = address + Constants.SYMBOL_COMMA
                + Constants.BLANK_SPACE + city + Constants.SYMBOL_COMMA
                + Constants.BLANK_SPACE + state;

        // get valid address location
        GeoPoint result = getLocationFromAddress(completeAddress);
        if (result != null) {
            showEventLocation(completeAddress);
        } else {
            showInvalidAddressDialog();
        }

    }

    public void showInvalidAddressDialog() {
        mBuilderDialog.content(R.string.dialog_invalid_address_message)
                .positiveText(R.string.dialog_invalid_address_btn_text)
                .build().show();

    }

    private void startShareIntent(Events ev) {
        initShareIntent(ev);
    }

    private void addEventToNativeCalendar(Events ev) {
        addEventToCalendar(ev);
    }

    public void openWebBrowser(final String eventUrl) {
        if (!StringUtil.isNullOrEmpty(eventUrl)) {
            Intent webBrowserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(eventUrl));
            callActivity(webBrowserIntent);
        } else {
            Toast.makeText(this, R.string.message_no_info_content, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            default:
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    public void showFilterActivity() {
        Intent filterIntent = new Intent(this, FilterActivity.class);
        String listFilterableEventsPageSerializedToJson = new Gson().toJson(mListEvents);
        filterIntent.putExtra(Constants.LIST_FILTER_EVENTS_PARCELABLE_OBJECT, listFilterableEventsPageSerializedToJson);
        filterIntent.putExtra(Constants.IS_ALL_EVENTS_SELECTED, isAllSelected);
        startActivityForResult(filterIntent, 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                if (data.getExtras() != null && !StringUtil.isNullOrEmpty(data.getExtras().getString(Constants.LIST_FILTERABLE_EVENTS_PARCELABLE_OBJECT))) {

                    String listFilterableSerializedToJson = data.getExtras()
                            .getString(Constants.LIST_FILTERABLE_EVENTS_PARCELABLE_OBJECT);

                    List<Events> mListFilterableEvents = new Gson().fromJson(listFilterableSerializedToJson,
                            new TypeToken<List<Events>>() {
                            }.getType());

                    isAllSelected = data.getExtras().getBoolean(Constants.IS_ALL_EVENTS_SELECTED);

                    if (isAllSelected) {
                        for (int j = 0; j < mListEvents.size(); j++) {
                            mListEvents.get(j).setChecked(false);

                        }
                    } else {
                        for (int i = 0; i < mListEvents.size(); i++) {
                            mListEvents.get(i).setChecked(false);
                        }
                        for (int i = 0; i < mListEvents.size(); i++) {
                            if (mListFilterableEvents != null) {
                                for (int j = 0; j < mListFilterableEvents.size(); j++) {
                                    if (mListFilterableEvents.get(j).getLabel().equalsIgnoreCase(mListEvents.get(i).getLabel())) {
                                        mListEvents.get(i).setChecked(true);
                                    }
                                }
                            }
                        }
                    }

                    mEventsAdapter.populateEventsList(mListFilterableEvents);


                }

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //TODO Cancel logic
            }
        }
    }

    public void showCalendarEventsActivity() {
        Intent calendarIntent = new Intent(this, EventsCalendarActivity.class);
        String listEventsSerializedToJson = new Gson().toJson(mListEvents);
        calendarIntent.putExtra(Constants.LIST_EVENT_PARCELABLE_OBJECT, listEventsSerializedToJson);
        startActivity(calendarIntent);
    }

    public void showEventDetailActivity(List<Events> selectedListEvents, int position) {
        Intent eventDetailIntent = new Intent(this, EventDetailActivity.class);
        String listEventsSerializedToJson = new Gson().toJson(selectedListEvents);
        eventDetailIntent.putExtra(Constants.SELECTED_EVENT, listEventsSerializedToJson);
        eventDetailIntent.putExtra(Constants.SELECTED_EVENT_POSITION, position);
        callActivity(eventDetailIntent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mEventsPresenter.detachView();
    }

    @Override
    public void showError(String message, @Constants.ErrorType int errorType) {
        switch (errorType) {
            case Constants.LOW_ERROR:
                super.showError(message, null, errorType);
                break;
        }
    }

    @Override
    public void showRefresh(boolean show) {
        mAVLoading.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, HomeScreenPodsActivity.class);
        startActivity(intent);

    }

    @Override
    public void getEventsList(List<Events> eventsList) {
        mListEvents = eventsList;
        int counter = 0;
        for (int i = 0; i < mListEvents.size(); i++) {
            if (StringUtil.isNullOrEmpty(mListEvents.get(i).getLabel())) {
                counter = counter + 1;
            }
        }
        if (counter == mListEvents.size()) {
            mIcToolbarFilter.setVisibility(View.GONE);
        } else {
            mIcToolbarFilter.setVisibility(View.VISIBLE);
        }
        mEventsAdapter.populateEventsList(eventsList);
    }


}