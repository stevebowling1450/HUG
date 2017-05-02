package com.interapt.togglit.data.remote;

/**
 * Created by miller.barrera on 11/11/2016.
 */

public class ApiConstants {

    public static final String SOLIDUS_SYMBOL = "/";
    public static final String PATH_ID = "{id}";

    public static final String HUG_BASE_URL = "https://hug-backend.herokuapp.com";
    public static final String HUG_DEV_URL = "https://hug-backend-dev.herokuapp.com";

    public static final String HUG_LOGIN = "login";

    //Params
    public static final String PARAM_VOLUNTEER_EMAIL = "email";
    public static final String PARAM_VOLUNTEER_PASSWORD = "password";

    public static final String PARAM_ALL = "all/";

    public static final String PARAM_VOLUNTEERS = "volunteers";
    public static final String PARAM_VOLUNTEER = "volunteer";
    public static final String PARAM_LESSONS = "lessons";
    public static final String PARAM_SCHOOLS = "schools";
    public static final String PARAM_STUDENTS = "students";
    public static final String PARAM_AVERAGES = "averages";
    public static final String PARAM_SELF = "self";
    public static final String PARAM_LESSONS_NO_IMAGE = "lessons_no_image";
    public static final String PARAM_UNDONE_LESSONS = "undone_lesson_list";
    public static final String PARAM_EVENTS = "events";
    public static final String PARAM_FORGOT_PASSWORD = "updatepass";
    public static final String PARAM_SESSIONS = "sessions";


    public static final String HUG_VOLUNTEER_LOGIN_ENDPOINT = HUG_BASE_URL + SOLIDUS_SYMBOL + PARAM_VOLUNTEER + SOLIDUS_SYMBOL + HUG_LOGIN;
//    public static final String HUG_VOLUNTEER_LOGIN_ENDPOINT = HUG_DEV_URL + SOLIDUS_SYMBOL + PARAM_VOLUNTEER + SOLIDUS_SYMBOL + HUG_LOGIN;


    public static final String HUG_SINGLE_VOLUNTEER_ENDPOINT =
            HUG_BASE_URL + SOLIDUS_SYMBOL + PARAM_VOLUNTEER + SOLIDUS_SYMBOL + PATH_ID;
    public static final String HUG_SINGLE_LESSON_ENDPOINT =
            HUG_BASE_URL + SOLIDUS_SYMBOL + PARAM_LESSONS + SOLIDUS_SYMBOL + PATH_ID;
    public static final String HUG_SINGLE_SCHOOL_ENDPOINT =
            HUG_BASE_URL + SOLIDUS_SYMBOL + PARAM_SCHOOLS + SOLIDUS_SYMBOL + PATH_ID;
    public static final String HUG_SINGLE_STUDENT_ENDPOINT =
            HUG_BASE_URL + SOLIDUS_SYMBOL + PARAM_STUDENTS + SOLIDUS_SYMBOL + PATH_ID;
    public static final String HUG_SINGLE_AVERAGE_ENDPOINT =
            HUG_BASE_URL + SOLIDUS_SYMBOL + PARAM_AVERAGES + SOLIDUS_SYMBOL + PATH_ID;
    public static final String HUG_SINGLE_EVENT_ENDPOINT =
            HUG_BASE_URL + SOLIDUS_SYMBOL + PARAM_EVENTS + SOLIDUS_SYMBOL + PATH_ID;


    public static final String HUG_ALL_VOLUNTEERS_ENDPOINT =
            HUG_BASE_URL + SOLIDUS_SYMBOL + PARAM_ALL + PARAM_VOLUNTEERS;
    public static final String HUG_ALL_LESSONS_ENDPOINT =
            HUG_BASE_URL + SOLIDUS_SYMBOL + PARAM_LESSONS;
    public static final String HUG_ALL_STUDENTS_ENDPOINT =
            HUG_BASE_URL + SOLIDUS_SYMBOL + PARAM_STUDENTS;
    public static final String HUG_ALL_SCHOOLS_ENDPOINT =
            HUG_BASE_URL + SOLIDUS_SYMBOL + PARAM_SCHOOLS;
    public static final String HUG_SELF_ENDPOINT =
            HUG_BASE_URL + SOLIDUS_SYMBOL + PARAM_SELF;
    public static final String HUG_LESSONS_NO_IMAGE_ENDPOINT =
            HUG_BASE_URL + SOLIDUS_SYMBOL + PARAM_LESSONS_NO_IMAGE;
    public static final String HUG_ALL_EVENTS_ENDPOINT =
            HUG_BASE_URL + SOLIDUS_SYMBOL + PARAM_EVENTS;
    public static final String HUG_LESSONS_UNDONE_ENDPOINT =
            HUG_BASE_URL + SOLIDUS_SYMBOL + PARAM_UNDONE_LESSONS + SOLIDUS_SYMBOL + PATH_ID;
    public static final String HUG_FORGOT_PASSWORD_ENDPOINT =
            HUG_BASE_URL + SOLIDUS_SYMBOL + PARAM_FORGOT_PASSWORD;
    public static final String HUG_SESSIONS_POST =
            HUG_BASE_URL + SOLIDUS_SYMBOL + PARAM_SESSIONS;


}
