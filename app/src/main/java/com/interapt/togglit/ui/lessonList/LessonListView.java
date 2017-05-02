package com.interapt.togglit.ui.lessonList;

import com.interapt.togglit.data.model.lists.Lessons;
import com.interapt.togglit.data.model.lists.LessonsNoImage;
import com.interapt.togglit.ui.base.MvpView;

import java.util.List;

/**
 * Created by Matthew.Watson on 2/16/17.
 */

public interface LessonListView extends MvpView {

    void setLesson(List<LessonsNoImage> lesson);
}
