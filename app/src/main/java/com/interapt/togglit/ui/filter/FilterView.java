package com.interapt.togglit.ui.filter;

import com.interapt.togglit.data.model.lists.Schools;
import com.interapt.togglit.ui.base.MvpView;

import java.util.List;

/**
 * Created by stevebowling on 3/8/17.
 */

public interface FilterView extends MvpView {

    void getSchoolList(List<Schools> schoolList);
}
