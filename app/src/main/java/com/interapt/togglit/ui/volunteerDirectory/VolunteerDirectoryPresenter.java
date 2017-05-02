package com.interapt.togglit.ui.volunteerDirectory;

import com.interapt.togglit.common.Constants;
import com.interapt.togglit.data.model.lists.Volunteers;
import com.interapt.togglit.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by nicholashall on 2/13/17.
 */

public class VolunteerDirectoryPresenter extends BasePresenter<VolunteerDirectoryView> {
    private CompositeSubscription mCompositeSubscription;
    private final VolunteerDirectoryUseCase volunteerDirectoryUseCase;



    @Inject
    public VolunteerDirectoryPresenter(VolunteerDirectoryUseCase volunteerDirectoryUseCase) {
        this.volunteerDirectoryUseCase = volunteerDirectoryUseCase;
    }


    @Override
    public void attachView(VolunteerDirectoryView mvpView) {
        super.attachView(mvpView);
        if (mCompositeSubscription == null || mCompositeSubscription.isUnsubscribed()) {
            mCompositeSubscription = new CompositeSubscription();
        }
    }

    @Override
    public void detachView() {
        super.detachView();
        volunteerDirectoryUseCase.destroy();
        if (mCompositeSubscription != null) {
            mCompositeSubscription.clear();
        }
    }


    public void onCreate(ArrayList<Integer> filteredList) {
        checkViewAttached();
        volunteerDirectoryUseCase.getAllVolunteers(filteredList,new Subscriber<List<Volunteers>>() {
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
            public void onNext(List<Volunteers> volunteers) {
                getMvpView().getVolunteerList(volunteers);
                getMvpView().showRefresh(false);

            }
        });

    }
}