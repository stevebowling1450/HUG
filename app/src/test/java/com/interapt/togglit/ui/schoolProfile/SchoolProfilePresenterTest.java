package com.interapt.togglit.ui.schoolProfile;

import com.interapt.togglit.data.model.user.School;
import com.interapt.togglit.data.remote.IDataManager;
import com.interapt.togglit.util.ObserveOn;
import com.interapt.togglit.util.SubscribeOn;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rx.Observable;
import rx.schedulers.Schedulers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by stevebowling on 4/6/17.
 */
public class SchoolProfilePresenterTest {

    @Mock
    SchoolProfileView view;

    @Mock
    IDataManager iDataManager;

    @Mock
    SubscribeOn subscribeOn;

    @Mock
    ObserveOn observeOn;

    @Captor
    private ArgumentCaptor<School> captor;

    private SchoolProfilePresenter schoolProfilePresenter;


    int schoolId = 1;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        // Create new presenter and attach the view.
        schoolProfilePresenter = new SchoolProfilePresenter(iDataManager,subscribeOn,observeOn);
        schoolProfilePresenter.attachView(view);

        when(subscribeOn.getScheduler()).thenReturn(Schedulers.immediate());
        when(observeOn.getScheduler()).thenReturn(Schedulers.immediate());
    }

    @Test
    public void should_pass_school_view() throws Exception {
        //given
        School school = new School();
        school.setId(1);
        //when
        when(iDataManager.getSchool(schoolId)).thenReturn(Observable.just(school));
        //then
        schoolProfilePresenter.onCreate(schoolId);
        verify(view).setSchool(captor.capture());
        assertThat(captor.getValue().getId(), is(school.getId()));
    }

    @SuppressWarnings("WrongConstant")
    @Test
    public void shouldShowError(){
        // ERROR

        when(iDataManager.getSchool(schoolId)).thenReturn(Observable.error(new RuntimeException("There was an error")));

        // then
        schoolProfilePresenter.onCreate(schoolId);
        verify(view).showError(anyString(), anyInt());
    }

}