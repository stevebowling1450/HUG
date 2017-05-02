package com.interapt.togglit.ui.volunteerProfile;

import com.interapt.togglit.data.model.user.Volunteer;
import com.interapt.togglit.data.remote.IDataManager;
import com.interapt.togglit.util.ObserveOn;
import com.interapt.togglit.util.SubscribeOn;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import rx.Observable;
import rx.schedulers.Schedulers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by nicholashall on 3/24/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class VolunteerProfilePresenterTest {

    private VolunteerProfilePresenter volunteerProfilePresenter;

    @Mock
    ObserveOn observeOn;

    @Mock
    SubscribeOn subscribeOn;

    @Mock
    IDataManager iDataManager;

    @Mock
    VolunteerProfileView view;

    @Captor
    ArgumentCaptor<Volunteer> captor;




    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        volunteerProfilePresenter = new VolunteerProfilePresenter(iDataManager,observeOn, subscribeOn);
        Mockito.when(subscribeOn.getScheduler()).thenReturn(Schedulers.immediate());
        Mockito.when(observeOn.getScheduler()).thenReturn(Schedulers.immediate());
        volunteerProfilePresenter.attachView(view);

    }


    @Test
    public void should_return_volunteer_given_to_it_from_the_view(){
        Volunteer volunteer3 = new Volunteer();

        Mockito.when(iDataManager.getVolunteer(0)).thenReturn(Observable.just(volunteer3));


        volunteerProfilePresenter.onCreate(0);


        Mockito.verify(view).setVolunteer(captor.capture());

        Volunteer capturedValues = captor.getValue();

        assertThat(capturedValues, is(volunteer3));
    }


    @Test
    public void should_return_error_if_given_one() throws Exception {

        Mockito.when(iDataManager.getVolunteer(0)).thenReturn(Observable.error(new RuntimeException("There was an error!")));


        volunteerProfilePresenter.onCreate(0);

    }




}
