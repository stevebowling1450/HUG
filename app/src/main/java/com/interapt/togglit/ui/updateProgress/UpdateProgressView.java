package com.interapt.togglit.ui.updateProgress;

import com.interapt.togglit.data.model.lists.LessonsUndone;
import com.interapt.togglit.ui.base.MvpView;

import java.util.List;

/**
 * Created by stevebowling on 3/6/17.
 */

public interface UpdateProgressView extends MvpView {
    void setLesson(List<LessonsUndone> lesson);


    void showSnackbar();
}
