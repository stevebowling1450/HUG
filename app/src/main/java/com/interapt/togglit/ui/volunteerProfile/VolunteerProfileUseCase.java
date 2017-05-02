package com.interapt.togglit.ui.volunteerProfile;

import com.interapt.togglit.data.model.user.Volunteer;

import rx.Observable;

/**
 * Created by nicholashall on 3/24/17.
 */

public interface VolunteerProfileUseCase {

    Observable<Volunteer> getVolunteer(Integer id);

    void destroy();

}
