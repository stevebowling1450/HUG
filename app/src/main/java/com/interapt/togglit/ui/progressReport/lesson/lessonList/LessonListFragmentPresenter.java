package com.interapt.togglit.ui.progressReport.lesson.lessonList;

import com.interapt.togglit.common.Constants;
import com.interapt.togglit.data.model.lists.LessonsNoImage;
import com.interapt.togglit.data.model.session.Session;
import com.interapt.togglit.data.remote.IDataManager;
import com.interapt.togglit.ui.base.BasePresenter;
import com.interapt.togglit.util.ObserveOn;
import com.interapt.togglit.util.SubscribeOn;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by Matthew.Watson on 2/21/17.
 */

public class LessonListFragmentPresenter extends BasePresenter<LessonListFragmentView> {

    private CompositeSubscription mCompositeSubscription;
    private final IDataManager mDataManager;
    private final SubscribeOn subscribeOn;
    private final ObserveOn observeOn;


    @Inject
    public LessonListFragmentPresenter(IDataManager mDataManager, SubscribeOn subscribeOn, ObserveOn observeOn) {
        this.mDataManager = mDataManager;
        this.subscribeOn = subscribeOn;
        this.observeOn = observeOn;
    }

    @Override
    public void attachView(LessonListFragmentView mvpView) {
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
        mCompositeSubscription.add(mDataManager.getStudent(studentId)
                .flatMap(student -> Observable.from(student.getSessions()))
                .map(Session::getLessonId)
                .distinct()
                .toSortedList()
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Integer>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e("Bad Things Happened", e);

                    }

                    @Override
                    public void onNext(List<Integer> lessonIds) {
                        getMvpView().showLessons(lessonIds);
                        getMvpView().showRefresh(false);

                    }
                }));
    }

    public void getLessons(List<Integer> studentLessons){
        mCompositeSubscription.add(mDataManager.getAllLessonsNoImage()
                .flatMap(lessons -> Observable.from(lessons)
                        .filter(lessonsList -> studentLessons.contains(lessonsList.getId()))
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
