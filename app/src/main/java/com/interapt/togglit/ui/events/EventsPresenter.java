package com.interapt.togglit.ui.events;

import android.content.Context;

import com.interapt.togglit.common.Constants;
import com.interapt.togglit.data.model.events.Events;
import com.interapt.togglit.data.remote.IDataManager;
import com.interapt.togglit.injection.qualifier.ApplicationContext;
import com.interapt.togglit.ui.base.BasePresenter;
import com.interapt.togglit.util.ObserveOn;
import com.interapt.togglit.util.SubscribeOn;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by miller.barrera on 10/01/2017.
 */

public class EventsPresenter extends BasePresenter<EventsView> {

    private CompositeSubscription mCompositeSubscription;
    private final IDataManager mDataManager;
    private final SubscribeOn subscribeOn;
    private final ObserveOn observeOn;

    @Inject
    @ApplicationContext
    Context mContext;


    @Inject
    public EventsPresenter(IDataManager mDataManager, SubscribeOn subscribeOn, ObserveOn observeOn) {
        this.mDataManager = mDataManager;
        this.subscribeOn = subscribeOn;
        this.observeOn = observeOn;
    }

    @Override
    public void attachView(EventsView mvpView) {
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


    public void onCreate(ArrayList<Integer> filteredList) {
        int i = 0;
        checkViewAttached();
        getMvpView().showRefresh(true);
        mCompositeSubscription.add(mDataManager.getAllEvents()
                .flatMap(events -> Observable.from(events)
                        .filter(event -> event != null && (filteredList.isEmpty() || filteredList.contains(event.getSchoolId())))
                        .toSortedList((e1, e2) -> e1.getStartDate().compareTo(e2.getStartDate())))
                .observeOn(observeOn.getScheduler())
                .subscribeOn(subscribeOn.getScheduler())
                .subscribe(new Subscriber<List<Events>>() {
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
                    public void onNext(List<Events> eventsList) {
                        if (getMvpView() != null) {
                            if (eventsList != null && !eventsList.isEmpty()) {
                                getMvpView().getEventsList(eventsList);
                            }
                            getMvpView().showRefresh(false);
                        }
                    }
                }));
    }

}