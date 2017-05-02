package com.interapt.togglit.ui.progressReport.lesson.lessonResult;

import com.interapt.togglit.data.model.session.Session;
import com.interapt.togglit.ui.base.MvpView;

import java.util.List;

/**
 * Created by Matthew.Watson on 2/22/17.
 */

public interface LessonResultFragmentView extends MvpView {

    void setFragmentLesson(List<Session> session);

}
