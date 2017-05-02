package com.interapt.togglit.ui.schoolProfile;

import com.interapt.togglit.data.model.user.School;
import com.interapt.togglit.ui.base.MvpView;

/**
 * Created by nicholashall on 2/23/17.
 */

public interface SchoolProfileView extends MvpView {


    void setSchool(School school);

}
