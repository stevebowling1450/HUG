package com.interapt.togglit.injection.module;

import android.app.Activity;
import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import com.interapt.togglit.injection.qualifier.ActivityContext;
import com.interapt.togglit.ui.events.EventsAdapter;
import com.interapt.togglit.ui.events.eventsCalendar.EventsCalendarDetailAdapter;
import com.interapt.togglit.ui.events.eventsDetail.EventDetailPagerAdapter;
import com.interapt.togglit.ui.lessonList.LessonListAdapter;
import com.interapt.togglit.ui.progressReport.acumen.AcumenLessonListAdapter;
import com.interapt.togglit.ui.progressReport.lesson.lessonList.LessonListFragmentAdapter;
import com.interapt.togglit.ui.schoolDirectory.SchoolDirectoryAdapter;
import com.interapt.togglit.ui.studentDirectory.StudentDirectoryAdapter;
import com.interapt.togglit.ui.volunteerDirectory.VolunteerDirectoryAdapter;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by miller.barrera
 */
@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    @Named("simpleDialog")
    MaterialDialog.Builder provideMaterialDialogSimple() {
        return new MaterialDialog.Builder(mActivity);
    }

    //Inject adapters

    @Provides
    VolunteerDirectoryAdapter volunteerDirectoryAdapter() {
        return new VolunteerDirectoryAdapter(mActivity);
    }

    @Provides
    SchoolDirectoryAdapter schoolDirectoryAdapter() {
        return new SchoolDirectoryAdapter(mActivity);
    }

    @Provides
    StudentDirectoryAdapter studentDirectoryAdapter() {
        return new StudentDirectoryAdapter(mActivity);
    }

    @Provides
    LessonListAdapter lessonListAdapter() {
        return new LessonListAdapter(mActivity);
    }

    @Provides
    LessonListFragmentAdapter sessionsListAdapter() {
        return new LessonListFragmentAdapter(mActivity);
    }

    @Provides
    EventsAdapter eventsAdapter() {
        return new EventsAdapter(mActivity);
    }

    @Provides
    EventsCalendarDetailAdapter calendarEventsDetailAdapter() {
        return new EventsCalendarDetailAdapter(mActivity);
    }

    @Provides
    EventDetailPagerAdapter eventDetailPagerAdapter() {
        return new EventDetailPagerAdapter(mActivity);
    }

    @Provides
    AcumenLessonListAdapter acumenLessonListAdapter() {
        return new AcumenLessonListAdapter(mActivity);
    }


}
