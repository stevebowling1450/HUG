package com.interapt.togglit.ui.schoolDirectory;

import com.interapt.togglit.data.model.lists.Schools;
import com.interapt.togglit.ui.base.MvpView;

import java.util.List;

/**
 * Created by nicholashall on 2/22/17.
 */

public interface SchoolDirectoryView extends MvpView {

    void getSchoolList(List<Schools> schoolList);

}
