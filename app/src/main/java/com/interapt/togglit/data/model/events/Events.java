
package com.interapt.togglit.data.model.events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

public class Events implements Comparable<Events>, Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("event_name")
    @Expose
    private String eventName;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("school_id")
    @Expose
    private Integer schoolId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("zip")
    @Expose
    private Integer zip;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("end_date")
    @Expose
    private String endDate;

    private boolean isChecked = true;

    private String custom_color;


    public String getCustom_color() {
        return custom_color;
    }

    public void setCustom_color(String custom_color) {
        this.custom_color = custom_color;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getZip() {
        return zip;
    }

    public void setZip(Integer zip) {
        this.zip = zip;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


    @Override
    public int compareTo(Events o) {
        return Comparators.DATE.compare(this, o);
    }

    /**
     * Sort Events list by start_date
     */
    public static class Comparators {
        public static Comparator<Events> DATE = (o1, o2) -> o1.getStartDate().compareTo(o2.getStartDate());
        public static Comparator<Events> LABEL = (o1, o2) -> o1.getLabel().compareTo(o2.getLabel());

    }

    public boolean isEventForSchool(List<Integer> filteredList) {
        for (int i : filteredList) {
            if (schoolId.equals(i)) {
                return true;
            }
        }
        return false;
    }
}
