package com.interapt.togglit.data.model.lists;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Matthew.Watson on 4/4/17.
 */

public class LessonsNoImage {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("lesson_name")
    @Expose
    private String lessonName;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("total_word")
    @Expose
    private Integer totalWord;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("grade_level")
    @Expose
    private String gradeLevel;
    @SerializedName("lesson_identifier")
    @Expose
    private String lessonIdentifier;

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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public String  getLessonIdentifier() {
        return lessonIdentifier;
    }

    public void setLessonIdentifier(String lessonIdentifier) {
        this.lessonIdentifier = lessonIdentifier;
    }
}
