package com.interapt.togglit.ui.progressReport.acumen;

import com.interapt.togglit.data.model.session.Session;
import com.interapt.togglit.data.model.session.StudentLessonAcumen;
import com.interapt.togglit.data.remote.IDataManager;
import com.interapt.togglit.ui.base.BasePresenter;
import com.interapt.togglit.util.ObserveOn;
import com.interapt.togglit.util.SubscribeOn;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.observables.GroupedObservable;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by nicholashall on 3/3/17.
 */

public class AcumenFragmentPresenter extends BasePresenter<AcumenFragmentView> {

    private CompositeSubscription mCompositeSubscription;
    private final IDataManager mDataManager;
    private final ObserveOn observeOn;
    private final SubscribeOn subscribeOn;


    @Inject
    public AcumenFragmentPresenter(IDataManager mDataManager, ObserveOn observeOn, SubscribeOn subscribeOn) {
        this.mDataManager = mDataManager;
        this.observeOn = observeOn;
        this.subscribeOn = subscribeOn;
    }

    @Override
    public void attachView(AcumenFragmentView mvpView) {
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
                .flatMap(student -> Observable.from(student.getSessions())
                        .groupBy(Session::getLessonId)
                        .flatMap(new Func1<GroupedObservable<Integer, Session>, Observable<StudentLessonAcumen>>() {
                            @Override
                            public Observable<StudentLessonAcumen> call(GroupedObservable<Integer, Session> integerSessionsGroupedObservable) {
                                return integerSessionsGroupedObservable.toList().map(sessions -> {
                                    Collections.sort(sessions, (o1, o2) -> o1.getFirstAttempt().compareTo(o2.getSecondAttempt()));
                                    Integer first = sessions.get(0).getAcumen();
                                    String firstDate = sessions.get(0).getDate().toString();
                                    Integer firstFluency = sessions.get(0).getTime();
                                    Integer firstMood = sessions.get(0).getStudentMood();
                                    Integer second = 0;
                                    String secondDate = "";
                                    Integer secondFluency = 0;
                                    Integer secondMood = 0;
                                    String lessonIdentifier =sessions.get(0).getLessonIdentifier();
                                    if (sessions.size() > 1) {
                                        second = sessions.get(1).getAcumen();
                                        secondDate = sessions.get(1).getDate().toString();
                                        secondFluency = sessions.get(1).getTime();
                                        secondMood = sessions.get(1).getStudentMood();

                                    }

                                    return new StudentLessonAcumen(String.valueOf(integerSessionsGroupedObservable.getKey()),
                                            first, second, firstDate, secondDate, firstFluency, secondFluency, firstMood, secondMood,lessonIdentifier);
                                });
                            }
                        }).toList()
                )
                .observeOn(observeOn.getScheduler()).subscribeOn(subscribeOn.getScheduler())
                .subscribe(new Subscriber<List<StudentLessonAcumen>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e("onErrorHit", e);
                        getMvpView().showError("There was an error getting the Acumen");
                    }

                    @Override
                    public void onNext(List<StudentLessonAcumen> acumens) {
                        getMvpView().setFragmentSessions(acumens);
                        getMvpView().showRefresh(false);
                    }
                }));

    }

//    public void getLessons(){
//        mCompositeSubscription.add(mDataManager.getAllLessonsNoImage()
//                .observeOn(observeOn.getScheduler())
//                .subscribeOn(subscribeOn.getScheduler())
//                .subscribe(new Subscriber<List<LessonsNoImage>>() {
//                    @Override
//                    public void onCompleted() {
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        getMvpView().showError(e.getMessage(), Constants.LOW_ERROR);
//                        Timber.e("Bad things happened");
//                        e.printStackTrace();
//
//                    }
//
//                    @Override
//                    public void onNext(List<LessonsNoImage> lessons) {
//                        getMvpView().setLesson(lessons);
//                        getMvpView().showRefresh(false);
//                    }
//                }));
//    }
}