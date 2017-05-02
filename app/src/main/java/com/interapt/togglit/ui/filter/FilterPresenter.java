package com.interapt.togglit.ui.filter;

import android.content.Context;

import com.interapt.togglit.common.Constants;
import com.interapt.togglit.data.model.lists.Schools;
import com.interapt.togglit.data.remote.IDataManager;
import com.interapt.togglit.injection.qualifier.ApplicationContext;
import com.interapt.togglit.ui.base.BasePresenter;
import com.interapt.togglit.util.ObserveOn;
import com.interapt.togglit.util.SubscribeOn;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by stevebowling on 3/8/17.
 */

public class FilterPresenter extends BasePresenter<FilterView> {
    @Inject
    @ApplicationContext
    Context mContext;

    private CompositeSubscription mCompositeSubscription;
    private IDataManager iDataManager;

    private final SubscribeOn subscribeOn;
    private final ObserveOn observeOn;

    @Inject
    public FilterPresenter(IDataManager dataManager,SubscribeOn subscribeOn, ObserveOn observeOn) {
        this.iDataManager = dataManager;
        this.subscribeOn = subscribeOn;
        this.observeOn = observeOn;
    }



    @Override
    public void attachView(FilterView mvpView) {
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
        mCompositeSubscription.add(iDataManager.getAllSchools()
                .flatMap(schools -> Observable.from(schools)
                        .filter(school -> true)
                        .toList())
                .observeOn(observeOn.getScheduler()).subscribeOn(subscribeOn.getScheduler())
                .subscribe(new Subscriber<List<Schools>>() {
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
                    public void onNext(List<Schools> schools) {
                        for (int i = 0; i > schools.size(); i++) {
                            if (schools.get(i).getSchoolName() != null) {
                                Collections.sort(schools, (v1, v2)
                                        -> v1.getSchoolName().compareTo(v2.getSchoolName()));
                            }
                        }
                        getMvpView().getSchoolList(schools);
                        getMvpView().showRefresh(false);
                    }
                }));


    }

}
