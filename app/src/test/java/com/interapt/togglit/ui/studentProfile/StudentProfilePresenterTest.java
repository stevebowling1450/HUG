package com.interapt.togglit.ui.studentProfile;

import com.interapt.togglit.common.ISharedPreferencesManager;
import com.interapt.togglit.data.model.user.Student;
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
import rx.Observable;
import rx.schedulers.Schedulers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by stevebowling on 3/23/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class StudentProfilePresenterTest {

    @Mock
    StudentProfileView view;

    @Mock
    IDataManager iDataManager;

    @Mock
    SubscribeOn subscribeOn;

    @Mock
    ObserveOn observeOn;

    @Mock
    ISharedPreferencesManager iSharedPreferencesManager;

    @Captor
    private ArgumentCaptor<Student> captor;

    private StudentProfilePresenter studentProfilePresenter;

    int studentId = 0;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        // Create new presenter and attach the view.
        studentProfilePresenter = new StudentProfilePresenter(iDataManager,subscribeOn,observeOn);
        studentProfilePresenter.attachView(view);

        when(subscribeOn.getScheduler()).thenReturn(Schedulers.immediate());
        when(observeOn.getScheduler()).thenReturn(Schedulers.immediate());
    }

    @Test
    public void shouldPassStudentView() throws Exception {
        //given
        Student student = new Student();
        student.setFirstName("Steve-0");
        student.setId(0);

        //when
        when(iDataManager.getStudent(0)).thenReturn(Observable.just(student));
        //then
        studentProfilePresenter.onCreate(studentId);
        verify(view).getStudent(captor.capture());
        assertThat(captor.getValue().getId(), is(student.getId()));

    }

    @SuppressWarnings("WrongConstant")
    @Test
    public void shouldShowError(){
        // ERROR

        when(iDataManager.getStudent(studentId)).thenReturn(Observable.error(new RuntimeException("There was an error")));

        // then
        studentProfilePresenter.onCreate(studentId);
        verify(view).showError(anyString(), anyInt());
    }

}