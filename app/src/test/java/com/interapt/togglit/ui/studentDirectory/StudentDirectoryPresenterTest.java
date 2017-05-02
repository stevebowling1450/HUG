package com.interapt.togglit.ui.studentDirectory;

import com.interapt.togglit.data.model.lists.Students;
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

import java.util.ArrayList;
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
 * Created by stevebowling on 3/21/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class StudentDirectoryPresenterTest {


    @Mock
    StudentDirectoryView view;

    @Mock
    IDataManager iDataManager;

    @Mock
    SubscribeOn subscribeOn;

    @Mock
    ObserveOn observeOn;

    @Captor
    private ArgumentCaptor<List<Students>> captor;

    private StudentDirectoryPresenter studentDirectoryPresenter;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        studentDirectoryPresenter = new StudentDirectoryPresenter(iDataManager, subscribeOn, observeOn);
        studentDirectoryPresenter.attachView(view);
        when(subscribeOn.getScheduler()).thenReturn(Schedulers.immediate());
        when(observeOn.getScheduler()).thenReturn(Schedulers.immediate());

    }

    @Test
    public void shouldPassStudentsView() throws Exception {
        //given
        List<Students> students = Arrays.asList(new Students());

        //when
        when(iDataManager.getAllStudents()).thenReturn(Observable.just(students));

       // then
        studentDirectoryPresenter.onCreate(new ArrayList<>());

        verify(view).setStudentList(captor.capture());

        assertThat(captor.getValue().size(), is(students.size()));
    }

    @Test
    public void shouldFilterSchools() throws Exception{
        //Given
        ArrayList<Integer> schools = new ArrayList<>();
        schools.add(1);

        List<Students> students = Arrays.asList(new Students(),new Students(),new Students());
        students.get(0).setLastName("Steve");
        students.get(1).setLastName("Steve");
        students.get(2).setLastName("Steve");
        students.get(0).setSchoolId(1);
        students.get(1).setSchoolId(1);
        students.get(2).setSchoolId(2);
        //when

        when(iDataManager.getAllStudents()).thenReturn(Observable.just(students));

        //then
        studentDirectoryPresenter.onCreate(schools);

        verify(view).setStudentList(captor.capture());


        assertThat(captor.getValue().size(),is(2));

    }

    @SuppressWarnings("WrongConstant")
    @Test
    public void shouldShowError(){
        //given

        List<Students> students = Arrays.asList(new Students());

        // ERROR

        when(iDataManager.getAllStudents()).thenReturn(Observable.error(new RuntimeException("There was an error")));

        // then
        studentDirectoryPresenter.onCreate(new ArrayList<>());
        verify(view).showError(anyString(), anyInt());
    }
}