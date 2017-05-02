package com.interapt.togglit.ui.lessonDetail;

import com.interapt.togglit.data.model.user.Lesson;
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
 * Created by nicholashall on 3/27/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class LessonDetailPresenterTest {

    private LessonDetailPresenter lessonDetailPresenter;

    @Mock
    SubscribeOn subscribeOn;

    @Mock
    ObserveOn observeOn;

    @Mock
    LessonDetailView view;

    @Mock
    IDataManager iDataManager;

    @Captor
    ArgumentCaptor<Lesson> lessonArgumentCaptor;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        lessonDetailPresenter = new LessonDetailPresenter(iDataManager,observeOn, subscribeOn);
        Mockito.when(subscribeOn.getScheduler()).thenReturn(Schedulers.immediate());
        Mockito.when(observeOn.getScheduler()).thenReturn(Schedulers.immediate());
        lessonDetailPresenter.attachView(view);
    }

    @Test
    public void should_return_the_given_lesson(){
        Lesson lesson = new Lesson();

        Mockito.when(iDataManager.getLesson(0)).thenReturn(Observable.just(lesson));

        lessonDetailPresenter.onCreate(0);

        Mockito.verify(view).setLesson(lessonArgumentCaptor.capture());

        Lesson capturedValues = lessonArgumentCaptor.getValue();

        assertThat(capturedValues,is(lesson));
    }

    @Test
    public void should_return_error_if_given_one() throws Exception {

        Mockito.when(iDataManager.getLesson(0)).thenReturn(Observable.error(new RuntimeException("There was an error!")));


        lessonDetailPresenter.onCreate(0);

    }

}
