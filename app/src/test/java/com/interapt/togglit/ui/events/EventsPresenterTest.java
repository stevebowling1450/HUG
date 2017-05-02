package com.interapt.togglit.ui.events;

import com.interapt.togglit.data.model.events.Events;
import com.interapt.togglit.data.remote.IDataManager;
import com.interapt.togglit.util.ObserveOn;
import com.interapt.togglit.util.SubscribeOn;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Matthew.Watson on 4/4/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class EventsPresenterTest {

    @Mock
    EventsView view;

    @Mock
    IDataManager iDataManager;

    @Mock
    SubscribeOn subscribeOn;

    @Mock
    ObserveOn observeOn;

    @Captor
    private ArgumentCaptor<List<Events>> captor;

    private EventsPresenter presenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new EventsPresenter(iDataManager, subscribeOn, observeOn);
        presenter.attachView(view);
        when(subscribeOn.getScheduler()).thenReturn(Schedulers.immediate());
        when(observeOn.getScheduler()).thenReturn(Schedulers.immediate());

    }

    @Test
    public void should_pass_events_to_view() throws Exception {
        //given
        List<Events> events = Arrays.asList(new Events(), new Events(), new Events());

        //when
        when(iDataManager.getAllEvents()).thenReturn(Observable.just(events));
//        presenter.onCreate(Mockito.anyList());


        // then

        verify(view).getEventsList(captor.capture());

        assertThat(captor.getValue().size(), is(events.size()));
    }

    @SuppressWarnings("WrongConstant")
    @Test
    public void should_show_error() {
        // ERROR
        when(iDataManager.getAllEvents()).thenReturn(Observable.error(new RuntimeException("There was an error")));

        // then
//        presenter.onCreate();

        verify(view).showError(anyString(), anyInt());
    }


}