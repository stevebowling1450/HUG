package com.interapt.togglit.ui.volunteerProfile;

import com.interapt.togglit.data.model.user.Volunteer;
import com.interapt.togglit.ui.base.MvpView;

/**
 * Created by Matthew.Watson on 2/9/17.
 */

public interface VolunteerProfileView extends MvpView {

void setVolunteer(Volunteer volunteer);

}
