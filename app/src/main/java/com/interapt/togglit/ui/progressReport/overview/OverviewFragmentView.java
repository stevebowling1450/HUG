package com.interapt.togglit.ui.progressReport.overview;

import com.interapt.togglit.data.model.Average;
import com.interapt.togglit.ui.base.MvpView;

/**
 * Created by nicholashall on 3/7/17.
 */

public interface OverviewFragmentView extends MvpView {
    void setAverage(Average average);
}
