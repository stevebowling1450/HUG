package com.interapt.togglit.injection.component;

import android.app.Application;
import android.content.Context;

import com.interapt.togglit.common.ISharedPreferencesManager;
import com.interapt.togglit.common.SharedPreferencesManager;
import com.interapt.togglit.data.remote.DataManager;
import com.interapt.togglit.data.remote.IDataManager;
import com.interapt.togglit.injection.module.ApplicationModule;
import com.interapt.togglit.injection.module.DirectoryModule;
import com.interapt.togglit.injection.module.NetworkModule;
import com.interapt.togglit.injection.module.SchedulerModule;
import com.interapt.togglit.injection.module.SignInModule;
import com.interapt.togglit.injection.qualifier.ApplicationContext;
import com.interapt.togglit.ui.custom.views.CustomizableButtonView;
import com.interapt.togglit.ui.custom.views.CustomizableTextViewFontRegular;
import com.interapt.togglit.ui.custom.views.CustomizableToolbar;
import com.interapt.togglit.ui.forgotPassword.ForgotPasswordActivity;
import com.interapt.togglit.ui.login.LoginActivity;
import com.interapt.togglit.ui.schoolDirectory.SchoolDirectoryUseCase;
import com.interapt.togglit.ui.splash.SplashActivity;
import com.interapt.togglit.ui.volunteerDirectory.VolunteerDirectoryUseCase;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by miller.barrera on 14/10/2016.
 */

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class, SignInModule.class, SchedulerModule.class, DirectoryModule.class})
public interface ApplicationComponent {


    void inject(LoginActivity loginActivity);

    void inject(ForgotPasswordActivity forgotPasswordActivity);

    void inject(SplashActivity splashActivity);

    void inject(CustomizableButtonView customizableButtonView);

    void inject(CustomizableToolbar customizableToolbar);

    void inject(CustomizableTextViewFontRegular customizableTextViewFontRegular);

    void inject(ISharedPreferencesManager iSharedPreferencesManager);

    @ApplicationContext
    Context context();

    Application application();

    SharedPreferencesManager sharedPreferencesManager();

    ISharedPreferencesManager iSharedPreferencesManager();

    DataManager dataManager();

    IDataManager idataManager();

    SchoolDirectoryUseCase schoolDirectoryUseCase();

    VolunteerDirectoryUseCase volunteerListUseCase();


}
