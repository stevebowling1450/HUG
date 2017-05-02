package com.interapt.togglit.ui.updateProgress;

import com.interapt.togglit.common.Constants;
import com.interapt.togglit.data.model.lists.LessonsUndone;
import com.interapt.togglit.data.remote.IDataManager;
import com.interapt.togglit.ui.base.BasePresenter;
import com.interapt.togglit.util.ObserveOn;
import com.interapt.togglit.util.SubscribeOn;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by stevebowling on 3/6/17.
 */

public class UpdateProgressPresenter extends BasePresenter<UpdateProgressView> {

    private final IDataManager iDataManager;
    private CompositeSubscription mCompositeSubscription;
    private final SubscribeOn subscribeOn;
    private final ObserveOn observeOn;

    @Inject
    UpdateProgressPresenter(IDataManager iDataManager, SubscribeOn subscribeOn, ObserveOn observeOn) {
        this.iDataManager = iDataManager;
        this.subscribeOn = subscribeOn;
        this.observeOn = observeOn;
    }


    @Override
    public void attachView(UpdateProgressView mvpView) {
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
        mCompositeSubscription.add(iDataManager.getUndoneLessons(studentId)
                .observeOn(observeOn.getScheduler())
                .subscribeOn(subscribeOn.getScheduler())
                .subscribe(new Subscriber<List<LessonsUndone>>() {
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
                    public void onNext(List<LessonsUndone> lessonsUndones) {
                        Collections.sort(lessonsUndones, (v1, v2)
                                -> v1.getLessonIdentifier().compareTo(v2.getLessonIdentifier()));
                        getMvpView().setLesson(lessonsUndones);
                        getMvpView().showRefresh(false);

                    }
                }));
    }

    void callCreateSession(Date date, Integer studentId, Integer studentMood, Integer wordsMissed, Integer lessonId,
                           Integer userId, String recordedBy, Integer time) {
        checkViewAttached();
        getMvpView().showRefresh(true);
        iDataManager.CreateSession(date, studentId, studentMood, wordsMissed, lessonId, userId, recordedBy, time).subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {
                getMvpView().showSnackbar();
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
}
