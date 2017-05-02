package com.interapt.togglit.ui.progressReport;

import com.interapt.togglit.common.Constants;
import com.interapt.togglit.data.model.user.Student;
import com.interapt.togglit.data.remote.IDataManager;
import com.interapt.togglit.ui.base.BasePresenter;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by Matthew.Watson on 2/20/17.
 */

public class ProgressReportPresenter extends BasePresenter<ProgressReportView> {

    private CompositeSubscription mCompositeSubscription;
    private IDataManager mDataManager;

    @Inject
    public ProgressReportPresenter(IDataManager dataManager) {
        this.mDataManager = dataManager;
    }

    @Override
    public void attachView(ProgressReportView mvpView) {
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
        checkViewAttached();
        getMvpView().showRefresh(true);
        mCompositeSubscription.add(mDataManager.getStudent(studentId)
                .flatMap(students -> Observable.just(students)
                        .filter(student -> student.getId() == studentId))
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Student>() {
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
                    public void onNext(Student student) {
                        getMvpView().setStudent(student);
                        getMvpView().showRefresh(false);

                    }
                }));
    }

}
