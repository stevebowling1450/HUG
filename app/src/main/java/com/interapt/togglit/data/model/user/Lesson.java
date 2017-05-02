package com.interapt.togglit.data.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lesson {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("lesson_name")
    @Expose
    private String lessonName;
    @SerializedName("lesson_identifier")
    @Expose
    private String lessonIdentifier;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("total_word")
    @Expose
    private Integer totalWord;
    @SerializedName("image")
    @Expose
    private String image;


    public String getLessonIdentifier() {
        return lessonIdentifier;
    }

    public void setLessonIdentifier(String lessonIdentifier) {
        this.lessonIdentifier = lessonIdentifier;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getTotalWord() {
        return totalWord;
    }

    public void setTotalWord(Integer totalWord) {
        this.totalWord = totalWord;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}