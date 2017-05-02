package com.interapt.togglit.ui.events.eventsDetail;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.interapt.togglit.R;
import com.interapt.togglit.common.Constants;
import com.interapt.togglit.data.model.events.Events;
import com.interapt.togglit.util.StringUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by miller.barrera on 24/01/2017.
 */

public class EventDetailPagerAdapter extends PagerAdapter implements View.OnClickListener {

    private Context mContext;
    private List<Events> mListEvents;
    private EventDetailPagerAdapter.OnItemClickListener mItemClickListener;


    @Inject
    public EventDetailPagerAdapter(Context context) {
        this.mContext = context;
        mListEvents = new ArrayList<>();
    }

    public void populatePagerList(List<Events> eventsList) {
        mListEvents = eventsList;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return mListEvents.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public Events get(int position) {
        return mListEvents.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = mLayoutInflater.inflate(R.layout.item_event_detail_pager, container, false);

        Events ev = mListEvents.get(position);

        //StartDate
        Date formatDate = StringUtil.convertStringToDate(ev.getStartDate());
        String month = StringUtil.getOnlyMonthFromDate(formatDate);
        String year = StringUtil.getOnlyYearFromDate(formatDate);
        String day = StringUtil.getOnlyDayFromDate(formatDate, true);
        String time = StringUtil.toTwelveHourFormat(StringUtil.getOnlyTimeFromDate(formatDate));
        //EndDate
        Date formatEndDate = StringUtil.convertStringToDate(ev.getEndDate());
        String monthEndDate = StringUtil.getOnlyMonthFromDate(formatEndDate).toLowerCase();
        String yearEndDate = StringUtil.getOnlyYearFromDate(formatEndDate);
        String dayEndDate = StringUtil.getOnlyDayFromDate(formatEndDate, true);
        String timeEndDate = StringUtil.toTwelveHourFormat(StringUtil.getOnlyTimeFromDate(formatEndDate));


        LinearLayout contentMonthDayDate = (LinearLayout) itemView.findViewById(R.id.content_mont_day_date);
        TextView tvEventDetailDayNumber = (TextView) itemView.findViewById(R.id.tv_event_detail_day_number);
        TextView tvEventDetailTitle = (TextView) itemView.findViewById(R.id.tv_event_detail_title);
        TextView tvEventDetailDate = (TextView) itemView.findViewById(R.id.tv_event_detail_date);
        TextView tvBlackBarDetailMonthYear = (TextView) itemView.findViewById(R.id.tv_black_bar_detail_month_year);
        TextView tvEventDescriptionTitle = (TextView) itemView.findViewById(R.id.tv_event_description_title);
        TextView tvEventDescription = (TextView) itemView.findViewById(R.id.tv_detail_description);
        TextView tvEventAddressTitle = (TextView) itemView.findViewById(R.id.tv_event_address_title);
        TextView tvEventAddress = (TextView) itemView.findViewById(R.id.tv_detail_address);
        ImageView imgEventImage = (ImageView) itemView.findViewById(R.id.img_detail_image);
        ImageView imgEventCalendar = (ImageView) itemView.findViewById(R.id.img_calendar);
        ImageView imgEventDirections = (ImageView) itemView.findViewById(R.id.img_directions);
        ImageView imgEventShare = (ImageView) itemView.findViewById(R.id.img_share);
        ImageView imgEventInfo = (ImageView) itemView.findViewById(R.id.img_info);
        ImageView imgChevronRight = (ImageView) itemView.findViewById(R.id.chevron_event_detail_left);
        ImageView imgChevronLeft = (ImageView) itemView.findViewById(R.id.chevron_event_detail_right);

        imgEventCalendar.setOnClickListener(this);
        imgEventDirections.setOnClickListener(this);
        imgEventShare.setOnClickListener(this);
        imgEventInfo.setOnClickListener(this);
        imgChevronRight.setOnClickListener(this);
        imgChevronLeft.setOnClickListener(this);




        tvEventDetailDayNumber.setText(day);
        tvEventDetailTitle.setText(ev.getEventName());

        if (month.equalsIgnoreCase(monthEndDate) && day.equalsIgnoreCase(dayEndDate)) {
            tvEventDetailDate.setText(StringUtil.capitalizeString(month) + Constants.BLANK_SPACE + day
                    + Constants.SYMBOL_VERTICAL_LINE + time + Constants.SYMBOL_HYPHEN_MINUS + timeEndDate);
        } else {
            tvEventDetailDate.setText(StringUtil.capitalizeString(month) + Constants.BLANK_SPACE + day
                    + Constants.SYMBOL_HYPHEN_MINUS + time + Constants.SYMBOL_VERTICAL_LINE
                    + StringUtil.capitalizeString(monthEndDate) + Constants.BLANK_SPACE + dayEndDate
                    + Constants.SYMBOL_HYPHEN_MINUS + timeEndDate);
        }

        tvBlackBarDetailMonthYear.setText(month.toUpperCase() +
                Constants.BLANK_SPACE + day + Constants.SYMBOL_COMMA + year);

        tvEventDescription.setText(ev.getDescription());
        tvEventAddress.setText(ev.getAddress());

        setDetailImage(ev.getImage(), imgEventImage);

        container.addView(itemView);

        return itemView;
    }


    private void setDetailImage(String img, ImageView imgView) {
        Glide.with(mContext).load(img)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .placeholder(R.drawable.img_pod_place_holder)
                .centerCrop()
                .into(imgView);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    @Override
    public void onClick(View v) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick(v);
        }
    }

    public void setOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view);
    }
}
