package com.interapt.togglit.ui.base;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.maps.GeoPoint;
import com.interapt.togglit.InteraptApplication;
import com.interapt.togglit.R;
import com.interapt.togglit.common.Constants;
import com.interapt.togglit.common.ISharedPreferencesManager;
import com.interapt.togglit.data.connectivity.ConnectivityReceiver;
import com.interapt.togglit.data.model.events.Events;
import com.interapt.togglit.injection.component.ActivityComponent;
import com.interapt.togglit.injection.component.DaggerActivityComponent;
import com.interapt.togglit.injection.module.ActivityModule;
import com.interapt.togglit.util.StringUtil;
import com.interapt.togglit.util.ViewUtil;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import timber.log.Timber;


/**
 * Created by miller.barrera
 */
public class BaseActivity extends AppCompatActivity implements BaseActivityView, ConnectivityReceiver.ConnectivityReceiverListener {

    protected boolean firstLoad = false;

    @Inject
    BaseActivityPresenter mBaseActivityPresenter;

    @Inject
    ISharedPreferencesManager iSharedPreferencesManager;

    private ActivityComponent mActivityComponent;
    private Window window;
    private boolean isColorValidToSnackbar = false;
    private MaterialDialog mConnectionDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);

        window = this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        mBaseActivityPresenter.attachView(this);

        mConnectionDialog = new MaterialDialog.Builder(this).title(R.string.offline_mod_dialog_title)
                .autoDismiss(false).content(R.string.offline_mod_dialog_content)
                .positiveText(R.string.offline_mod_dialog_button).progress(true, 0).cancelable(false)
                .canceledOnTouchOutside(false).build();
    }

    private void checkForCrashes() {
        mBaseActivityPresenter.checkForCrashes();
    }


    @Override
    protected void onResume() {
        super.onResume();
        checkForCrashes();
        InteraptApplication.get(this).setConnectivityListener(this);
        onNetworkConnectionChanged(ConnectivityReceiver.isConnected(this));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBaseActivityPresenter.detachView();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    public static StringBuffer formatPhoneNumber(String number) {
        StringBuffer stringBuffer = new StringBuffer(number);
        stringBuffer.insert(0, "(");
        stringBuffer.insert(4, ")");
        stringBuffer.deleteCharAt(5);
        stringBuffer.insert(5, " ");
        return stringBuffer;
    }

    public static String formatDate(Date date) {
        date = new Date();
        Format formatter = new SimpleDateFormat("MM/dd/yy", Locale.US);
        return formatter.format(date);
    }


    public ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder().activityModule(new ActivityModule(this))
                    .applicationComponent(InteraptApplication.get(this).getComponent()).build();
        }
        return mActivityComponent;
    }

    protected void callActivity(Intent intent) {
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Inflate the menu; this adds items to the action bar if it is present. */
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void showError(String message, @Nullable Intent intent, @Constants.ErrorType int errorType) {

        if (Constants.LOW_ERROR == errorType) {

            Snackbar snack = ViewUtil.snackbar(this, findViewById(android.R.id.content), message);

            snack.setDuration(Snackbar.LENGTH_LONG).show();

        }
    }


    @Override
    public void showError(String message, @Constants.ErrorType int errorType) {
        Timber.d("BaseActivity: Error retrieving ads");
    }

    @Override
    public void showRefresh(boolean show) {

    }


    public String getEventMessageToShare(Events ev) {
        String evMessage = "";
        //StartDate
        Date formatDate = StringUtil.convertStringToDate(ev.getStartDate());
        String month = StringUtil.capitalizeString(StringUtil.getOnlyMonthFromDate(formatDate));
        String day = StringUtil.getOnlyDayFromDate(formatDate, true);
        String time = StringUtil.toTwelveHourFormat(StringUtil.getOnlyTimeFromDate(formatDate));

        if (!StringUtil.isNullOrEmpty(ev.getUrl())) {
            if (!StringUtil.isNullOrEmpty(day)) {
                evMessage = ev.getEventName() + Constants.BLANK_SPACE + "is happening on" +
                        Constants.BLANK_SPACE + month + Constants.BLANK_SPACE
                        + day + Constants.BLANK_SPACE + "at" + Constants.BLANK_SPACE + time +
                        "!" + Constants.BLANK_SPACE + "Go to" + Constants.BLANK_SPACE +
                        ev.getUrl() + Constants.BLANK_SPACE + "for more info.";
            }
        } else {
            if (!StringUtil.isNullOrEmpty(day)) {
                evMessage = ev.getEventName() + Constants.BLANK_SPACE + "is happening on" +
                        Constants.BLANK_SPACE + month + Constants.BLANK_SPACE
                        + day + Constants.BLANK_SPACE + "at" + Constants.BLANK_SPACE + time + "!";
            }
        }

        return evMessage;
    }


    /**
     * Get location (lat, long) from String address
     *
     * @param strAddress is a string containing the address.
     *                   The address variable holds the converted addresses.
     * @return GeoPoint with latitude and longitude
     */
    public GeoPoint getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        GeoPoint p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 1);
            if (address == null) {
                return null;
            }
            if (address.size() > 0) {
                Address location = address.get(0);
                location.getLatitude();
                location.getLongitude();

                p1 = new GeoPoint((int) (location.getLatitude() * 1E6),
                        (int) (location.getLongitude() * 1E6));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return p1;
    }

    /**
     * Open the event location in native maps app
     *
     * @param validAddress valid address
     */
    public void showEventLocation(String validAddress) {
        String encodeAddress = Uri.encode(validAddress);
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + encodeAddress);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(gmmIntentUri);

        if (intent.resolveActivity(getPackageManager()) != null) {
            callActivity(intent);
        }

    }

    public void initShareIntent(Events ev) {
        Intent sendIntent = new Intent();
            if (ev != null) {
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, getEventMessageToShare(ev));
                sendIntent.setType("text/plain");
            }
        callActivity(sendIntent);
    }



    /**
     * Add events to native app Calendar dynamically
     *
     * @param ev selected event
     */
    public void addEventToCalendar(Events ev) {

        Intent calIntent = new Intent(Intent.ACTION_INSERT);
        calIntent.setData(CalendarContract.Events.CONTENT_URI);
        calIntent.putExtra(CalendarContract.Events.TITLE, ev.getEventName());
        calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, ev.getAddress());
        calIntent.putExtra(CalendarContract.Events.DESCRIPTION, ev.getDescription());
        calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, StringUtil.getTimeInMillis(ev.getStartDate()));
        calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, StringUtil.getTimeInMillis(ev.getEndDate()));

        callActivity(calIntent);
    }


    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (!isConnected && !mConnectionDialog.isShowing()) {
            mConnectionDialog.show();
        } else if (isConnected && mConnectionDialog.isShowing()) {
            mConnectionDialog.dismiss();
            firstLoad = false;
        } else if (isConnected && !mConnectionDialog.isShowing()) {
            firstLoad = true;
        }
    }

    public void showSnackBarError(String message, @Nullable Intent intent, @Constants.ErrorType int errorType) {

        if (Constants.LOW_ERROR == errorType) {

            Snackbar snack = ViewUtil.snackbar(this, findViewById(android.R.id.content), message);


            snack.setDuration(Snackbar.LENGTH_LONG).show();

        }
    }


}


