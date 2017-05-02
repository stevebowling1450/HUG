package com.interapt.togglit.ui.studentDirectory;

import com.interapt.togglit.data.model.lists.Students;
import com.interapt.togglit.ui.base.MvpView;

import java.util.List;

/**
 * Created by stevebowling on 2/14/17.
 */

public interface StudentDirectoryView extends MvpView {
    void setStudentList(List<Students> studentList);

}
