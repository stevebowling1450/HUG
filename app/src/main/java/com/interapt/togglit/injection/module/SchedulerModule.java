package com.interapt.togglit.injection.module;

import com.interapt.togglit.util.ObserveOn;
import com.interapt.togglit.util.SubscribeOn;

import dagger.Module;
import dagger.Provides;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by stevebowling on 3/21/17.
 */

@Module
public class SchedulerModule {

    @Provides
    SubscribeOn providesSubscribeOn() {
        return Schedulers::io;
    }

    @Provides
    ObserveOn providesObserveOn() {
        return AndroidSchedulers::mainThread;
    }

}
