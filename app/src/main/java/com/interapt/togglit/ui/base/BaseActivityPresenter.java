package com.interapt.togglit.ui.base;

import android.content.Context;

import com.interapt.togglit.common.Constants;
import com.interapt.togglit.common.SharedPreferencesManager;
import com.interapt.togglit.data.remote.IDataManager;
import com.interapt.togglit.hockeyapp.CustomCrashManagerListener;
import com.interapt.togglit.injection.qualifier.ApplicationContext;

import net.hockeyapp.android.CrashManager;

import javax.inject.Inject;

import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by miller.barrera on 20/11/2016.
 */

public class BaseActivityPresenter extends BasePresenter<BaseActivityView> {

    private CompositeSubscription mCompositeSubscription;
    private final IDataManager mDataManager;

    @Inject
    SharedPreferencesManager mSharedPreferencesManager;

    @Inject
    @ApplicationContext
    Context mContext;

    @Inject
    public BaseActivityPresenter(IDataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void attachView(BaseActivityView mvpView) {
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

    /**
     * HockeyApp Check for crashes
     **/
    public void checkForCrashes() {
        Schedulers.io().createWorker().schedule(() -> CrashManager.register(mContext, Constants.HONKEY_APP_ID, new CustomCrashManagerListener()));
    }

}
