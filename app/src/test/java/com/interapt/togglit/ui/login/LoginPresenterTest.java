package com.interapt.togglit.ui.login;

import com.interapt.togglit.common.ISharedPreferencesManager;
import com.interapt.togglit.data.model.user.Volunteer;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Matthew.Watson on 3/29/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {

    @Mock
    LoginView view;

    @Mock
    IDataManager iDataManager;

    @Mock
    SubscribeOn subscribeOn;

    @Mock
    ObserveOn observeOn;

    @Mock
    ISharedPreferencesManager iSharedPreferencesManager;

    private LoginPresenter presenter;

    @Captor
    private ArgumentCaptor<String> captor;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new LoginPresenter(iDataManager, iSharedPreferencesManager, subscribeOn, observeOn);
        presenter.attachView(view);
        when(subscribeOn.getScheduler()).thenReturn(Schedulers.immediate());
        when(observeOn.getScheduler()).thenReturn(Schedulers.immediate());
    }

    @Test
    public void should_log_in_user_and_go_to_homeScreen() {
        //given
        String userName = "new5@new.com";
        String password = "12345678";
        Volunteer volunteer = new Volunteer();


        //when
        when(iDataManager.getLoginAsVolunteer(userName, password))
                .thenReturn(Observable.just(volunteer));
        when(iDataManager.getSelf()).thenReturn(Observable.just(volunteer));


        //then
        presenter.doLogin(userName, password);


        //if anything before this breaks then the activity will not change.
        verify(view).goToHomeScreen();
    }


    @Test
    public void should_save_token_to_shared_preferences() {
        //given
        Volunteer volunteer = new Volunteer();
        volunteer.setToken("12345678abcdefg");

        //when
        when(iDataManager.getSelf()).thenReturn(Observable.just(volunteer));

        presenter.saveLoggedProfile(volunteer.getToken());

        verify(iSharedPreferencesManager).saveToken(captor.capture());

        //then
        assertThat(captor.getValue(), is(volunteer.getToken()));
    }


    @SuppressWarnings("WrongConstant")
    @Test
    public void should_error_if_incorrect_email_format() {
        String badEmail = "hfdsajhdjlahfjkldsahfjlkz";

        presenter.validateRx(Observable.just(badEmail), Observable.just(anyString()));

        verify(view).setEmailTextError(false);

    }


    @SuppressWarnings("WrongConstant")
    @Test
    public void should_error_if_password_is_fewer_than_six_characters() {
        String badPassword = "12345";

        presenter.validateRx(Observable.just(badPassword), Observable.just(anyString()));

        verify(view).setPasswordTextError(false);

    }


}