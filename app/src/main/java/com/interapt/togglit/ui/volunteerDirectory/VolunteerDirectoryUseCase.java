package com.interapt.togglit.ui.volunteerDirectory;

import com.interapt.togglit.data.model.lists.Volunteers;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * Created by nicholashall on 3/21/17.
 */

public interface VolunteerDirectoryUseCase {

    void getAllVolunteers(ArrayList<Integer> filteredList, Subscriber<List<Volunteers>> subscriber);

    void destroy();

}
