package com.interapt.togglit.ui.studentProfile;

import com.interapt.togglit.common.Constants;
import com.interapt.togglit.data.model.user.Student;
import com.interapt.togglit.data.remote.IDataManager;
import com.interapt.togglit.ui.base.BasePresenter;
import com.interapt.togglit.util.ObserveOn;
import com.interapt.togglit.util.SubscribeOn;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by stevebowling on 2/20/17.
 */

public class StudentProfilePresenter extends BasePresenter<StudentProfileView> {

    private final IDataManager iDataManager;
    private CompositeSubscription mCompositeSubscription;
    private final SubscribeOn subscribeOn;
    private final ObserveOn observeOn;

    @Inject
    StudentProfilePresenter(IDataManager iDataManager, SubscribeOn subscribeOn, ObserveOn observeOn) {
        this.iDataManager = iDataManager;
        this.subscribeOn = subscribeOn;
        this.observeOn = observeOn;
    }

    @Override
    public void attachView(StudentProfileView mvpView) {
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

    public void onCreate(int studentId) {
        checkViewAttached();
        getMvpView().showRefresh(true);
        mCompositeSubscription.add(iDataManager.getStudent(studentId)
                .observeOn(observeOn.getScheduler())
                .subscribeOn(subscribeOn.getScheduler())
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
                        getMvpView().getStudent(student);
                        getMvpView().showRefresh(true);

                    }
                }));
    }

    void getStudentVolunteer(int volID) {
        checkViewAttached();
        getMvpView().showRefresh(true);
        mCompositeSubscription.add(iDataManager.getVolunteer(volID)
                .flatMap(volunteer -> Observable.just(volunteer.getPhone()))
                .observeOn(observeOn.getScheduler())
                .subscribeOn(subscribeOn.getScheduler())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e("Bad things happened");
                        getMvpView().showError(e.getMessage(), Constants.LOW_ERROR);
                    }

                    @Override
                    public void onNext(String volunteerPhone) {
                        getMvpView().getVolunteerPhoneNumber(volunteerPhone);
                        getMvpView().showRefresh(false);

                    }
                }));
    }


}

