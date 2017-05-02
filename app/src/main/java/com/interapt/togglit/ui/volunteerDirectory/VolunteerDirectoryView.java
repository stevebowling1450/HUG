package com.interapt.togglit.ui.volunteerDirectory;

import com.interapt.togglit.data.model.lists.Volunteers;
import com.interapt.togglit.ui.base.MvpView;

import java.util.List;

/**
 * Created by nicholashall on 2/13/17.
 */

public interface VolunteerDirectoryView extends MvpView {

    void getVolunteerList(List<Volunteers> volunteerList);
}
