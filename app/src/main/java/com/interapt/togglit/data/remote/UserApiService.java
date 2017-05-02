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

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by miller.barrera on 18/10/2016.
 */

public interface UserApiService {

    @FormUrlEncoded
    @POST(ApiConstants.HUG_VOLUNTEER_LOGIN_ENDPOINT)
    Observable<Volunteer> doVolunteerLogin(
            @Field(ApiConstants.PARAM_VOLUNTEER_EMAIL) String email,
            @Field(ApiConstants.PARAM_VOLUNTEER_PASSWORD) String password);


    @POST(ApiConstants.HUG_SESSIONS_POST)
    Observable<Void> doCreateSession(
            @Body CreateSessionRequest request);

    @POST(ApiConstants.HUG_FORGOT_PASSWORD_ENDPOINT)
    Observable<Void> doForgotPassword(
            @Body ForgotPassword request);

//    @FormUrlEncoded
//    @POST(ApiConstants.INTERAPT_FORGOT_PASSWORD_ENDPOINT)
//    Observable<ForgotPassword> doForgotPassword(
//            @Field(ApiConstants.PARAM_APP_ID) String appId,
//            @Field(ApiConstants.PARAM_EMAIL) String email);


    @GET(ApiConstants.HUG_SINGLE_STUDENT_ENDPOINT)
    Observable<Student> doStudent(
            @Path("id") Integer studentId);

    @GET(ApiConstants.HUG_SINGLE_SCHOOL_ENDPOINT)
    Observable<School> doSchool(
            @Path("id") Integer schoolId);

    @GET(ApiConstants.HUG_SINGLE_LESSON_ENDPOINT)
    Observable<Lesson> doLesson(
            @Path("id") Integer lessonId);

    @GET(ApiConstants.HUG_SINGLE_VOLUNTEER_ENDPOINT)
    Observable<Volunteer> doVolunteer(
            @Path("id") Integer volId);

    @GET(ApiConstants.HUG_SINGLE_AVERAGE_ENDPOINT)
    Observable<Average> doAverage(
            @Path("id") Integer avgId);

    @GET(ApiConstants.HUG_LESSONS_UNDONE_ENDPOINT)
    Observable<List<LessonsUndone>> doUndoneLessons(
            @Path("id") Integer studentId);

    @GET(ApiConstants.HUG_SINGLE_EVENT_ENDPOINT)
    Observable<Events> doSingleEvent(
            @Path("id") Integer eventId);

    @GET(ApiConstants.HUG_SELF_ENDPOINT)
    Observable<Volunteer> doGetSelf();

    @GET(ApiConstants.HUG_ALL_VOLUNTEERS_ENDPOINT)
    Observable<List<Volunteers>> doGetAllVolunteers();

    @GET(ApiConstants.HUG_ALL_SCHOOLS_ENDPOINT)
    Observable<List<Schools>> doGetAllSchools();

    @GET(ApiConstants.HUG_ALL_LESSONS_ENDPOINT)
    Observable<List<Lessons>> doGetAllLessons();

    @GET(ApiConstants.HUG_ALL_STUDENTS_ENDPOINT)
    Observable<List<Students>> doGetAllStudents();

    @GET(ApiConstants.HUG_LESSONS_NO_IMAGE_ENDPOINT)
    Observable<List<LessonsNoImage>> doGetAllLessonsNoImage();

    @GET(ApiConstants.HUG_ALL_EVENTS_ENDPOINT)
    Observable<List<Events>> doGetAllEvents();


}
