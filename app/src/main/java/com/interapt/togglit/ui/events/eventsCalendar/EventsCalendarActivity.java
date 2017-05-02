package com.interapt.togglit.ui.events.eventsCalendar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.interapt.togglit.R;
import com.interapt.togglit.common.Constants;
import com.interapt.togglit.common.SharedPreferencesManager;
import com.interapt.togglit.data.model.events.Events;
import com.interapt.togglit.ui.base.BaseActivity;
import com.interapt.togglit.ui.custom.CustomLayoutManager;
import com.interapt.togglit.ui.custom.views.CustomizableToolbar;
import com.interapt.togglit.ui.events.eventsDetail.EventDetailActivity;
import com.interapt.togglit.ui.filter.FilterActivity;
import com.interapt.togglit.util.StringUtil;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;


public class EventsCalendarActivity extends BaseActivity {

    @Inject
    EventsCalendarDetailAdapter mCalendarEventsDetailAdapter;

    @Inject
    SharedPreferencesManager mSharedPreferencesManager;

    @BindView(R.id.home_events__calendar_toolbar)
    CustomizableToolbar mToolbar;

    @BindView(R.id.tool_bar_title)
    TextView mTvToolbarTitle;

    @BindView(R.id.content_calendar_day_month_title)
    LinearLayout mContentDayMonthTitle;

    @BindView(R.id.tv_day_number)
    TextView mTvDayNumber;

    @BindView(R.id.tv_event_date)
    TextView mTvEventDate;

    @BindView(R.id.compact_calendar_view)
    CompactCalendarView mCompactCalendarView;

    @BindView(R.id.calendar_month_text_date)
    TextView mTvCalendarMonthDateBar;

    @BindView(R.id.tv_number_of_events)
    TextView mTvNumberOfEvents;

    @BindView(R.id.chevron_calendar_left)
    ImageView mImgChevronLeft;

    @BindView(R.id.chevron_calendar_right)
    ImageView mImgChevronRight;

    @BindView(R.id.recycler_view_calendar_events)
    RecyclerView mRecyclerViewCalendarEvents;

    @BindView(R.id.toolbar_ic_filter)
    ImageView mToolbarIcFilter;

    @BindView(R.id.sliding_layout)
    SlidingUpPanelLayout mSlidingUpPanelLayout;

    @Inject
    @Named("simpleDialog")
    MaterialDialog.Builder mBuilderDialog;


    private static final String LOG_TAG = EventsCalendarActivity.class.getSimpleName();
    private List<Events> mEventsList;
    List<Events> mSelectedCalendarEventsList;
    private List<Events> mListSelectedEvent;
    public ArrayList<Integer> filteredList = new ArrayList<>();


    private boolean isAllSelected = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_events_calendar);
        ButterKnife.bind(this);
        Intent intent2 = getIntent();
        if (intent2.getExtras() != null) {
            filteredList = intent2.getIntegerArrayListExtra("filteredList");
            if (filteredList == null) {
                filteredList = new ArrayList<>();
            }
        }
        initializeViews();
    }

    private void initializeViews() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.ic_arrow_left);
            upArrow.setColorFilter(ContextCompat.getColor(this, R.color.colorAccentWhite), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setTitle(null);
        }


        mListSelectedEvent = new ArrayList<>();


        mSelectedCalendarEventsList = new ArrayList<>();

        if (getIntent().getExtras() != null && !StringUtil.isNullOrEmpty(getIntent().getExtras().getString(Constants.LIST_EVENT_PARCELABLE_OBJECT))) {

            String eventsListSerializedToJson = getIntent().getExtras()
                    .getString(Constants.LIST_EVENT_PARCELABLE_OBJECT);

            mEventsList = new Gson().fromJson(eventsListSerializedToJson,
                    new TypeToken<List<Events>>() {
                    }.getType());

            if (mEventsList != null) {
                for (int j = 0; j < mEventsList.size(); j++) {
                    mEventsList.get(j).setChecked(false);
                    mEventsList.get(j).setCustom_color("#000000");

                }
            }
        }



        CustomLayoutManager mLayoutManager = new CustomLayoutManager(this);
        mRecyclerViewCalendarEvents.setLayoutManager(mLayoutManager);
        mRecyclerViewCalendarEvents.setHasFixedSize(false);
        mRecyclerViewCalendarEvents.setAdapter(mCalendarEventsDetailAdapter);

        mCompactCalendarView.setUseThreeLetterAbbreviation(true);
        mCompactCalendarView.displayOtherMonthDays(true);
