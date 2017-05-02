package com.interapt.togglit.ui.schoolDirectory;

import com.interapt.togglit.data.model.lists.Schools;
import com.interapt.togglit.data.remote.UserApiService;
import com.interapt.togglit.util.ObserveOn;
import com.interapt.togglit.util.SubscribeOn;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;


/**
 * Created by Matthew.Watson on 3/23/17.
 */
public class SchoolDirectoryApiImpTest {

    private SchoolDirectoryApiImp schoolDirectoryApiImp;

    @Mock
    UserApiService userApiService;

    @Mock
    SchoolDirectoryUseCase schoolDirectoryUseCase;

    @Mock
    SubscribeOn subscribeOn;

    @Mock
    ObserveOn observeOn;

    @Mock
    SchoolDirectoryView view;

    @Captor
    private ArgumentCaptor<List<Schools>> captor;

    private List<Schools> schoolsList;

    private SchoolDirectoryPresenter presenter;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        schoolsList = new ArrayList<>();
        presenter = new SchoolDirectoryPresenter(schoolDirectoryUseCase);
        presenter.attachView(view);
        schoolDirectoryApiImp = new SchoolDirectoryApiImp(userApiService, subscribeOn, observeOn);
        Mockito.when(subscribeOn.getScheduler()).thenReturn(Schedulers.immediate());
        Mockito.when(observeOn.getScheduler()).thenReturn(Schedulers.immediate());

    }


    @Test
    public void should_get_all_schools_and_pass_to_presenter() throws Exception {

        //given
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
        verify(view).getSchoolList(schoolsList);

    }

    @Test
    public void sorts_list_of_schools_by_name() {
        Schools schools1 = new Schools();
        schools1.setSchoolName("A");
        Schools schools2 = new Schools();
        schools2.setSchoolName("B");
        Schools schools3 = new Schools();
        schools3.setSchoolName("C");

        schoolsList = Arrays.asList(schools2, schools3, schools1);

        Mockito.when(userApiService.doGetAllSchools()).thenReturn(Observable.just(schoolsList));

        TestSubscriber<List<Schools>> subscriber = new TestSubscriber<>();
        schoolDirectoryApiImp.getSchools(subscriber);

        subscriber.awaitTerminalEvent();
        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);
        List<Schools> shouldBeOrderedSchools = subscriber.getOnNextEvents().get(0);

        assertThat(shouldBeOrderedSchools.size(), is(3));
        assertThat(shouldBeOrderedSchools.get(0).getSchoolName(), is("A"));
        assertThat(shouldBeOrderedSchools.get(1).getSchoolName(), is("B"));
        assertThat(shouldBeOrderedSchools.get(2).getSchoolName(), is("C"));
    }
}