package com.interapt.togglit.ui.filter;

import com.interapt.togglit.data.model.lists.Schools;
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
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by stevebowling on 3/27/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class FilterPresenterTest {

    @Mock
    FilterView view;

    @Mock
    IDataManager iDataManager;

    @Mock
    SubscribeOn subscribeOn;

    @Mock
    ObserveOn observeOn;

    @Captor
    private ArgumentCaptor<List<Schools>> captor;

    private FilterPresenter filterPresenter;



    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        filterPresenter = new FilterPresenter(iDataManager,subscribeOn,observeOn);
        filterPresenter.attachView(view);
        when(subscribeOn.getScheduler()).thenReturn(Schedulers.immediate());
        when(observeOn.getScheduler()).thenReturn(Schedulers.immediate());
    }

    @Test
    public void shouldPassSchoolsView() throws Exception {
        //given
        List<Schools>schools = Arrays.asList(new Schools());
        //when
        when(iDataManager.getAllSchools()).thenReturn(Observable.just(schools));
        //then
        filterPresenter.onCreate();
        verify(view).getSchoolList(captor.capture());
        assertThat(captor.getValue().size(), is(schools.size()));
    }

    @SuppressWarnings("WrongConstant")
    @Test
    public void shouldShowError(){
        //given
        List<Schools>schools = Arrays.asList(new Schools());

        // ERROR
        when(iDataManager.getAllSchools()).thenReturn(Observable.error(new RuntimeException("There was an error")));


        // then
        filterPresenter.onCreate();
        verify(view).showError(anyString(), anyInt());
    }

}