package com.interapt.togglit.ui.forgotPassword;

import android.content.Context;

import com.interapt.togglit.common.Constants;
import com.interapt.togglit.common.SharedPreferencesManager;
import com.interapt.togglit.data.remote.IDataManager;
import com.interapt.togglit.ui.base.BasePresenter;
import com.interapt.togglit.util.StringUtil;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by miller.barrera on 16/11/2016.
 */

public class ForgotPasswordPresenter extends BasePresenter<ForgotPasswordView> {

    private CompositeSubscription mCompositeSubscription;
    private final IDataManager mDataManager;
    private Context mContext;

    @Inject
    SharedPreferencesManager mSharedPreferencesManager;

    @Inject
    public ForgotPasswordPresenter(IDataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void attachView(ForgotPasswordView mvpView) {
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



    void callForgotPassword(String email) {
        checkViewAttached();
        getMvpView().showRefresh(true);
        mDataManager.ForgotPassword(email)
                .subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {
                getMvpView().showSuccessForgotPassword();
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().showError(e.getMessage(), Constants.LOW_ERROR);
                Timber.e("Bad things happened");
//                e.printStackTrace();

            }

            @Override
            public void onNext(Object o) {
                // NEVER CALLED
            }
        });
    }

    /**
     * Validate the fields used to create a account.
     */
    public void validateRx(Observable<CharSequence> observableTextEmail) {
        checkViewAttached();

        //Observer to validate email
        Observable<Boolean> observableEmail = observableTextEmail.map(charSequence -> StringUtil.getEmailPattern()
                .matcher(charSequence).matches());

        mCompositeSubscription.add(observableEmail.map(aBoolean -> aBoolean).subscribe(aBoolean -> {
            getMvpView().isValidEmail(aBoolean);
        }));



    }

}
