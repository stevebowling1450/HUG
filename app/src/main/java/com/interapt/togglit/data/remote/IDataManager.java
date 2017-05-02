package com.interapt.togglit.data.remote;

import com.interapt.togglit.data.model.Average;
import com.interapt.togglit.data.model.events.Events;
import com.interapt.togglit.data.model.lists.Lessons;
import com.interapt.togglit.data.model.lists.LessonsNoImage;
import com.interapt.togglit.data.model.lists.LessonsUndone;
import com.interapt.togglit.data.model.lists.Schools;
import com.interapt.togglit.data.model.lists.Students;
import com.interapt.togglit.data.model.lists.Volunteers;
import com.interapt.togglit.data.model.user.Lesson;
import com.interapt.togglit.data.model.user.School;
import com.interapt.togglit.data.model.user.Student;
import com.interapt.togglit.data.model.user.Volunteer;

import java.util.Date;
import java.util.List;

import rx.Completable;
import rx.Observable;

/**
 * Created by Matthew.Watson on 2/22/17.
 */

public interface IDataManager {

    Observable<Volunteer> getLoginAsVolunteer(String email, String password);

    Observable<Volunteer> getVolunteer(Integer id);

    Observable<Lesson> getLesson(Integer id);

    Observable<School> getSchool(Integer id);

    Observable<Student> getStudent(Integer id);

    Observable<Average> getAverage(Integer id);

    Observable<Events> getEvent(Integer id);

    Observable<List<LessonsUndone>> getUndoneLessons(Integer id);

    Observable<Volunteer> getSelf();

    Observable<List<Lessons>> getAllLessons();

    Observable<List<Schools>> getAllSchools();

    Observable<List<Students>> getAllStudents();

    Observable<List<Volunteers>> getAllVolunteers();

    Observable<List<LessonsNoImage>> getAllLessonsNoImage();

    Observable<List<Events>> getAllEvents();


    Completable CreateSession(Date date, Integer studentId, Integer studentMood,
                              Integer wordsMissed, Integer lessonId,
                              Integer userId, String recordedBy, Integer time);

    Completable ForgotPassword(String email);


}
