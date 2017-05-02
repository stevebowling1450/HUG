package com.interapt.togglit.ui.homeScreenPods;

import com.interapt.togglit.ui.base.BasePresenter;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by miller.barrera on 14/10/2016.
 */

public class HomeScreenPodsPresenter extends BasePresenter<HomeScreenPodsView> {

    private CompositeSubscription mCompositeSubscription;


    @Inject
    public HomeScreenPodsPresenter() {

    }

    @Override
    public void attachView(HomeScreenPodsView mvpView) {
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

}
