package com.interapt.togglit.injection.module;

import com.interapt.togglit.data.remote.UserApiService;
import com.interapt.togglit.ui.schoolDirectory.SchoolDirectoryApiImp;
import com.interapt.togglit.ui.schoolDirectory.SchoolDirectoryUseCase;
import com.interapt.togglit.ui.volunteerDirectory.VolunteerDirectoryApiImp;
import com.interapt.togglit.ui.volunteerDirectory.VolunteerDirectoryUseCase;
import com.interapt.togglit.util.ObserveOn;
import com.interapt.togglit.util.SubscribeOn;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Matthew.Watson on 3/21/17.
 */


@Module
public class DirectoryModule {

    @Provides
    @Singleton
    SchoolDirectoryUseCase providesSchoolDirectoryUseCase(UserApiService userApiService, SubscribeOn subscribeOn, ObserveOn observeOn) {
        return new SchoolDirectoryApiImp(userApiService, subscribeOn, observeOn);
    }

    @Provides
    @Singleton
    VolunteerDirectoryUseCase providesVolunteersListUseCase(UserApiService userApiService, SubscribeOn subscribeOn, ObserveOn observeOn) {
        return new VolunteerDirectoryApiImp(subscribeOn, observeOn, userApiService);


    }
}
