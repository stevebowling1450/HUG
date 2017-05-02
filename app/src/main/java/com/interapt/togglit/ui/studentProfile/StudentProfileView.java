package com.interapt.togglit.ui.studentProfile;

import com.interapt.togglit.data.model.user.Student;
import com.interapt.togglit.ui.base.MvpView;

/**
 * Created by stevebowling on 2/20/17.
 */

public interface StudentProfileView extends MvpView {
    void getStudent(Student student);

    void getVolunteerPhoneNumber(String volunteerPhone);


}