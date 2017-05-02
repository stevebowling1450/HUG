package com.interapt.togglit.common;

import android.support.annotation.IntDef;

/**
 * Created by miller.barrera on 18/10/2016.
 */

public class Constants {


    public static final int LOW_ERROR = 111;


    @IntDef({LOW_ERROR})
    public @interface ErrorType {
    }

    //Symbols
    public static final String SYMBOL_VERTICAL_LINE = " | ";
    public static final String SYMBOL_HYPHEN_MINUS = " - ";
    public static final String SYMBOL_COMMA = " , ";
    public static final String BLANK_SPACE = " ";

    //HockeyApp AppID
    public static final String HONKEY_APP_ID = "d82cbd5a125949f6a0527658fc9fdb2b";


    //Intent extras keys

    public static final String LIST_EVENT_PARCELABLE_OBJECT = "events_list_parcelable_object";
    public static final String SELECTED_EVENT = "selected_event";
    public static final String SELECTED_EVENT_POSITION = "selected_event_position";
    public static final String LIST_FILTER_EVENTS_PARCELABLE_OBJECT = "list_filter_events";
    public static final String LIST_FILTERABLE_EVENTS_PARCELABLE_OBJECT = "list_filterable_events";
    public static final String IS_ALL_EVENTS_SELECTED = "is_all_events_selected";


    //sharepreferences keys
    public static final String FIREBASE_USER_PROFILE = "user_profile";
    public static final String LOGGED_USER_PROFILE = "logged_user_profile";
    public static final String TOKEN = "token";
    public static final String APP_SETTINGS = "app_settings";
    public static final String PERSONA = "persona";


}