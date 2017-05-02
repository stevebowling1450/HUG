package com.interapt.togglit.ui.volunteerDirectory;

import com.interapt.togglit.data.model.lists.Volunteers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import rx.Subscriber;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by nicholashall on 3/21/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class VolunteerDirectoryPresenterTest {

    @Mock
    VolunteerDirectoryUseCase volunteerDirectoryUseCase;

    @Mock
    VolunteerDirectoryView view;

    @Captor
    private ArgumentCaptor<List<Volunteers>> captor;

    private VolunteerDirectoryPresenter volunteerDirectoryPresenter;



    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        volunteerDirectoryPresenter = new VolunteerDirectoryPresenter(volunteerDirectoryUseCase);
        volunteerDirectoryPresenter.attachView(view);
    }

    @Test
    public void should_not_crash_if_one_volunteers_exist(){


        List<Volunteers> volunteers = Arrays.asList(new Volunteers());

        Mockito.doAnswer(invocation -> {
            Subscriber<List<Volunteers>> subscriber = (Subscriber<List<Volunteers>>) invocation.getArguments()[1];
            subscriber.onNext(volunteers);
            subscriber.onCompleted();
            return null;
        }).when(volunteerDirectoryUseCase).getAllVolunteers(Mockito.any(),Mockito.any());


        volunteerDirectoryPresenter.onCreate(new ArrayList<>());

        Mockito.verify(view).getVolunteerList(captor.capture());

        assertThat(captor.getValue().size(), is(volunteers.size()));

    }



    @Test
    public void should_not_crash_if_multiple_volunteers_exist(){


        List<Volunteers> volunteers = Arrays.asList(new Volunteers(),new Volunteers(),new Volunteers());

        Mockito.doAnswer(invocation -> {
            Subscriber<List<Volunteers>> subscriber = (Subscriber<List<Volunteers>>) invocation.getArguments()[1];
            subscriber.onNext(volunteers);
            subscriber.onCompleted();
            return null;
        }).when(volunteerDirectoryUseCase).getAllVolunteers(Mockito.any(), Mockito.any());

        volunteerDirectoryPresenter.onCreate(new ArrayList<>());

        Mockito.verify(view).getVolunteerList(captor.capture());

        List<Volunteers> capturedValues = captor.getValue();

        assertThat(capturedValues.size(), is(volunteers.size()));

    }

    @Test
    public void should_not_crash_if_no_volunteers_exist(){

        Mockito.doAnswer(invocation -> {
            Subscriber<List<Volunteers>> subscriber = (Subscriber<List<Volunteers>>) invocation.getArguments()[1];
            subscriber.onNext(Collections.emptyList());
            subscriber.onCompleted();
            return null;
        }).when(volunteerDirectoryUseCase).getAllVolunteers(Mockito.any(), Mockito.any());

        volunteerDirectoryPresenter.onCreate(new ArrayList<>());



    }

    @SuppressWarnings("WrongConstant")
    @Test
    public void should_show_error_if_given_one(){


        Mockito.doAnswer(invocation -> {
            Subscriber<List<Volunteers>> subscriber = (Subscriber<List<Volunteers>>) invocation.getArguments()[1];

            subscriber.onError(new RuntimeException("Hi I just broke which is actually good!"));
            return null;
        }).when(volunteerDirectoryUseCase).getAllVolunteers(Mockito.any(), Mockito.any());

        volunteerDirectoryPresenter.onCreate(new ArrayList<>());

        Mockito.verify(view).showError(Mockito.any(),Mockito.anyInt());



    }


}
