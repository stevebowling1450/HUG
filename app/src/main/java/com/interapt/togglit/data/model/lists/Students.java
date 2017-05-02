package com.interapt.togglit.data.model.lists;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Matthew.Watson on 3/2/17.
 */

public class Students {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
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
    private Object draScore1;
    @SerializedName("dra_score_2")
    @Expose
    private Object draScore2;
    @SerializedName("dra_score_3")
    @Expose
    private Object draScore3;
    @SerializedName("notes")
    @Expose
    private Object notes;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("sessions")
    @Expose
    private List<Object> sessions = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Object getDraScore1() {
        return draScore1;
    }

    public void setDraScore1(Object draScore1) {
        this.draScore1 = draScore1;
    }

    public Object getDraScore2() {
        return draScore2;
    }

    public void setDraScore2(Object draScore2) {
        this.draScore2 = draScore2;
    }

    public Object getDraScore3() {
        return draScore3;
    }

    public void setDraScore3(Object draScore3) {
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

    public List<Object> getSessions() {
        return sessions;
    }

    public void setSessions(List<Object> sessions) {
        this.sessions = sessions;
    }

}
