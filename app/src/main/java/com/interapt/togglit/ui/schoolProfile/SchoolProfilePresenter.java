package com.interapt.togglit.ui.schoolProfile;

import com.interapt.togglit.common.Constants;
import com.interapt.togglit.data.model.user.School;
import com.interapt.togglit.data.remote.IDataManager;
import com.interapt.togglit.ui.base.BasePresenter;
import com.interapt.togglit.util.ObserveOn;
import com.interapt.togglit.util.SubscribeOn;

import javax.inject.Inject;

import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by nicholashall on 2/23/17.
 */

public class SchoolProfilePresenter extends BasePresenter<SchoolProfileView> {

    private final IDataManager mDataManager;
    private CompositeSubscription mCompositeSubscription;
    private final SubscribeOn subscribeOn;
    private final ObserveOn observeOn;

    @Inject
    public SchoolProfilePresenter(IDataManager mDataManager, SubscribeOn subscribeOn, ObserveOn observeOn) {
        this.mDataManager = mDataManager;
        this.subscribeOn = subscribeOn;
        this.observeOn = observeOn;

    }


    @Override
    public void attachView(SchoolProfileView mvpView) {
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


    public void onCreate(Integer schoolID) {
        checkViewAttached();
        getMvpView().showRefresh(true);
        mCompositeSubscription.add(mDataManager.getSchool(schoolID)
                .observeOn(observeOn.getScheduler())
                .subscribeOn(subscribeOn.getScheduler())
                .subscribe(new Subscriber<School>() {
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
                    public void onNext(School school) {
                        getMvpView().setSchool(school);
                        getMvpView().showRefresh(false);

                    }
                }));
    }
}
