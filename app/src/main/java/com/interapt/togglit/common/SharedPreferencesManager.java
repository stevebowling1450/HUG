package com.interapt.togglit.common;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.interapt.togglit.data.model.user.Volunteer;

import javax.inject.Singleton;

import timber.log.Timber;

/**
 * Created by miller.barrera
 */
@Singleton
public class SharedPreferencesManager implements ISharedPreferencesManager {

    private final SharedPreferences mSharedPreferences;

    public SharedPreferencesManager(SharedPreferences sharedPreferences) {
        this.mSharedPreferences = sharedPreferences;
    }

    public void saveLoggedUserProfile(Volunteer loggedUserProfile) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(loggedUserProfile);
        editor.putString(Constants.LOGGED_USER_PROFILE, jsonFavorites);
        Timber.d("Logged user: ",jsonFavorites);
        editor.apply();
    }



    @Override
    public void savePersona(Volunteer persona) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(Constants.PERSONA, persona.getPersona());
        editor.apply();
    }

    public int getPersona() {
        return mSharedPreferences.getInt("persona",0);

    }

    public Volunteer getLoggedUserProfile() {
        Gson gson = new Gson();
        String json = mSharedPreferences.getString(Constants.LOGGED_USER_PROFILE, "");
        return gson.fromJson(json, Volunteer.class);
    }


    public void removeLoggedUserProfile() {
        mSharedPreferences.edit().remove("token").apply();
    }

    public String getToken() {
        return mSharedPreferences.getString(Constants.TOKEN, null);
    }

    public void saveToken(String token) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(Constants.TOKEN, token);
        editor.apply();
    }
}
