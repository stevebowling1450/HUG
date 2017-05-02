package com.interapt.togglit.ui.events.eventsCalendar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.interapt.togglit.R;
import com.interapt.togglit.common.Constants;
import com.interapt.togglit.data.model.events.Events;
import com.interapt.togglit.util.StringUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by miller.barrera on 24/01/2017.
 */

public class EventsCalendarDetailAdapter extends RecyclerView.Adapter<EventsCalendarDetailAdapter.CalendarEventsDetailViewHolder> {
    private List<Events> mCalendarEventsList;
    private EventsCalendarDetailAdapter.OnItemClickListener mItemClickListener;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    @Inject
    public EventsCalendarDetailAdapter(Context mContext) {
        mCalendarEventsList = new ArrayList<>();
        // uncomment the line below if you want to open only one row at a time
        viewBinderHelper.setOpenOnlyOne(true);
    }

    @Override
    public CalendarEventsDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_calendar_detail_event, parent, false);
        return new CalendarEventsDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CalendarEventsDetailViewHolder holder, int position) {
        Events ev = mCalendarEventsList.get(position);

        final String data = mCalendarEventsList.get(position).toString();

        viewBinderHelper.bind(holder.swipeRevealLayout, data);
        //StartDate
        Date formatDate = StringUtil.convertStringToDate(ev.getStartDate());
        String month = StringUtil.getOnlyMonthFromDate(formatDate);
        String year = StringUtil.getOnlyYearFromDate(formatDate);
        String day = StringUtil.getOnlyDayFromDate(formatDate, true);
        String time = StringUtil.toTwelveHourFormat(StringUtil.getOnlyTimeFromDate(formatDate));

        //EndDate
        Date formatEndDate = StringUtil.convertStringToDate(ev.getEndDate());
        String monthEndDate = StringUtil.getOnlyMonthFromDate(formatEndDate);
        String yearEndDate = StringUtil.getOnlyYearFromDate(formatEndDate);
        String dayEndDate = StringUtil.getOnlyDayFromDate(formatEndDate, true);
        String timeEndDate = StringUtil.toTwelveHourFormat(StringUtil.getOnlyTimeFromDate(formatEndDate));

        //Required to validate last position with current
        Date formatLastDay;
        String lastDay = "";


        if (StringUtil.isNullOrEmpty(ev.getUrl())) {
            holder.mContentInfoIcon.setVisibility(View.GONE);
        } else {
            holder.mContentInfoIcon.setVisibility(View.VISIBLE);
        }

        if (StringUtil.isNullOrEmpty(ev.getCity()) || StringUtil.isNullOrEmpty(ev.getAddress())
                || StringUtil.isNullOrEmpty(ev.getState())) {
            holder.mContentLocationIcon.setVisibility(View.GONE);
        } else {
            holder.mContentLocationIcon.setVisibility(View.VISIBLE);
        }
        if (position > 0) {
            formatLastDay = StringUtil.convertStringToDate(get(position - 1).getStartDate());
            lastDay = StringUtil.getOnlyDayFromDate(formatLastDay, true);
        }


        if (day.equalsIgnoreCase(lastDay)) {
            holder.contentCalendarMonthDay.setVisibility(View.INVISIBLE);
        } else {
            holder.contentCalendarMonthDay.setVisibility(View.VISIBLE);
            holder.calendarEventMonthDayName.setText(StringUtil.getOnlyDayFromDate(StringUtil.convertStringToDate(ev.getStartDate()), false));
            holder.calendarMonthDayNumber.setText(StringUtil.getOnlyDayFromDate(StringUtil.convertStringToDate(ev.getStartDate()), true));

        }


        holder.calendarEventTitle.setText(ev.getEventName());

        if (month.equalsIgnoreCase(monthEndDate) && day.equalsIgnoreCase(dayEndDate)) {
            holder.calendarEventDate.setText(StringUtil.capitalizeString(month) + Constants.BLANK_SPACE + day
                    + Constants.SYMBOL_VERTICAL_LINE + time + Constants.SYMBOL_HYPHEN_MINUS + timeEndDate);
        } else {
            holder.calendarEventDate.setText(StringUtil.capitalizeString(month) + Constants.BLANK_SPACE + day
                    + Constants.SYMBOL_VERTICAL_LINE + time + Constants.SYMBOL_HYPHEN_MINUS + "\n"
                    + StringUtil.capitalizeString(monthEndDate) + Constants.BLANK_SPACE + dayEndDate
                    + Constants.SYMBOL_VERTICAL_LINE + timeEndDate);
        }


    }

    @Override
    public int getItemCount() {
        return mCalendarEventsList.size();
    }

    public Events get(int position) {
        return mCalendarEventsList.get(position);
    }

    public List<Events> getmListEvents() {
        return mCalendarEventsList;
    }

    public void populateCalendarEventsList(List<Events> calendarEventsList) {
        mCalendarEventsList = calendarEventsList;
        notifyDataSetChanged();
    }


    class CalendarEventsDetailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.content_calendar_month_day)
        RelativeLayout contentCalendarMonthDay;

        @BindView(R.id.swipe_layout)
        SwipeRevealLayout swipeRevealLayout;

        @BindView(R.id.content_location)
        RelativeLayout mContentLocationIcon;

        @BindView(R.id.content_info)
        RelativeLayout mContentInfoIcon;

        @BindView(R.id.tv_calendar_event_month_day_name)
        TextView calendarEventMonthDayName;

        @BindView(R.id.tv_calendar_month_day_number)
        TextView calendarMonthDayNumber;

        @BindView(R.id.tv_calendar_events_title)
        TextView calendarEventTitle;

        @BindView(R.id.calendar_events_date)
        TextView calendarEventDate;

        @BindView(R.id.content_main_event_item_layout)
        FrameLayout mContentMainItemLayout;

        @BindView(R.id.img_calendar)
        ImageView imgCalendar;

        @BindView(R.id.img_directions)
        ImageView imgDirections;

        @BindView(R.id.img_share)
        ImageView imgShare;

        @BindView(R.id.img_info)
        ImageView imgInfo;

        public CalendarEventsDetailViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mContentMainItemLayout.setOnClickListener(this);
            imgCalendar.setOnClickListener(this);
            imgDirections.setOnClickListener(this);
            imgShare.setOnClickListener(this);
            imgInfo.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }


    public void setOnItemClickListener(EventsCalendarDetailAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

}
