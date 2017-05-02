package com.interapt.togglit.ui.lessonList;

import com.interapt.togglit.common.Constants;
import com.interapt.togglit.data.model.lists.LessonsNoImage;
import com.interapt.togglit.data.remote.IDataManager;
import com.interapt.togglit.ui.base.BasePresenter;
import com.interapt.togglit.util.ObserveOn;
import com.interapt.togglit.util.SubscribeOn;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by Matthew.Watson on 2/16/17.
 */

public class LessonListPresenter extends BasePresenter<LessonListView> {

    private CompositeSubscription mCompositeSubscription;
    private final IDataManager mIDataManager;
    private final SubscribeOn subscribeOn;
    private final ObserveOn observeOn;


    @Inject
    public LessonListPresenter(IDataManager mDataManager, SubscribeOn subscribeOn, ObserveOn observeOn) {
        this.mIDataManager = mDataManager;
        this.subscribeOn = subscribeOn;
        this.observeOn = observeOn;
    }

    @Override
    public void attachView(LessonListView mvpView) {
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

    public void onCreate() {
        mCompositeSubscription.add(mIDataManager.getAllLessonsNoImage()
                .flatMap(lessons -> Observable.from(lessons)
                        .filter(lessonsList -> true)
                        .toSortedList((v1, v2) -> v1.getId().compareTo(v2.getId())))
                .observeOn(observeOn.getScheduler())
                .subscribeOn(subscribeOn.getScheduler())
                .subscribe(new Subscriber<List<LessonsNoImage>>() {
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
                    public void onNext(List<LessonsNoImage> lessons) {
                        Collections.sort(lessons, (v1, v2)
                                -> v1.getLessonIdentifier().compareTo(v2.getLessonIdentifier()));

                        getMvpView().setLesson(lessons);
                        getMvpView().showRefresh(false);
                    }
                }));
    }
}
