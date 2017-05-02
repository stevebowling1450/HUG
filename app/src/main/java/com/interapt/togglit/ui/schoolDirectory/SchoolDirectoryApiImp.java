package com.interapt.togglit.ui.schoolDirectory;

import com.interapt.togglit.data.model.lists.Schools;
import com.interapt.togglit.data.remote.UserApiService;
import com.interapt.togglit.util.ObserveOn;
import com.interapt.togglit.util.SubscribeOn;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by Matthew.Watson on 3/21/17.
 */

public class SchoolDirectoryApiImp implements SchoolDirectoryUseCase {
    private final UserApiService userApiService;
    private final SubscribeOn subscribeOn;
    private final ObserveOn observeOn;
    private Subscription subscription = Subscriptions.empty();

    public SchoolDirectoryApiImp(UserApiService userApiService, SubscribeOn subscribeOn, ObserveOn observeOn) {
        this.userApiService = userApiService;
        this.subscribeOn = subscribeOn;
        this.observeOn = observeOn;
    }


    @Override
    public void getSchools(Subscriber<List<Schools>> subscriber) {
        subscription = userApiService.doGetAllSchools()
                .flatMap(schools -> Observable.from(schools)
                        .filter(school -> true)
                        .toSortedList((v1, v2) -> v1.getSchoolName().compareTo(v2.getSchoolName())))
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
