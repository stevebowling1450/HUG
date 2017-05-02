package com.interapt.togglit.data.remote;

import com.interapt.togglit.data.model.Average;
import com.interapt.togglit.data.model.events.Events;
import com.interapt.togglit.data.model.forgotPassword.ForgotPassword;
import com.interapt.togglit.data.model.lists.Lessons;
import com.interapt.togglit.data.model.lists.LessonsNoImage;
import com.interapt.togglit.data.model.lists.LessonsUndone;
import com.interapt.togglit.data.model.lists.Schools;
import com.interapt.togglit.data.model.lists.Students;
import com.interapt.togglit.data.model.lists.Volunteers;
import com.interapt.togglit.data.model.session.CreateSessionRequest;
import com.interapt.togglit.data.model.user.Lesson;
import com.interapt.togglit.data.model.user.School;
import com.interapt.togglit.data.model.user.Student;
import com.interapt.togglit.data.model.user.Volunteer;

import java.util.Date;
import java.util.List;

import rx.Completable;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by miller.barrera on 18/10/2016.
 */

public class DataManager implements IDataManager {

    private UserApiService mUserTokenApiService;

    public DataManager(UserApiService userTokenApiService) {
        this.mUserTokenApiService = userTokenApiService;
    }

    public Observable<Volunteer> getVolunteer(Integer id) {
        Timber.d(ApiConstants.HUG_SINGLE_VOLUNTEER_ENDPOINT);
        return mUserTokenApiService.doVolunteer(id);
    }

    public Observable<Lesson> getLesson(Integer id) {
        Timber.d(ApiConstants.HUG_SINGLE_LESSON_ENDPOINT);
        return mUserTokenApiService.doLesson(id);
    }

    public Observable<School> getSchool(Integer id) {
        Timber.d(ApiConstants.HUG_SINGLE_SCHOOL_ENDPOINT);
        return mUserTokenApiService.doSchool(id);
    }

    public Observable<Student> getStudent(Integer id) {
        Timber.d(ApiConstants.HUG_SINGLE_STUDENT_ENDPOINT);
        return mUserTokenApiService.doStudent(id);
    }

    public Observable<Average> getAverage(Integer id) {
        Timber.d(ApiConstants.HUG_SINGLE_AVERAGE_ENDPOINT);
        return mUserTokenApiService.doAverage(id);
    }

    public Observable<Events> getEvent(Integer id) {
        Timber.d(ApiConstants.HUG_SINGLE_EVENT_ENDPOINT);
        return mUserTokenApiService.doSingleEvent(id);
    }

    public Observable<List<LessonsUndone>> getUndoneLessons(Integer id) {
        Timber.d(ApiConstants.HUG_SINGLE_VOLUNTEER_ENDPOINT);
        return mUserTokenApiService.doUndoneLessons(id);
    }

    public Observable<Volunteer> getSelf() {
        Timber.d(ApiConstants.HUG_SELF_ENDPOINT);
        return mUserTokenApiService.doGetSelf();
    }

    public Observable<Volunteer> getLoginAsVolunteer(String email, String password) {
        Timber.d("Login in Url %s ", ApiConstants.HUG_VOLUNTEER_LOGIN_ENDPOINT);
        return mUserTokenApiService.doVolunteerLogin(email, password);
    }

    public Observable<List<Volunteers>> getAllVolunteers() {
        Timber.d(ApiConstants.HUG_ALL_VOLUNTEERS_ENDPOINT);
        return mUserTokenApiService.doGetAllVolunteers();
    }

    public Observable<List<Lessons>> getAllLessons() {
        Timber.d(ApiConstants.HUG_ALL_LESSONS_ENDPOINT);
        return mUserTokenApiService.doGetAllLessons();
    }


    public Observable<List<Schools>> getAllSchools() {
        Timber.d(ApiConstants.HUG_ALL_SCHOOLS_ENDPOINT);
        return mUserTokenApiService.doGetAllSchools();
    }

    public Observable<List<Students>> getAllStudents() {
        Timber.d(ApiConstants.HUG_ALL_SCHOOLS_ENDPOINT);
        return mUserTokenApiService.doGetAllStudents();
    }

    public Observable<List<Events>> getAllEvents() {
        Timber.d(ApiConstants.HUG_ALL_EVENTS_ENDPOINT);
        return mUserTokenApiService.doGetAllEvents();
    }

    public Observable<List<LessonsNoImage>> getAllLessonsNoImage() {
        Timber.d(ApiConstants.HUG_LESSONS_NO_IMAGE_ENDPOINT);
        return mUserTokenApiService.doGetAllLessonsNoImage();
    }


    public Completable CreateSession(Date date, Integer studentId, Integer studentMood, Integer wordsMissed, Integer lessonId, Integer userId, String recordedBy, Integer time) {
        try {
            Timber.d(ApiConstants.HUG_SESSIONS_POST);
            return Completable.fromObservable(mUserTokenApiService.doCreateSession
                    (new CreateSessionRequest(date, studentId, studentMood, wordsMissed, lessonId, userId, recordedBy, time))
                    .subscribeOn(Schedulers.io()))
                    .observeOn(AndroidSchedulers.mainThread());
        } catch (Exception e) {
            return null;
        }
    }

    public Completable ForgotPassword(String email) {
        try {
            Timber.d(ApiConstants.HUG_FORGOT_PASSWORD_ENDPOINT);
            return Completable.fromObservable(mUserTokenApiService.doForgotPassword
                    (new ForgotPassword(email))
                    .subscribeOn(Schedulers.io()))
                    .observeOn(AndroidSchedulers.mainThread());
        } catch (Exception e) {
            return null;
        }
    }

}
