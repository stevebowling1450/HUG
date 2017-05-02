package com.interapt.togglit.ui.lessonList;

import com.interapt.togglit.data.model.lists.Lessons;
import com.interapt.togglit.data.model.lists.LessonsNoImage;
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
 * Created by Matthew.Watson on 3/24/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class LessonListPresenterTest {

    @Mock
    LessonListView view;

    @Mock
    IDataManager iDataManager;

    @Mock
    SubscribeOn subscribeOn;

    @Mock
    ObserveOn observeOn;

    @Captor
    private ArgumentCaptor<List<LessonsNoImage>> captor;

    private LessonListPresenter presenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new LessonListPresenter(iDataManager, subscribeOn, observeOn);
        presenter.attachView(view);
        when(subscribeOn.getScheduler()).thenReturn(Schedulers.immediate());
        when(observeOn.getScheduler()).thenReturn(Schedulers.immediate());

    }

    @Test
    public void should_pass_lessons_to_view() throws Exception {
        //given
        List<Lessons> lessons = Arrays.asList(new Lessons(), new Lessons(), new Lessons());
        lessons.get(0).setId(1);
        lessons.get(1).setId(2);
        lessons.get(2).setId(3);

        //when
        when(iDataManager.getAllLessons()).thenReturn(Observable.just(lessons));

        // then
        presenter.onCreate();

        verify(view).setLesson(captor.capture());

        assertThat(captor.getValue().size(), is(lessons.size()));
    }

    @Test
    public void should_sort_lessons_by_id() throws Exception {
        //given
        List<Lessons> lessons = Arrays.asList(new Lessons(), new Lessons(), new Lessons());
        lessons.get(0).setId(2);
        lessons.get(1).setId(3);
        lessons.get(2).setId(1);

        //when
        when(iDataManager.getAllLessons()).thenReturn(Observable.just(lessons));

        // then
        presenter.onCreate();

        verify(view).setLesson(captor.capture());

        List<List<LessonsNoImage>> shouldBeOrderedLessons = captor.getAllValues();

        assertThat(shouldBeOrderedLessons.size(), is(1));
        assertThat(shouldBeOrderedLessons.get(0).get(0).getId(), is(1));
        assertThat(shouldBeOrderedLessons.get(0).get(1).getId(), is(2));
        assertThat(shouldBeOrderedLessons.get(0).get(2).getId(), is(3));
    }

    @SuppressWarnings("WrongConstant")
    @Test
    public void should_show_error() {
        // ERROR
        when(iDataManager.getAllLessons()).thenReturn(Observable.error(new RuntimeException("There was an error")));

        // then
        presenter.onCreate();

        verify(view).showError(anyString(), anyInt());
    }

}