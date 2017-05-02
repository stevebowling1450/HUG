package com.interapt.togglit.ui.login;

import com.interapt.togglit.data.model.user.Volunteer;
import com.interapt.togglit.ui.base.MvpView;

/**
 * Created by miller.barrera on 15/11/2016.
 */

public interface LoginView extends MvpView {
    void signInSuccessful();

    void getLogInUserData(Volunteer volunteer);

    void setEmailTextError(Boolean aBoolean);

    void doLogInProcess(Boolean aBoolean);

    void setPasswordTextError(Boolean aBoolean);

    void goToHomeScreen();
}
