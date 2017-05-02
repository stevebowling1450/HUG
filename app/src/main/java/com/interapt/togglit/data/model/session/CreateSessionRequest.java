package com.interapt.togglit.data.model.session;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class CreateSessionRequest {

    @SerializedName("date")
    @Expose
    private Date date;
    @SerializedName("student_id")
    @Expose
    private Integer studentId;
    @SerializedName("student_mood")
    @Expose
    private Integer studentMood;
    @SerializedName("word_missed")
    @Expose
    private Integer wordMissed;
    @SerializedName("lesson_id")
    @Expose
    private Integer lessonId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;

    @SerializedName("recorded_by")
    @Expose
    private String recordedBy;

    @SerializedName("time")
    @Expose
    private Integer time;

    public CreateSessionRequest(Date date, Integer studentId, Integer studentMood, Integer wordMissed, Integer lessonId, Integer userId, String recordedBy, Integer time) {
        this.date = date;
        this.studentId = studentId;
        this.studentMood = studentMood;
        this.wordMissed = wordMissed;
        this.lessonId = lessonId;
        this.userId = userId;
        this.recordedBy = recordedBy;
        this.time = time;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getStudentMood() {
        return studentMood;
    }

    public void setStudentMood(Integer studentMood) {
        this.studentMood = studentMood;
    }

    public Integer getWordMissed() {
        return wordMissed;
    }

    public void setWordMissed(Integer wordMissed) {
        this.wordMissed = wordMissed;
    }

    public Integer getLessonId() {
        return lessonId;
    }

    public void setLessonId(Integer lessonId) {
        this.lessonId = lessonId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRecordedBy() {
        return recordedBy;
    }

    public void setRecordedBy(String recordedBy) {
        this.recordedBy = recordedBy;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }
}