package com.interapt.togglit.injection.module;

import android.app.Application;
import android.content.Context;

import com.interapt.togglit.injection.qualifier.ApplicationContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by miller.barrera.
 */
@Module
public class ApplicationModule {

    protected final Application mApplication;

    public ApplicationModule(Application application) {
        this.mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }
}
