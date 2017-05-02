package com.interapt.togglit.data.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import net.hockeyapp.android.metrics.model.User;

import java.util.List;

/**
 * Created by Matthew.Watson on 2/13/17.
 */

public class School {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("school_name")
    @Expose
    private String schoolName;
    @SerializedName("school_phone")
    @Expose
    private String schoolPhone;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("school_city")
    @Expose
    private String schoolCity;
    @SerializedName("school_state")
    @Expose
    private String schoolState;
    @SerializedName("school_zip")
    @Expose
    private Integer schoolZip;
    @SerializedName("school_district")
    @Expose
    private String schoolDistrict;
    @SerializedName("principal_name")
    @Expose
    private String principalName;
    @SerializedName("vice_principal_name")
    @Expose
    private String vicePrincipalName;
    @SerializedName("clarity_person_name")
    @Expose
    private String clarityPersonName;
    @SerializedName("clarity_person_phone")
    @Expose
    private String clarityPersonPhone;
    @SerializedName("clarity_person_second_phone")
    @Expose
    private String clarityPersonSecondPhone;
    @SerializedName("clarity_person_email")
    @Expose
    private String clarityPersonEmail;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("students")
    @Expose
    private List<Student> students = null;
    @SerializedName("users")
    @Expose
    private List<User> users = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolPhone() {
        return schoolPhone;
    }

    public void setSchoolPhone(String schoolPhone) {
        this.schoolPhone = schoolPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSchoolCity() {
        return schoolCity;
    }

    public void setSchoolCity(String schoolCity) {
        this.schoolCity = schoolCity;
    }

    public String getSchoolState() {
        return schoolState;
    }

    public void setSchoolState(String schoolState) {
        this.schoolState = schoolState;
    }

    public Integer getSchoolZip() {
        return schoolZip;
    }

    public void setSchoolZip(Integer schoolZip) {
        this.schoolZip = schoolZip;
    }

    public String getSchoolDistrict() {
        return schoolDistrict;
    }

    public void setSchoolDistrict(String schoolDistrict) {
        this.schoolDistrict = schoolDistrict;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public String getVicePrincipalName() {
        return vicePrincipalName;
    }

    public void setVicePrincipalName(String vicePrincipalName) {
        this.vicePrincipalName = vicePrincipalName;
    }

    public String getClarityPersonName() {
        return clarityPersonName;
    }

    public void setClarityPersonName(String clarityPersonName) {
        this.clarityPersonName = clarityPersonName;
    }

    public String getClarityPersonPhone() {
        return clarityPersonPhone;
    }

    public void setClarityPersonPhone(String clarityPersonPhone) {
        this.clarityPersonPhone = clarityPersonPhone;
    }

    public String getClarityPersonSecondPhone() {
        return clarityPersonSecondPhone;
    }

    public void setClarityPersonSecondPhone(String clarityPersonSecondPhone) {
        this.clarityPersonSecondPhone = clarityPersonSecondPhone;
    }

    public String getClarityPersonEmail() {
        return clarityPersonEmail;
    }

    public void setClarityPersonEmail(String clarityPersonEmail) {
        this.clarityPersonEmail = clarityPersonEmail;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}