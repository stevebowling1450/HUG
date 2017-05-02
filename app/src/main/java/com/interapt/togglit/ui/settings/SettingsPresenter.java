package com.interapt.togglit.ui.settings;

import com.interapt.togglit.common.SharedPreferencesManager;
import com.interapt.togglit.data.model.user.Volunteer;
import com.interapt.togglit.ui.base.BasePresenter;

import javax.inject.Inject;

import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by miller.barrera on 24/11/2016.
 */

public class SettingsPresenter extends BasePresenter<SettingsView> {
    @Inject
    SharedPreferencesManager mSharePreferencesManager;

    private CompositeSubscription mCompositeSubscription;

    @Inject
    public SettingsPresenter() {
    }

    @Override
    public void attachView(SettingsView mvpView) {
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

    public void validateAuthenticationSession() {
        Volunteer loggedUser = mSharePreferencesManager.getLoggedUserProfile();
        if (loggedUser != null) {
            getMvpView().showLoggedUserProfileInfo(loggedUser);
        } else {
            getMvpView().showLogInActionItem();
        }
    }


    //Use this method in log out session
    public void logout() {
        removeLoggedProfile();
    }


    public void removeLoggedProfile() {
        Schedulers.io().createWorker().schedule(() -> {
            mSharePreferencesManager.removeLoggedUserProfile();
        });
    }

}

