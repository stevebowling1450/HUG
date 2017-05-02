package com.interapt.togglit.ui.progressReport.overview;

import com.interapt.togglit.common.Constants;
import com.interapt.togglit.data.model.Average;
import com.interapt.togglit.data.model.user.Student;
import com.interapt.togglit.data.remote.IDataManager;
import com.interapt.togglit.ui.base.BasePresenter;
import com.interapt.togglit.util.ObserveOn;
import com.interapt.togglit.util.SubscribeOn;

import javax.inject.Inject;

import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by nicholashall on 3/7/17.
 */

public class OverviewFragmentPresenter extends BasePresenter<OverviewFragmentView> {

    private CompositeSubscription mCompositeSubscription;
    private final IDataManager iDataManager;
    private final ObserveOn observeOn;
    private final SubscribeOn subscribeOn;


    @Inject
    public OverviewFragmentPresenter(IDataManager iDataManager, ObserveOn observeOn, SubscribeOn subscribeOn) {
        this.iDataManager = iDataManager;
        this.observeOn = observeOn;
        this.subscribeOn = subscribeOn;
    }

    @Override
    public void attachView(OverviewFragmentView mvpView) {
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


    public void onCreate(Integer studentId) {
        mCompositeSubscription.add(iDataManager.getStudent(studentId)
                .map(Student::getAverage)
                .observeOn(observeOn.getScheduler()).subscribeOn(subscribeOn.getScheduler())
                .subscribe(new Subscriber<Average>() {
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
                    public void onNext(Average average) {
                        getMvpView().setAverage(average);
                        getMvpView().showRefresh(true);
                    }
                }));

    }

}
