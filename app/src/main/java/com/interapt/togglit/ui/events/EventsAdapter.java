package com.interapt.togglit.ui.events;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
 * Created by miller.barrera on 10/01/2017.
 */

public class EventsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    // This object helps you save/restore the open/close state of each view
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private OnItemClickListener mItemClickListener;
    private List<Events> mListEvents;

    @Inject
    public EventsAdapter(Context context) {
        mListEvents = new ArrayList<>();
        // uncomment the line below if you want to open only one row at a time
        viewBinderHelper.setOpenOnlyOne(true);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_events, parent, false);
            return new EventsViewHolder(view);
        } else if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_item_header, parent, false);
            return new EventsViewHolderHeader(view);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Events events = mListEvents.get(position);
        final String data = mListEvents.get(position).toString();


        //StartDate
        Date formatDate = StringUtil.convertStringToDate(events.getStartDate());
        String month = StringUtil.getOnlyMonthFromDate(formatDate);
        String year = StringUtil.getOnlyYearFromDate(formatDate);
        String day = StringUtil.getOnlyDayFromDate(formatDate, true);
        String time = StringUtil.toTwelveHourFormat(StringUtil.getOnlyTimeFromDate(formatDate));

        //EndDate
        Date formatEndDate = StringUtil.convertStringToDate(events.getEndDate());
        String monthEndDate = StringUtil.getOnlyMonthFromDate(formatEndDate);
        String yearEndDate = StringUtil.getOnlyYearFromDate(formatEndDate);
        String dayEndDate = StringUtil.getOnlyDayFromDate(formatEndDate, true);
        String timeEndDate = StringUtil.toTwelveHourFormat(StringUtil.getOnlyTimeFromDate(formatEndDate));

        //Required to validate last position with current
        Date formatLastDay = null;
        String lastDay = "";

        if (position > 0) {
            formatLastDay = StringUtil.convertStringToDate(get(position - 1).getStartDate());
            lastDay = StringUtil.getOnlyDayFromDate(formatLastDay, true);
        }

        if (holder instanceof EventsViewHolder) {


            // Use ViewBindHelper to restore and save the open/close state of the SwipeRevealView
            // put an unique string id as value, can be any string which uniquely define the data
            viewBinderHelper.bind(((EventsViewHolder) holder).swipeRevealLayout, data);


            if (StringUtil.isNullOrEmpty(events.getUrl())) {
                ((EventsViewHolder) holder).mContentInfoIcon.setVisibility(View.GONE);
            } else {
                ((EventsViewHolder) holder).mContentInfoIcon.setVisibility(View.VISIBLE);
            }

            if (StringUtil.isNullOrEmpty(events.getCity()) || StringUtil.isNullOrEmpty(events.getAddress())
                    || StringUtil.isNullOrEmpty(events.getState())) {
                ((EventsViewHolder) holder).mContentLocationIcon.setVisibility(View.GONE);
            } else {
                ((EventsViewHolder) holder).mContentLocationIcon.setVisibility(View.VISIBLE);
            }


            if (!StringUtil.isNullOrEmpty(lastDay)) {
                if (day.equalsIgnoreCase(lastDay)) {
                    ((EventsViewHolder) holder).mContentMonthDay.setVisibility(View.INVISIBLE);
                } else {
                    ((EventsViewHolder) holder).mContentMonthDay.setVisibility(View.VISIBLE);
                    ((EventsViewHolder) holder).tvEventMonthDayName.setText(StringUtil.getOnlyDayFromDate(StringUtil.convertStringToDate(events.getStartDate()), false));
                    ((EventsViewHolder) holder).tvEventMonthDayNumber.setText(StringUtil.getOnlyDayFromDate(StringUtil.convertStringToDate(events.getStartDate()), true));

                }
            }

            ((EventsViewHolder) holder).eventsTitle.setText(events.getEventName());

            if (month.equalsIgnoreCase(monthEndDate) && day.equalsIgnoreCase(dayEndDate)) {
                ((EventsViewHolder) holder).eventsDate.setText(StringUtil.capitalizeString(month) + Constants.BLANK_SPACE + day
                        + Constants.SYMBOL_VERTICAL_LINE + time + Constants.SYMBOL_HYPHEN_MINUS + timeEndDate);
            } else {
                ((EventsViewHolder) holder).eventsDate.setText(StringUtil.capitalizeString(month) + Constants.BLANK_SPACE + day
                        + Constants.SYMBOL_VERTICAL_LINE + time + Constants.SYMBOL_HYPHEN_MINUS + "\n"
                        + StringUtil.capitalizeString(monthEndDate) + Constants.BLANK_SPACE + dayEndDate
                        + Constants.SYMBOL_VERTICAL_LINE + timeEndDate);
            }

        } else if (holder instanceof EventsViewHolderHeader) {


            ((EventsViewHolderHeader) holder).tvBlackBarMonth.setVisibility(View.VISIBLE);
            ((EventsViewHolderHeader) holder).tvBlackBarMonth.setText(month.toUpperCase() + Constants.SYMBOL_COMMA + year);

            // Use ViewBindHelper to restore and save the open/close state of the SwipeRevealView
            // put an unique string id as value, can be any string which uniquely define the data
            viewBinderHelper.bind(((EventsViewHolderHeader) holder).swipeRevealLayout, data);

            if (StringUtil.isNullOrEmpty(events.getUrl())) {
                ((EventsViewHolderHeader) holder).mContentInfoIcon.setVisibility(View.GONE);
            } else {
                ((EventsViewHolderHeader) holder).mContentInfoIcon.setVisibility(View.VISIBLE);
            }

            if (StringUtil.isNullOrEmpty(events.getCity()) || StringUtil.isNullOrEmpty(events.getAddress())
                    || StringUtil.isNullOrEmpty(events.getState())) {
                ((EventsViewHolderHeader) holder).mContentLocationIcon.setVisibility(View.GONE);
            } else {
                ((EventsViewHolderHeader) holder).mContentLocationIcon.setVisibility(View.VISIBLE);
            }

            ((EventsViewHolderHeader) holder).tvEventMonthDayName.setText(StringUtil.getOnlyDayFromDate(StringUtil.convertStringToDate(events.getStartDate()), false));
            ((EventsViewHolderHeader) holder).tvEventMonthDayNumber.setText(StringUtil.getOnlyDayFromDate(StringUtil.convertStringToDate(events.getStartDate()), true));
            ((EventsViewHolderHeader) holder).eventsTitle.setText(events.getEventName());


            if (month.equalsIgnoreCase(monthEndDate) && day.equalsIgnoreCase(dayEndDate)) {
                ((EventsViewHolderHeader) holder).eventsDate.setText(StringUtil.capitalizeString(month) + Constants.BLANK_SPACE + day
                        + Constants.SYMBOL_VERTICAL_LINE + time + Constants.SYMBOL_HYPHEN_MINUS + timeEndDate);
            } else {
                ((EventsViewHolderHeader) holder).eventsDate.setText(month + Constants.BLANK_SPACE + day
                        + Constants.SYMBOL_VERTICAL_LINE + time + Constants.SYMBOL_HYPHEN_MINUS + "\n"
                        + StringUtil.capitalizeString(monthEndDate) + Constants.BLANK_SPACE + dayEndDate
                        + Constants.SYMBOL_VERTICAL_LINE + timeEndDate);
            }
        }
    }


    public void populateEventsList(List<Events> eventList) {
        mListEvents = eventList;
        notifyDataSetChanged();
    }

    public Events get(int position) {
        return mListEvents.get(position);
    }

    public List<Events> getmListEvents() {
        return mListEvents;
    }

    @Override
    public int getItemCount() {
        return mListEvents.size();
    }

    @Override
    public int getItemViewType(int position) {
        Date formatLastDate;
        String lastMonth = "";
        Date formatDate = StringUtil.convertStringToDate(get(position).getStartDate());
        String month = StringUtil.getOnlyMonthFromDate(formatDate);

        if (position > 0) {
            formatLastDate = StringUtil.convertStringToDate(get(position - 1).getStartDate());
            lastMonth = StringUtil.getOnlyMonthFromDate(formatLastDate);
        }
        int viewTypeId;

        if (isPositionHeader(position)) {
            viewTypeId = 0;

        } else {
            if (month.equalsIgnoreCase(lastMonth)) {
                viewTypeId = 1;
            } else {
                viewTypeId = 0;
            }
        }

        return viewTypeId;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    public void setOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    class EventsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.swipe_layout)
        SwipeRevealLayout swipeRevealLayout;

        @BindView(R.id.content_month_day)
        RelativeLayout mContentMonthDay;

        @BindView(R.id.content_location)
        RelativeLayout mContentLocationIcon;

        @BindView(R.id.content_info)
        RelativeLayout mContentInfoIcon;

        @BindView(R.id.tv_event_month_day_name)
        TextView tvEventMonthDayName;

        @BindView(R.id.tv_event_month_day_number)
        TextView tvEventMonthDayNumber;

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

        @BindView(R.id.events_title)
        TextView eventsTitle;

        @BindView(R.id.events_date)
        TextView eventsDate;

        public EventsViewHolder(View itemView) {
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

    class EventsViewHolderHeader extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.content_black_bar_month)
        LinearLayout contentBlackBarMonth;

        @BindView(R.id.tv_black_bar_month_year)
        TextView tvBlackBarMonth;

        @BindView(R.id.swipe_layout)
        SwipeRevealLayout swipeRevealLayout;

        @BindView(R.id.content_month_day)
        RelativeLayout mContenMonthDay;

        @BindView(R.id.content_location)
        RelativeLayout mContentLocationIcon;

        @BindView(R.id.content_info)
        RelativeLayout mContentInfoIcon;

        @BindView(R.id.tv_event_month_day_name)
        TextView tvEventMonthDayName;

        @BindView(R.id.tv_event_month_day_number)
        TextView tvEventMonthDayNumber;

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

        @BindView(R.id.events_title)
        TextView eventsTitle;

        @BindView(R.id.events_date)
        TextView eventsDate;


        public EventsViewHolderHeader(View itemView) {
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
}

