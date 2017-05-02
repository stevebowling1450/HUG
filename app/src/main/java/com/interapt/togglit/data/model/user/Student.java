package com.interapt.togglit.data.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interapt.togglit.data.model.Average;
import com.interapt.togglit.data.model.session.Session;

import java.util.List;

public class Student {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("clarity_phone")
    @Expose
    private String vol_phone;
    @SerializedName("school_id")
    @Expose
    private Integer schoolId;
    @SerializedName("school_name")
    @Expose
    private String schoolName;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("dra_score_1")
    @Expose
    private Integer draScore1;
    @SerializedName("dra_score_2")
    @Expose
    private Integer draScore2;
    @SerializedName("dra_score_3")
    @Expose
    private Integer draScore3;
    @SerializedName("notes")
    @Expose
    private Object notes;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("vol_name")
    @Expose
    private String volName;
    @SerializedName("vol_email")
    @Expose
    private String volEmail;
    @SerializedName("sessions")
    @Expose
    private List<Session> sessions = null;
    @SerializedName("average")
    @Expose
    private Average average;



    public Average getAverage() {
        return average;
    }

    public void setAverage(Average average) {
        this.average = average;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVol_phone() {
        return vol_phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDraScore1() {
        return draScore1;
    }

    public void setDraScore1(Integer draScore1) {
        this.draScore1 = draScore1;
    }

    public Integer getDraScore2() {
        return draScore2;
    }

    public void setDraScore2(Integer draScore2) {
        this.draScore2 = draScore2;
    }

    public Integer getDraScore3() {
        return draScore3;
    }

    public void setDraScore3(Integer draScore3) {
        this.draScore3 = draScore3;
    }

    public Object getNotes() {
        return notes;
    }

    public void setNotes(Object notes) {
        this.notes = notes;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVolName() {
        return volName;
    }

    public void setVolName(String volName) {
        this.volName = volName;
    }

    public String getVolEmail() {
        return volEmail;
    }

    public void setVolEmail(String volEmail) {
        this.volEmail = volEmail;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }

}