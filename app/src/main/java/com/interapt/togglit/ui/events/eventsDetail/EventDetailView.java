package com.interapt.togglit.ui.events.eventsDetail;

import com.interapt.togglit.data.model.events.Events;
import com.interapt.togglit.ui.base.MvpView;

import java.util.List;

/**
 * Created by miller.barrera on 13/01/2017.
 */

public interface EventDetailView  extends MvpView {
    void getEvent(List<Events> event);

}
