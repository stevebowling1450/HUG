package com.interapt.togglit.ui.volunteerDirectory;

import com.interapt.togglit.data.model.lists.Volunteers;
import com.interapt.togglit.data.remote.UserApiService;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by nicholashall on 3/21/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class VolunteerDirectoryApiImpTest {

    private VolunteerDirectoryApiImp volunteerDirectoryApiImp;

    @Mock
    UserApiService userApiService;

    @Mock
    SubscribeOn subscribeOn;

    @Mock
    ObserveOn observeOn;

    @Captor
    private ArgumentCaptor<List<Volunteers>> captor;

    private List<Volunteers> volunteers;

    private ArrayList<Integer> filteredList = new ArrayList<>();



    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        volunteers = new ArrayList<>();
        volunteerDirectoryApiImp = new VolunteerDirectoryApiImp(subscribeOn, observeOn, userApiService);
        Mockito.when(subscribeOn.getScheduler()).thenReturn(Schedulers.immediate());
        Mockito.when(observeOn.getScheduler()).thenReturn(Schedulers.immediate());

    }

    @Test
    public void return_ordered_list_of_volunteers_when_executed(){
        Volunteers volunteer1 = new Volunteers();
        volunteer1.setLastName("A");
        Volunteers volunteer2 = new Volunteers();
        volunteer2.setLastName("B");
        Volunteers volunteer3 = new Volunteers();
        volunteer3.setLastName("C");

        volunteers = Arrays.asList(volunteer2,volunteer3,volunteer1); // unordered

        Mockito.when(userApiService.doGetAllVolunteers()).thenReturn(Observable.just(volunteers));

        TestSubscriber<List<Volunteers>> subscriber = new TestSubscriber<>();
        volunteerDirectoryApiImp.getAllVolunteers(filteredList, subscriber);

        subscriber.awaitTerminalEvent(); //  wait for onComplete or onError
        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(1); // we got one list
        List<Volunteers> returnedVolunteers = subscriber.getOnNextEvents().get(0);

        assertThat(returnedVolunteers.size(), is(3));
        // Check order
        assertThat(returnedVolunteers.get(0).getLastName(), is("A"));
        assertThat(returnedVolunteers.get(1).getLastName(), is("B"));
        assertThat(returnedVolunteers.get(2).getLastName(), is("C"));
    }

    @Test
    public void test_when_error_thrown_passed_to_subscriber() throws Exception {
        Mockito.when(userApiService.doGetAllVolunteers()).thenReturn(Observable.error(new RuntimeException("There was an error!")));

        TestSubscriber<List<Volunteers>> subscriber = new TestSubscriber<>();
        volunteerDirectoryApiImp.getAllVolunteers(filteredList, subscriber);

        subscriber.awaitTerminalEvent(); //  wait for onComplete or onError
        subscriber.assertNotCompleted();
        subscriber.assertError(RuntimeException.class);
        Throwable throwable = subscriber.getOnErrorEvents().get(0);
        assertThat(throwable.getMessage(), is("There was an error!"));
    }
}