//        mCompactCalendarView.

        mCalendarEventsDetailAdapter.setOnItemClickListener((view, position) -> {
            switch (view.getId()) {
                case R.id.content_main_event_item_layout:
                    Timber.d("Clicked Main Content_ Event %s ", "Position " + position);
                    if (mListSelectedEvent.size() > 0) {
                        mListSelectedEvent.clear();
                    }
                    mListSelectedEvent.addAll(mCalendarEventsDetailAdapter.getmListEvents());
                    showEventDetailActivity(mListSelectedEvent, position);
                    break;
                case R.id.img_calendar:
                    Timber.d("Clicked Icon calendar %s ", "Position " + position);
                    addEventToNativeCalendar(mCalendarEventsDetailAdapter.get(position));
                    break;
                case R.id.img_directions:
                    Timber.d("Clicked icon directions %s ", "Position " + position);
                    startLocationIntent(mCalendarEventsDetailAdapter.get(position));
                    break;
                case R.id.img_share:
                    Timber.d("Clicked icon share %s ", "Position " + position);
                    startShareIntent(mCalendarEventsDetailAdapter.get(position));
                    break;
                case R.id.img_info:
                    Timber.d("Clicked icon info %s ", "Position " + position);
                    openWebBrowser(mCalendarEventsDetailAdapter.get(position).getUrl());
                    break;
                default:
                    break;

            }
        });

        mToolbarIcFilter.setOnClickListener(v -> {
            showFilterActivity();
        });

        setupCalendarAndEvents(mEventsList);
        setCurrentDateToViews();


    }

    public void showEventDetailActivity(List<Events> selectedListEvents, int position) {
        Intent eventDetailIntent = new Intent(this, EventDetailActivity.class);
        String listEventsSerializedToJson = new Gson().toJson(selectedListEvents);
        eventDetailIntent.putExtra(Constants.SELECTED_EVENT, listEventsSerializedToJson);
        eventDetailIntent.putExtra(Constants.SELECTED_EVENT_POSITION, position);
        callActivity(eventDetailIntent);

    }

    private void startLocationIntent(Events ev) {
        String address = ev.getAddress();
        String city = ev.getCity();
        String state = ev.getState();
        String completeAddress = address + Constants.SYMBOL_COMMA
                + Constants.BLANK_SPACE + city + Constants.SYMBOL_COMMA
                + Constants.BLANK_SPACE + state;

        // get valid address location
        //GeoPoint result = getLocationFromAddress(completeAddress);

        //if (result != null) {
        //    showEventLocation(completeAddress);
        //} else {
        //    showInvalidAddressDialog();
        //}

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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.menu_calendar, menu);
//
//
//        final Drawable icMenuFilter = ContextCompat.getDrawable(this, R.drawable.ic_filter);
//        icMenuFilter.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
//
//        for (int i = 0; i < menu.size(); i++) {
//            MenuItem mi = menu.getItem(i);
//            mi.setIcon(icMenuFilter);
//
//        }
//
//        return true;
//    }

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
        String listFilterableEventsPageSerializedToJson = new Gson().toJson(mEventsList);
        filterIntent.putExtra(Constants.LIST_FILTER_EVENTS_PARCELABLE_OBJECT, listFilterableEventsPageSerializedToJson);
        filterIntent.putExtra(Constants.IS_ALL_EVENTS_SELECTED, isAllSelected);
        startActivityForResult(filterIntent, 1);
    }

    @OnClick(R.id.toolbar_ic_filter)
    public void onFilterOptionEvent() {
        FilterActivity.whereTOGo = 4;
        Intent intent = new Intent(this, FilterActivity.class);
        if (!filteredList.isEmpty()) {
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        }
        startActivity(intent);
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

                    mCompactCalendarView.removeAllEvents();

                    if (isAllSelected) {
                        for (int j = 0; j < mEventsList.size(); j++) {
                            mEventsList.get(j).setChecked(false);
                            mEventsList.get(j).setCustom_color("#000000");

                        }
                    } else {
                        for (int i = 0; i < mEventsList.size(); i++) {
                            mEventsList.get(i).setChecked(false);
                        }
                        for (int i = 0; i < mEventsList.size(); i++) {
                            for (int j = 0; j < mListFilterableEvents.size(); j++) {
                                if (mListFilterableEvents.get(j).getLabel().equalsIgnoreCase(mEventsList.get(i).getLabel())) {
                                    mEventsList.get(i).setChecked(true);
                                    mEventsList.get(i).setCustom_color("#1b3d6e");
                                }
                            }
                        }
                    }
                    setupCalendarAndEvents(mListFilterableEvents);
                }

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }


    public void setCurrentDateToViews() {
        int day = StringUtil.getCurrentDate().get(Calendar.DAY_OF_MONTH);
        String currentMonthName = StringUtil.getMonthForInt(StringUtil.getCurrentDate().get(Calendar.MONTH));
        int year = StringUtil.getCurrentDate().get(Calendar.YEAR);
        Timber.d("Current day %s ", day);

        String tempDay = String.valueOf(day).replaceFirst("^0+", "");
        mTvDayNumber.setText(tempDay);


        mTvEventDate.setText(currentMonthName + Constants.BLANK_SPACE + day + Constants.SYMBOL_COMMA + year);


        mTvCalendarMonthDateBar.setText(StringUtil.capitalizeString(currentMonthName) + Constants.BLANK_SPACE + year);

        Date date = new Date();
        List<Event> currentDayEventsList = mCompactCalendarView.getEvents(date);

        if (currentDayEventsList != null && currentDayEventsList.size() > 0) {
            mTvNumberOfEvents.setText(currentDayEventsList.size() == 1 ?
                    currentDayEventsList.size() + Constants.BLANK_SPACE
                            + "event" : currentDayEventsList.size() + Constants.BLANK_SPACE + "events");

            if (currentDayEventsList.size() > 0) {
                for (int i = 0; i < currentDayEventsList.size(); i++) {
                    mSelectedCalendarEventsList.add((Events) currentDayEventsList.get(i).getData());
                }
                mCalendarEventsDetailAdapter.populateCalendarEventsList(mSelectedCalendarEventsList);

            }
        } else {
            mSlidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
            mTvNumberOfEvents.setText("No events");
        }

    }

    public void setupCalendarAndEvents(List<Events> eventsList) {
        // Set first day of week to Sunday, defaults to Sunday so calling setFirstDayOfWeek is not necessary
        // Use constants provided by Java Calendar class
        mCompactCalendarView.setFirstDayOfWeek(Calendar.SUNDAY);


        for (int i = 0; i < eventsList.size(); i++) {
            mCompactCalendarView.addEvent(new Event(Color.parseColor("#00b2e2"),
                    StringUtil.getTimeInMillis(eventsList.get(i).getStartDate()), eventsList.get(i)), false);
        }

        // define a listener to receive callbacks when certain events happen.
        mCompactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                String onlyDayFormat = StringUtil.getOnlyDayFromDate(dateClicked, true);
                String monthAndYear = StringUtil.getOnlyMonthFromDate(dateClicked) + Constants.BLANK_SPACE + StringUtil.getOnlyDayFromDate(dateClicked, true)
                        + Constants.SYMBOL_COMMA + StringUtil.getOnlyYearFromDate(dateClicked);

                mTvDayNumber.setText(onlyDayFormat);
                mTvEventDate.setText(monthAndYear.toUpperCase());
                List<Event> events = mCompactCalendarView.getEvents(dateClicked);
                if (mSelectedCalendarEventsList.size() > 0) {
                    mSelectedCalendarEventsList.clear();
                }
                if (events.size() > 0) {
                    for (int i = 0; i < events.size(); i++) {
                        mSelectedCalendarEventsList.add((Events) events.get(i).getData());
                    }

                }


                if (mSelectedCalendarEventsList.size() > 0) {
                    mSlidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
                    mTvNumberOfEvents.setText(mSelectedCalendarEventsList.size() == 1 ?
                            mSelectedCalendarEventsList.size() + Constants.BLANK_SPACE
                                    + "event" : mSelectedCalendarEventsList.size() + Constants.BLANK_SPACE + "events");
                    mCalendarEventsDetailAdapter.populateCalendarEventsList(mSelectedCalendarEventsList);
                    mSlidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                    Timber.d(LOG_TAG + " %s ", "Day was clicked: " + dateClicked + " with events " + events);

                } else {
                    mTvNumberOfEvents.setText("No events");
                    mSlidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                String monthAndYear = StringUtil.getOnlyMonthFromDate(firstDayOfNewMonth)
                        + Constants.SYMBOL_COMMA + StringUtil.getOnlyYearFromDate(firstDayOfNewMonth);
                Timber.d(LOG_TAG + " %s ", "Month was scrolled to: " + monthAndYear);

                mTvCalendarMonthDateBar.setText(monthAndYear);

                if (mSlidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    mSlidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                }
            }
        });
    }


    @OnClick(R.id.chevron_calendar_left)
    public void onClickCalendarChevronLeft() {
        Timber.d(LOG_TAG + " %s ", "Click previous Month");
        mCompactCalendarView.showPreviousMonth();
    }

    @OnClick(R.id.chevron_calendar_right)
    public void onClickCalendarChevronRight() {
        Timber.d(LOG_TAG + " %s ", "Click next Month");
        mCompactCalendarView.showNextMonth();
    }
}
