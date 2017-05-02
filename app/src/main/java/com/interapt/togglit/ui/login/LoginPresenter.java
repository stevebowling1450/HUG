package com.interapt.togglit.ui.login;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.interapt.togglit.common.Constants;
import com.interapt.togglit.common.ISharedPreferencesManager;
import com.interapt.togglit.data.model.error.ErrorParserResponse;
import com.interapt.togglit.data.model.user.Volunteer;
import com.interapt.togglit.data.remote.IDataManager;
import com.interapt.togglit.ui.base.BasePresenter;
import com.interapt.togglit.util.ObserveOn;
import com.interapt.togglit.util.StringUtil;
import com.interapt.togglit.util.SubscribeOn;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by miller.barrera on 15/11/2016.
 */

public class LoginPresenter extends BasePresenter<LoginView> {

    private static final String TAG = LoginPresenter.class.getSimpleName();
    private final IDataManager mDataManager;
    private final ISharedPreferencesManager mSharedPreferencesManager;
    private CompositeSubscription mCompositeSubscription;
    private final SubscribeOn subscribeOn;
    private final ObserveOn observeOn;

    @Inject
    public LoginPresenter(IDataManager dataManager,
                          ISharedPreferencesManager sharedPreferencesManager, SubscribeOn subscribeOn, ObserveOn observeOn) {
        this.mDataManager = dataManager;
        this.mSharedPreferencesManager = sharedPreferencesManager;
        this.subscribeOn = subscribeOn;
        this.observeOn = observeOn;
    }

    @Override
    public void attachView(LoginView mvpView) {
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
     * Save the logged user profile data  into preferences.
     */
    public void saveLoggedProfile(String token) {
        checkViewAttached();
        getMvpView().showRefresh(true);
        mSharedPreferencesManager.saveToken(token);
        mCompositeSubscription.add(mDataManager.getSelf()
                .filter(volunteer -> volunteer != null)
                .observeOn(observeOn.getScheduler())
                .subscribeOn(subscribeOn.getScheduler())
                .subscribe(new Subscriber<Volunteer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Volunteer volunteer) {
                        mSharedPreferencesManager.saveLoggedUserProfile(volunteer);
                        mSharedPreferencesManager.savePersona(volunteer);

                    }
                }));
    }

    public void doLogin(String email, String password) {
        checkViewAttached();
        getMvpView().showRefresh(true);
        mCompositeSubscription.add(mDataManager.getLoginAsVolunteer(email, password)
                .filter(authentication -> authentication != null)
                .observeOn(observeOn.getScheduler())
                .subscribeOn(subscribeOn.getScheduler())
                .subscribe(authentication -> {
                            LoginPresenter.this.getMvpView().showRefresh(false);
                            LoginPresenter.this.saveLoggedProfile(authentication.getToken());
                            LoginPresenter.this.getMvpView().goToHomeScreen();
                        },
                        throwable -> {
                            Log.e(TAG, "ERROR LOGGING IN", throwable);
                            if (throwable instanceof HttpException) {
                                ResponseBody body = ((HttpException) throwable).response().errorBody();
                                Gson gson = new Gson();
                                TypeAdapter<ErrorParserResponse> adapter =
                                        gson.getAdapter(ErrorParserResponse.class);
                                try {
                                    ErrorParserResponse errorParser = adapter.fromJson(body.string());
                                    Timber.d("Error Response :" + errorParser.getError());
                                    LoginPresenter.this.getMvpView()
                                            .showError(errorParser.getError(), Constants.LOW_ERROR);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            if (LoginPresenter.this.getMvpView() != null) {
                                LoginPresenter.this.getMvpView().showRefresh(false);
                            }
                        }));
    }

    /**
     * Validate the fields used to create a account.
     */
    public void validateRx(Observable<CharSequence> observableTextEmail,
                           Observable<CharSequence> observableTextPassword) {
        checkViewAttached();

        //Observer to validate email
        Observable<Boolean> observableEmail = observableTextEmail.map(
                charSequence -> StringUtil.getEmailPattern().matcher(charSequence).matches());

        Observable<Boolean> observablePassword =
                observableTextPassword.map(charSequence -> charSequence.length() >= 6);

        mCompositeSubscription.add(observableEmail.map(aBoolean -> aBoolean).subscribe(aBoolean -> {
            getMvpView().setEmailTextError(aBoolean);
        }));

        mCompositeSubscription.add(
                observablePassword.map(aBoolean -> aBoolean).subscribe(aBoolean -> {
                    LoginPresenter.this.getMvpView().setPasswordTextError(aBoolean);
                }));

        //Observer to enable/disable button create account
        mCompositeSubscription.add(
                Observable.combineLatest(observableEmail, observablePassword, (a, b) -> a && b)
                        .distinctUntilChanged()
                        .subscribe(aBoolean -> getMvpView().doLogInProcess(aBoolean)));
    }
}
