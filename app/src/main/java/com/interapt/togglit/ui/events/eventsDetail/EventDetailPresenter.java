package com.interapt.togglit.ui.events.eventsDetail;

import com.interapt.togglit.data.model.events.Events;
import com.interapt.togglit.data.remote.IDataManager;
import com.interapt.togglit.ui.base.BasePresenter;
import com.interapt.togglit.util.ObserveOn;
import com.interapt.togglit.util.SubscribeOn;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by miller.barrera on 13/01/2017.
 */

public class EventDetailPresenter extends BasePresenter<EventDetailView> {
    private final IDataManager iDataManager;
    private CompositeSubscription mCompositeSubscription;
    private final SubscribeOn subscribeOn;
    private final ObserveOn observeOn;

    @Inject
    public EventDetailPresenter(IDataManager iDataManager, SubscribeOn subscribeOn, ObserveOn observeOn) {
        this.iDataManager = iDataManager;
        this.subscribeOn = subscribeOn;
        this.observeOn = observeOn;
    }

    @Override
    public void attachView(EventDetailView mvpView) {
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

    public void onCreate() {
        checkViewAttached();
        getMvpView().showRefresh(true);
        mCompositeSubscription.add(iDataManager.getAllEvents()
                .observeOn(observeOn.getScheduler())
                .subscribeOn(subscribeOn.getScheduler())
                .subscribe(new Subscriber<List<Events>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Events> eventsList) {
                        getMvpView().getEvent(eventsList);
                        getMvpView().showRefresh(false);

                    }
                }));
    }
}




