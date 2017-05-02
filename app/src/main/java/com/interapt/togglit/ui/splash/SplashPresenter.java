package com.interapt.togglit.ui.splash;

import com.interapt.togglit.ui.base.BasePresenter;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

public class SplashPresenter extends BasePresenter<SplashView> {


    private CompositeSubscription mCompositeSubscription;

    @Inject
    public SplashPresenter() {

    }

    @Override
    public void attachView(SplashView mvpView) {
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

    public void goToLogin() {
        checkViewAttached();
        getMvpView().goToLogin();

    }

}