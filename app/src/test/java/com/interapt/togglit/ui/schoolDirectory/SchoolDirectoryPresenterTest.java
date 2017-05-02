package com.interapt.togglit.ui.schoolDirectory;

import com.interapt.togglit.data.model.lists.Schools;
import com.interapt.togglit.data.model.lists.Volunteers;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import rx.Subscriber;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

/**
 * Created by Matthew.Watson on 3/21/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class SchoolDirectoryPresenterTest {

    @Mock
    SchoolDirectoryUseCase schoolDirectoryUseCase;

    @Mock
    SchoolDirectoryView view;

    @Mock
    IDataManager iDataManager;

    @Mock
    SubscribeOn subscribeOn;

    @Mock
    ObserveOn observeOn;


    @Captor
    private ArgumentCaptor<List<Schools>> captor;

    private SchoolDirectoryPresenter presenter;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new SchoolDirectoryPresenter(schoolDirectoryUseCase);
        presenter.attachView(view);
    }


    @Test
    public void should_pass_schools_to_view() {
//        given
        List<Schools> schoolsList = Arrays.asList(new Schools(), new Schools(), new Schools());

        doAnswer(invocation -> {
            Subscriber<List<Schools>> subscriber = (Subscriber<List<Schools>>) invocation.getArguments()[0];
            subscriber.onNext(schoolsList);
            subscriber.onCompleted();
            return null;
        }).when(schoolDirectoryUseCase).getSchools(any());


//        when
        presenter.onCreate();

//        then
        verify(view).getSchoolList(captor.capture());

        List<Schools> capturedValues = captor.getValue();

        if (capturedValues.size() != schoolsList.size()) throw new AssertionError();

        assertThat(capturedValues.size(), is(schoolsList.size()));

    }

    @Test
    public void should_not_crash_if_no_schools_exist() {

        Mockito.doAnswer(invocation -> {
            Subscriber<List<Volunteers>> subscriber = (Subscriber<List<Volunteers>>) invocation.getArguments()[0];
            subscriber.onNext(Collections.emptyList());
            subscriber.onCompleted();
            return null;
        }).when(schoolDirectoryUseCase).getSchools(Mockito.any());

        presenter.onCreate();
    }

    @SuppressWarnings("WrongConstant")
    @Test
    public void should_show_error() {

        Mockito.doAnswer(invocation -> {
            Subscriber<List<Schools>> subscriber = (Subscriber<List<Schools>>) invocation.getArguments()[0];

            subscriber.onError(new RuntimeException("There was an ERROR"));
            return null;
        }).when(schoolDirectoryUseCase).getSchools(Mockito.any());

        presenter.onCreate();

        verify(view).showError(any(), anyInt());
    }
}