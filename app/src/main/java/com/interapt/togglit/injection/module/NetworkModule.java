package com.interapt.togglit.injection.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.interapt.togglit.common.ISharedPreferencesManager;
import com.interapt.togglit.common.SharedPreferencesManager;
import com.interapt.togglit.data.remote.ApiConstants;
import com.interapt.togglit.data.remote.DataManager;
import com.interapt.togglit.data.remote.IDataManager;
import com.interapt.togglit.data.remote.SessionRequestInterceptor;
import com.interapt.togglit.data.remote.UserApiService;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by miller.barrera.
 */
@Module
public class NetworkModule {

    protected final String PREF_NAME = "preferences";

    public NetworkModule() {
    }

    @Singleton
    @Provides
    SharedPreferences provideSharedPreferences(Application application) {
        return application.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    @Singleton
    @Provides
    SharedPreferencesManager providesSharedPreferencesManager(
            SharedPreferences sharedPreferences) {
        return new SharedPreferencesManager(sharedPreferences);
    }

    @Singleton
    @Provides
    ISharedPreferencesManager provideISharedPreferences(
            SharedPreferencesManager sharedPreferencesManager) {
        return sharedPreferencesManager;
    }

    @Singleton
    @Provides
    Gson providesGSON() {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
    }

    @Singleton
    @Provides
    SessionRequestInterceptor providesSessionRequestInterceptor(
            ISharedPreferencesManager sharedPreferencesManager) {
        return new SessionRequestInterceptor(sharedPreferencesManager);
    }

    @Provides
    @Singleton
    OkHttpClient providesOkHttp(
            SessionRequestInterceptor sessionRequestInterceptor) {
        HttpLoggingInterceptor log =
                new HttpLoggingInterceptor(); //print to log, body of requested response
        log.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder() //connects to server
                .connectTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(sessionRequestInterceptor)
                .addInterceptor(log)
                .build();

        //add cacheing here
    }

    @Provides
    @Singleton
    @Named("RxJava")
    CallAdapter.Factory providesRxCallAdapter() {
        return RxJavaCallAdapterFactory.create();
    }

    @Provides
    @Singleton
    @Named("Gson")
    Converter.Factory providesGsonConverter(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @Singleton
    Retrofit providesRetrofit(@Named("RxJava") CallAdapter.Factory rxAdapter,
                              @Named("Gson") Converter.Factory gsonConvertor, OkHttpClient okHttpClient) {
        return new Retrofit.Builder().baseUrl(ApiConstants.HUG_BASE_URL)
                .addConverterFactory(gsonConvertor)
                .addCallAdapterFactory(rxAdapter)
                .client(okHttpClient)
                .build();
    }

    @Singleton
    @Provides
    UserApiService userTokenApiService(Retrofit retrofit) {
        return retrofit.create(UserApiService.class);
    }

    @Provides
    @Singleton
    IDataManager providesIDataManager(DataManager dataManager) {
        return dataManager;
    }

    @Provides
    @Singleton
    DataManager providesDataManager(UserApiService userApiService) {
        return new DataManager(userApiService);
    }
}
