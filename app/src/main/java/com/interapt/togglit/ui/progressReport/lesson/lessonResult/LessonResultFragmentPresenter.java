package com.interapt.togglit.ui.progressReport.lesson.lessonResult;

import com.interapt.togglit.common.Constants;
import com.interapt.togglit.data.model.session.Session;
import com.interapt.togglit.data.remote.IDataManager;
import com.interapt.togglit.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by Matthew.Watson on 2/22/17.
 */

public class LessonResultFragmentPresenter extends BasePresenter<LessonResultFragmentView> {
    private CompositeSubscription mCompositeSubscription;
    private final IDataManager mDataManager;


    @Inject
    public LessonResultFragmentPresenter(IDataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void attachView(LessonResultFragmentView mvpView) {
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

    public void doActionGetLessonsForFragment(Integer lessonId, Integer studentId) {
        mCompositeSubscription.add(mDataManager.getStudent(studentId)
                .flatMap(student -> Observable.from(student.getSessions())
                        .filter(sessions -> sessions.getLessonId().equals(lessonId))
                        .toList()
                )
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Session>>() {
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
                    public void onNext(List<Session> sessions) {
                        getMvpView().setFragmentLesson(sessions);
                        getMvpView().showRefresh(false);

                    }
                }));
    }
}