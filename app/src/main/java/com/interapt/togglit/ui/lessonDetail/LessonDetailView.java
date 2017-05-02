package com.interapt.togglit.ui.lessonDetail;

import com.interapt.togglit.data.model.user.Lesson;
import com.interapt.togglit.ui.base.MvpView;

/**
 * Created by nicholashall on 3/9/17.
 */

public interface LessonDetailView extends MvpView {
    void setLesson(Lesson lesson);
}
