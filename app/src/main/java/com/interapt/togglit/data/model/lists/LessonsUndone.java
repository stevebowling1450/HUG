package com.interapt.togglit.data.model.lists;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Matthew.Watson on 4/4/17.
 */

public class LessonsUndone {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("total_word")
    @Expose
    private Integer totalWords;
    @SerializedName("lesson_name")
    @Expose
    private String lessonName;
    @SerializedName("grade_level")
    @Expose
    private Integer gradeLevel;
    @SerializedName("lesson_identifier")
    @Expose
    private String lessonIdentifier;

    public Integer getTotalWords() {
        return totalWords;
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

    public Integer getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(Integer gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public String getLessonIdentifier() {
        return lessonIdentifier;
    }

    public void setLessonIdentifier(String lessonIdentifier) {
        this.lessonIdentifier = lessonIdentifier;
    }


}
