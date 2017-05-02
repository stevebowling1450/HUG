package com.interapt.togglit.ui.volunteerProfile;


import com.interapt.togglit.common.Constants;
import com.interapt.togglit.data.model.user.Volunteer;
import com.interapt.togglit.data.remote.IDataManager;
import com.interapt.togglit.ui.base.BasePresenter;
import com.interapt.togglit.util.ObserveOn;
import com.interapt.togglit.util.SubscribeOn;

import javax.inject.Inject;

import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by Matthew.Watson on 2/9/17.
 */

public class VolunteerProfilePresenter extends BasePresenter<VolunteerProfileView> {

    private final IDataManager iDataManager;
    private CompositeSubscription mCompositeSubscription;

    private final ObserveOn observeOn;
    private final SubscribeOn subscribeOn;

    @Inject
    public VolunteerProfilePresenter(IDataManager iDataManager, ObserveOn observeOn, SubscribeOn subscribeOn) {
        this.iDataManager = iDataManager;
        this.observeOn = observeOn;
        this.subscribeOn = subscribeOn;
    }


    @Override
    public void attachView(VolunteerProfileView mvpView) {
        super.attachView(mvpView);
        if (mCompositeSubscription == null || mCompositeSubscription.isUnsubscribed()) {
            mCompositeSubscription = new CompositeSubscription();
        }
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mCompositeSubscription != null) {
            mCompositeSubscription.clear();
        }
    }

    public void onCreate(Integer volunteerID) {
        checkViewAttached();
        getMvpView().showRefresh(true);
        mCompositeSubscription.add(iDataManager.getVolunteer(volunteerID)
                .observeOn(observeOn.getScheduler()).subscribeOn(subscribeOn.getScheduler())
                .subscribe(new Subscriber<Volunteer>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showError(e.getMessage(), Constants.LOW_ERROR);
                        Timber.e("Bad things happened");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Volunteer volunteer) {
                        getMvpView().setVolunteer(volunteer);
                        getMvpView().showRefresh(false);

                    }
                }));
    }
}