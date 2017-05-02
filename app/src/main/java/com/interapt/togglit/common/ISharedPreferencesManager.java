package com.interapt.togglit.common;

import com.interapt.togglit.data.model.user.Volunteer;

/**
 * Created by Matthew.Watson on 3/2/17.
 */

public interface ISharedPreferencesManager {

    void saveLoggedUserProfile(Volunteer loggedUserProfile);

    void savePersona(Volunteer persona);

    Volunteer getLoggedUserProfile();

    void removeLoggedUserProfile();

    String getToken();

    void saveToken(String token);

    int getPersona();
}
