package com.interapt.togglit.ui.updateProgress;

import com.interapt.togglit.data.model.lists.LessonsUndone;
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
import java.util.Date;
import java.util.List;

import rx.Completable;
import rx.Observable;
import rx.schedulers.Schedulers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by stevebowling on 4/4/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class UpdateProgressPresenterTest {
    @Mock
    UpdateProgressView view;

    @Mock
    IDataManager iDataManager;

    @Mock
    SubscribeOn subscribeOn;

    @Mock
    ObserveOn observeOn;


    @Captor
    private ArgumentCaptor<List<LessonsUndone>> lessonsCaptor;

    private UpdateProgressPresenter updateProgressPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        updateProgressPresenter = new UpdateProgressPresenter(iDataManager, subscribeOn, observeOn);
        updateProgressPresenter.attachView(view);
        when(subscribeOn.getScheduler()).thenReturn(Schedulers.immediate());
        when(observeOn.getScheduler()).thenReturn(Schedulers.immediate());

    }

    @Test
    public void getLessonsNotDoneByStudent() throws Exception {
        //given
        List<LessonsUndone> undone = Arrays.asList(new LessonsUndone());

        //when
        when(iDataManager.getUndoneLessons(1)).thenReturn(Observable.just(undone));
        //then

        updateProgressPresenter.onCreate(1);
        verify(view).setLesson(lessonsCaptor.capture());
        assertThat(lessonsCaptor.getValue().size(), is(undone.size()));

    }

    @SuppressWarnings("WrongConstant")
    @Test
    public void shouldShowError(){
        //given

        List<LessonsUndone> undone = Arrays.asList(new LessonsUndone());

        // ERROR

        when(iDataManager.getUndoneLessons(1)).thenReturn(Observable.error(new RuntimeException("There was an error")));

        // then
        updateProgressPresenter.onCreate(1);
        verify(view).showError(anyString(), anyInt());
    }



    @Test
    public void callCreateSession() throws Exception {
        //Given
        Date date = new Date();
        //when
        when(iDataManager.CreateSession(date,1,1,
               1,1,1,"",1)).thenReturn(Completable.complete());

        //then
        updateProgressPresenter.callCreateSession(date,1,1,
                1,1,1,"",1);
        verify(view).showSnackbar();
    }

    @SuppressWarnings("WrongConstant")
    @Test
    public void ShowError(){
        //given
        Date date = new Date();
        // ERROR
        when(iDataManager.CreateSession(date,1,1,
                1,1,1,"",1)).thenReturn(Completable.error(new RuntimeException("There was an error")));
        // then
        updateProgressPresenter.callCreateSession(date,1,1,
                1,1,1,"",1);
        verify(view).showError(anyString(), anyInt());
    }

}