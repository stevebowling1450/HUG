package com.interapt.togglit.ui.progressReport.acumen;


import com.interapt.togglit.data.model.session.StudentLessonAcumen;
import com.interapt.togglit.ui.base.MvpView;

import java.util.List;

/**
 * Created by nicholashall on 3/3/17.
 */

public interface AcumenFragmentView extends MvpView {
    void setFragmentSessions(List<StudentLessonAcumen> sessions);

    void showError(String s);

   // void setLesson(List<LessonsNoImage> lesson);
}
