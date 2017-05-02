package com.interapt.togglit.ui.progressReport;

import com.interapt.togglit.data.model.session.Session;
import com.interapt.togglit.data.model.session.StudentLessonAcumen;
import com.interapt.togglit.data.model.user.Student;
import com.interapt.togglit.data.remote.IDataManager;
import com.interapt.togglit.ui.progressReport.acumen.AcumenFragmentPresenter;
import com.interapt.togglit.ui.progressReport.acumen.AcumenFragmentView;
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
import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

/**
 * Created by nicholashall on 4/3/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class AcumenFragmentPresenterTest {


    private AcumenFragmentPresenter acumenFragmentPresenter;

    @Mock
    ObserveOn observeOn;

    @Mock
    SubscribeOn subscribeOn;

    @Mock
    IDataManager iDataManager;

    @Mock
    AcumenFragmentView view;

    @Captor
    ArgumentCaptor<List<StudentLessonAcumen>> captor;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        acumenFragmentPresenter = new AcumenFragmentPresenter(
                iDataManager,
                observeOn,
                subscribeOn);

        when(subscribeOn.getScheduler()).thenReturn(Schedulers.immediate());
        when(observeOn.getScheduler()).thenReturn(Schedulers.immediate());

        acumenFragmentPresenter.attachView(view);
    }

    @Test
    public void takes_a_student_returns_a_student_lesson_acumen() throws InterruptedException {

        Student student = new Student();
        List<Session> sessions = new ArrayList<>();
        Session session = new Session();
        session.setAcumen(10.0);
        session.setId(2);
        session.setWordMissed(1);
        session.setLessonId(2);
        session.setDate(new Date());
        session.setFirstAttempt(true);
        session.setRecordedBy("Joe");
        session.setSecondAttempt(false);
        session.setStudentId(0);
        session.setStudentMood(3);
        session.setTime(400);
        sessions.add(session);
        student.setSessions(sessions);
        student.setId(0);

        when(iDataManager.getStudent(0)).thenReturn(Observable.just(student));

        acumenFragmentPresenter.onCreate(0);

        Mockito.verify(view,never()).showError(Mockito.anyString());

        Mockito.verify(view).setFragmentSessions(captor.capture());

        assertThat(captor.getValue().get(0).getAttemptOne(), is(10));

    }


    @Test
    public void takes_a_students_acumen_returns_a_student_lesson_acumen_first_attempt() throws InterruptedException {

        Student student = new Student();
        List<Session> sessions = new ArrayList<>();
        Session session = new Session();
        session.setAcumen(10.0);
        session.setId(2);
        session.setWordMissed(1);
        session.setLessonId(2);
        session.setDate(new Date());
        session.setFirstAttempt(true);
        session.setRecordedBy("Joe");
        session.setSecondAttempt(false);
        session.setStudentId(0);
        session.setStudentMood(3);
        session.setTime(400);
        sessions.add(session);
        student.setSessions(sessions);
        student.setId(0);

        when(iDataManager.getStudent(0)).thenReturn(Observable.just(student));

        acumenFragmentPresenter.onCreate(0);

        Mockito.verify(view,never()).showError(Mockito.anyString());

        Mockito.verify(view).setFragmentSessions(captor.capture());

        assertThat(captor.getValue().get(0).getAttemptOne(), is(10));

    }

}
