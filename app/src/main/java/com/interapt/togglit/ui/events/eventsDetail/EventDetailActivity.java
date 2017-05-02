package com.interapt.togglit.ui.events.eventsDetail;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.interapt.togglit.R;
import com.interapt.togglit.common.Constants;
import com.interapt.togglit.common.SharedPreferencesManager;
import com.interapt.togglit.data.model.events.Events;
import com.interapt.togglit.ui.base.BaseActivity;
import com.interapt.togglit.util.StringUtil;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class EventDetailActivity extends BaseActivity implements EventDetailView {

    @Inject
    EventDetailPresenter mEventDetailPresenter;

    @Inject
    EventDetailPagerAdapter mEventDetailPagerAdapter;

    @Inject
    SharedPreferencesManager mSharedPreferencesManager;

    @BindView(R.id.home_event_detail_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.tool_bar_title)
    TextView mTvToolbarTitle;

    @BindView(R.id.pager_event_detail)
    ViewPager mViewPager;

    private List<Events> mSelectedEventsList;
    private int mSelectedEventPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_event_detail);
        ButterKnife.bind(this);
        initializeViews();
    }

    private void initializeViews() {
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.ic_arrow_left);
            upArrow.setColorFilter(ContextCompat.getColor(this, R.color.colorAccentWhite), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(null);
        }

        mEventDetailPresenter.attachView(this);


        if (getIntent().getExtras() != null) {

            if (!StringUtil.isNullOrEmpty(getIntent().getExtras().getString(Constants.SELECTED_EVENT))) {
                String eventsListSerializedToJson = getIntent().getExtras()
                        .getString(Constants.SELECTED_EVENT);

                mSelectedEventsList = new Gson().fromJson(eventsListSerializedToJson,
                        new TypeToken<List<Events>>() {
                        }.getType());
            }

            mSelectedEventPosition = getIntent().getExtras()
                    .getInt(Constants.SELECTED_EVENT_POSITION);


        }
        mViewPager.setAdapter(mEventDetailPagerAdapter);


        mEventDetailPagerAdapter.setOnItemClickListener(view -> {
            switch (view.getId()) {
                case R.id.chevron_event_detail_left:
                    // select the previous event.
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
                    break;
                case R.id.chevron_event_detail_right:
                    // select the next event.
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                    break;
                case R.id.img_calendar:
                    Timber.d("Clicked Icon calendar %s ", "Position " + mViewPager.getCurrentItem());
                    addEventToNativeCalendar(mEventDetailPagerAdapter.get(mViewPager.getCurrentItem()));
                    break;
                case R.id.img_directions:
                    Timber.d("Clicked icon directions %s ", "Position " + mViewPager.getCurrentItem());
                    startLocationIntent(mEventDetailPagerAdapter.get(mViewPager.getCurrentItem()));
                    break;
                case R.id.img_share:
                    Timber.d("Clicked icon share %s ", "Position " + mViewPager.getCurrentItem());
                    startShareIntent(mEventDetailPagerAdapter.get(mViewPager.getCurrentItem()));
                    break;
                case R.id.img_info:
                    Timber.d("Clicked icon info %s ", "Position " + mViewPager.getCurrentItem());
                    openWebBrowser(mEventDetailPagerAdapter.get(mViewPager.getCurrentItem()).getUrl());
                    break;
                default:
                    break;
            }
        });
        mEventDetailPagerAdapter.populatePagerList(mSelectedEventsList);
        mViewPager.setCurrentItem(mSelectedEventPosition);

    }

    @Override
    public void getEvent(List<Events> events) {
        mSelectedEventsList = events;
    }

    private void startLocationIntent(Events ev) {
        String address = ev.getAddress();
        String city = ev.getCity();
        String state = ev.getState();
        String completeAddress = address + Constants.SYMBOL_COMMA
                + Constants.BLANK_SPACE + city + Constants.SYMBOL_COMMA
                + Constants.BLANK_SPACE + state;

        showEventLocation(completeAddress);
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
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mEventDetailPresenter.detachView();
    }

    @Override
    public void showError(String message, @Constants.ErrorType int errorType) {

    }

    @Override
    public void showRefresh(boolean show) {

    }



}