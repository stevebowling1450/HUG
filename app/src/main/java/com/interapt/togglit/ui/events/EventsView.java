package com.interapt.togglit.ui.events;

import com.interapt.togglit.data.model.events.Events;
import com.interapt.togglit.ui.base.MvpView;

import java.util.List;

/**
 * Created by miller.barrera on 10/01/2017.
 */

public interface EventsView extends MvpView {
    void getEventsList(List<Events> eventsList);

}
