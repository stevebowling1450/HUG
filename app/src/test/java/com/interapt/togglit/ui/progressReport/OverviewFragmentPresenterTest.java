package com.interapt.togglit.ui.progressReport;

import com.interapt.togglit.data.model.Average;
import com.interapt.togglit.data.model.user.Student;
import com.interapt.togglit.data.remote.IDataManager;
import com.interapt.togglit.ui.progressReport.overview.OverviewFragmentPresenter;
import com.interapt.togglit.ui.progressReport.overview.OverviewFragmentView;
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
 * Created by nicholashall on 3/27/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class OverviewFragmentPresenterTest {

    private OverviewFragmentPresenter overviewFragmentPresenter;

    @Mock
    ObserveOn observeOn;

    @Mock
    SubscribeOn subscribeOn;

    @Mock
    IDataManager iDataManager;

    @Mock
    OverviewFragmentView view;

    @Captor
    ArgumentCaptor<Average> captor;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        overviewFragmentPresenter = new OverviewFragmentPresenter(iDataManager, observeOn, subscribeOn);
        Mockito.when(subscribeOn.getScheduler()).thenReturn(Schedulers.immediate());
        Mockito.when(observeOn.getScheduler()).thenReturn(Schedulers.immediate());
        overviewFragmentPresenter.attachView(view);

    }


    @Test
    public void should_return_average_given_to_it_from_the_view() {

        Student student = new Student();



        Mockito.when(iDataManager.getStudent(0)).thenReturn(Observable.just(student));


        overviewFragmentPresenter.onCreate(0);


        Mockito.verify(view).setAverage(captor.capture());

        Average capturedValues = captor.getValue();



        assertThat(capturedValues, is(student.getAverage()));
    }


    @Test
    public void should_return_error_if_given_one() throws Exception {

        Mockito.when(iDataManager.getStudent(0)).thenReturn(Observable.error(new RuntimeException("There was an error!")));


        overviewFragmentPresenter.onCreate(0);

    }
}