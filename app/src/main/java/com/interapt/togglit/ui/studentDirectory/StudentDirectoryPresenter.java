package com.interapt.togglit.ui.studentDirectory;

import com.interapt.togglit.common.Constants;
import com.interapt.togglit.data.model.lists.Students;
import com.interapt.togglit.data.remote.IDataManager;
import com.interapt.togglit.ui.base.BasePresenter;
import com.interapt.togglit.util.ObserveOn;
import com.interapt.togglit.util.SubscribeOn;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by stevebowling on 2/14/17.
 */

public class StudentDirectoryPresenter extends BasePresenter<StudentDirectoryView> {
    private CompositeSubscription mCompositeSubscription;
    private final IDataManager iDataManager;
    private final SubscribeOn subscribeOn;
    private final ObserveOn observeOn;

    @Inject
    public StudentDirectoryPresenter(IDataManager iDataManager, SubscribeOn subscribeOn, ObserveOn observeOn) {
        this.iDataManager = iDataManager;
        this.subscribeOn = subscribeOn;
        this.observeOn = observeOn;
    }

    @Override
    public void attachView(StudentDirectoryView mvpView) {
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

    public void onCreate(ArrayList<Integer> filteredList) {
        checkViewAttached();
        mCompositeSubscription.add(iDataManager.getAllStudents()
                .flatMap(students -> Observable.from(students)
                        .filter(student -> student != null && (filteredList.isEmpty() || filteredList.contains(student.getSchoolId())))
                        .toSortedList((students1, students2) -> students1.getLastName().compareTo(students2.getLastName())))
                .observeOn(observeOn.getScheduler())
                .subscribeOn(subscribeOn.getScheduler())
                .subscribe(new Subscriber<List<Students>>() {
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
                    public void onNext(List<Students> students) {
                        getMvpView().setStudentList(students);
                        getMvpView().showRefresh(false);
                    }

                }));
    }
}
