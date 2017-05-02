package com.interapt.togglit.ui.volunteerDirectory;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.interapt.togglit.R;
import com.interapt.togglit.data.model.lists.Volunteers;
import com.interapt.togglit.data.model.user.Volunteer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.myinnos.alphabetsindexfastscrollrecycler.utilities_fs.StringMatcher;

/**
 * Created by nicholashall on 2/13/17.
 */

public class VolunteerDirectoryAdapter extends RecyclerView.Adapter<VolunteerDirectoryAdapter.VolunteerAdapterViewHolder> implements SectionIndexer {

    private VolunteerDirectoryAdapter.OnItemClickListener mItemClickListener;
    private String mSections = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private Context mContext;
    private List<Volunteers> mVolunteerListItems;

    @Inject
    public VolunteerDirectoryAdapter(Context context) {
        this.mContext = context;
        mVolunteerListItems = new ArrayList<>();
    }

    public List<Volunteers> getmVolunteerListItems() {
        return mVolunteerListItems;
    }

//    public void setCallback(Callback callback) {
//        this.callback = callback;
//    }

    @Override
    public VolunteerAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_volunteer, parent, false);
        return new VolunteerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VolunteerDirectoryAdapter.VolunteerAdapterViewHolder holder, int position) {
        Volunteers volunteer = mVolunteerListItems.get(position);
        holder.bindVolunteer(mVolunteerListItems.get(position));
        holder.volItemName.setText(volunteer.getFirstName() + " " + volunteer.getLastName());
        holder.volItemSchool.setText(volunteer.getCommitmentLevel());
//            holder.volItemPosition.setText(volunteer.getVolunteerMobilePhone());

        if (holder.volunteer.getImage() != null) {
            holder.imageText.setVisibility(View.GONE);
            holder.setItemImage(volunteer.getImage());
        } else {
            if (volunteer.getFirstName() != null && volunteer.getLastName() != null) {
                holder.imageText.setText(getInitals(volunteer.getFirstName(), volunteer.getLastName()));
            }
        }

    }

    @Override
    public void onViewDetachedFromWindow(VolunteerAdapterViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.volItemContainer.clearAnimation();
    }


    public void populateVolunteerList(List<Volunteers> volunteerList) {
        mVolunteerListItems = volunteerList;
//        notifyDataSetChanged();

    }

    public String getInitals(String string, String string2) {
        String firstI = String.valueOf(string.charAt(0)).toUpperCase();
        String lastI = String.valueOf(string2.charAt(0)).toUpperCase();
        return firstI + lastI;
    }


    @Override
    public int getItemCount() {
        return mVolunteerListItems.size();
    }

    public Volunteers get(int index) {
        return mVolunteerListItems.get(index);
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
                        if (StringMatcher.match(String.valueOf(mVolunteerListItems.get(j).getLastName().charAt(0)), String.valueOf(k)))
                            return j;
                    }
                } else {
                    if (StringMatcher.match(String.valueOf(mVolunteerListItems.get(j).getLastName().charAt(0)), String.valueOf(mSections.charAt(i))))
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

    public void setOnItemClickListener(final VolunteerDirectoryAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


    public interface Callback {
        void onVolunteerClick(Volunteer volunteer);
    }


    public interface OnItemClickListener {
        void onItemClick(View view, Volunteers volunteer);
    }

    public class VolunteerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.volunteer_image)
        ImageView volItemImg;

        @BindView(R.id.volunteer_name)
        TextView volItemName;

        @BindView(R.id.volunteer_school)
        TextView volItemSchool;

        @BindView(R.id.item_volunteer_container)
        RelativeLayout volItemContainer;

        @BindView(R.id.image_text)
        TextView imageText;

        private Volunteers volunteer;


        public VolunteerAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            volItemContainer.setOnClickListener(this);

        }

        public void setItemImage(String bitImage) {
            byte[] imageByteArray = Base64.decode(bitImage, Base64.DEFAULT);

            Glide.with(mContext).load(imageByteArray).asBitmap().centerCrop().into(new BitmapImageViewTarget(volItemImg) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    volItemImg.setImageDrawable(circularBitmapDrawable);
                }
            });
        }


        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, volunteer);
            }
        }

        public void bindVolunteer(Volunteers volunteer) {
            this.volunteer = volunteer;
        }
    }
}
