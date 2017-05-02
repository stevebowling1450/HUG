package com.interapt.togglit.ui.progressReport;

import com.interapt.togglit.data.model.user.Student;
import com.interapt.togglit.ui.base.MvpView;

/**
 * Created by Matthew.Watson on 2/20/17.
 */

public interface ProgressReportView extends MvpView {

    void setStudent(Student student);
}
