package com.interapt.togglit.ui.progressReport.lesson.lessonList;

import com.interapt.togglit.data.model.lists.Lessons;
import com.interapt.togglit.data.model.lists.LessonsNoImage;
import com.interapt.togglit.ui.base.MvpView;

import java.util.List;

/**
 * Created by Matthew.Watson on 2/21/17.
 */

public interface LessonListFragmentView extends MvpView {

    void showLessons(List<Integer> lesson);

    void setLesson(List<LessonsNoImage> lesson);
}