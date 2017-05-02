package com.interapt.togglit.ui.volunteerDirectory;

import com.interapt.togglit.data.model.lists.Volunteers;
import com.interapt.togglit.data.remote.UserApiService;
import com.interapt.togglit.util.ObserveOn;
import com.interapt.togglit.util.SubscribeOn;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by nicholashall on 3/21/17.
 */

public class VolunteerDirectoryApiImp implements VolunteerDirectoryUseCase {

    private final SubscribeOn subscribeOn;
    private final ObserveOn observeOn;
    private final UserApiService userApiService;

    private Subscription subscription = Subscriptions.empty();

    public VolunteerDirectoryApiImp(SubscribeOn subscribeOn, ObserveOn observeOn, UserApiService userApiService) {
        this.subscribeOn = subscribeOn;
        this.observeOn = observeOn;
        this.userApiService = userApiService;
    }

    @Override
    public void getAllVolunteers(ArrayList<Integer> filteredList, Subscriber<List<Volunteers>> subscriber) {
                subscription = userApiService.doGetAllVolunteers()
                .flatMap(volunteers -> Observable.from(volunteers)
                        .filter(vol -> vol != null && (filteredList.isEmpty() || vol.isVolunteerForSchool(filteredList)))
                        .toSortedList((v1, v2) -> v1.getLastName().compareTo(v2.getLastName())))
                .observeOn(observeOn.getScheduler()).subscribeOn(subscribeOn.getScheduler())
                .subscribe(subscriber);
    }

    @Override
    public void destroy() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
