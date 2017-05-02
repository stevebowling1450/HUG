package com.interapt.togglit.ui.settings;

import com.interapt.togglit.data.model.user.Volunteer;
import com.interapt.togglit.ui.base.MvpView;

/**
 * Created by miller.barrera on 24/11/2016.
 */

public interface SettingsView extends MvpView {
    void showLoggedUserProfileInfo(Volunteer loggedUser);

    void showLogInActionItem();

}
