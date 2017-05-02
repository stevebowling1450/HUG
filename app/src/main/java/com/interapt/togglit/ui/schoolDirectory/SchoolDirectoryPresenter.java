package com.interapt.togglit.ui.schoolDirectory;

import com.interapt.togglit.common.Constants;
import com.interapt.togglit.data.model.lists.Schools;
import com.interapt.togglit.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by nicholashall on 2/22/17.
 */

public class SchoolDirectoryPresenter extends BasePresenter<SchoolDirectoryView> {

    private CompositeSubscription mCompositeSubscription;
    private final SchoolDirectoryUseCase schoolDirectoryUseCase;

    @Inject
    public SchoolDirectoryPresenter(SchoolDirectoryUseCase schoolDirectoryUseCase) {
        this.schoolDirectoryUseCase = schoolDirectoryUseCase;
    }

    @Override
    public void attachView(SchoolDirectoryView mvpView) {
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
        checkViewAttached();
        schoolDirectoryUseCase.getSchools
                (new Subscriber<List<Schools>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showError(e.getMessage(), Constants.LOW_ERROR);
                        Timber.e("Bad things happened");
                        e.printStackTrace();                    }

                    @Override
                    public void onNext(List<Schools> schools) {
                        getMvpView().getSchoolList(schools);
                        getMvpView().showRefresh(false);
                    }
                });
    }
}
